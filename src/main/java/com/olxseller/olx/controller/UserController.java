package com.olxseller.olx.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.olxseller.olx.helper.Message;
import com.olxseller.olx.model.Blog;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.BlogRepository;
import com.olxseller.olx.service.BlogService;
import com.olxseller.olx.service.CategoryService;
import com.olxseller.olx.service.StateService;
import com.olxseller.olx.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userservice;
	@Autowired
	private BlogService blogService;

	@Autowired
	private BlogRepository blogRepo;
	@Autowired
	private CategoryService mainService;
	@Autowired
	private StateService stateService;

	@ModelAttribute
	public void CommonData(Model m, Principal principal) {
		m.addAttribute("mainCates", mainService.getAllMainCategory());
		m.addAttribute("allstates", stateService.getAllStates());
		m.addAttribute("user",principal!=null? userservice.findUserByEmail(principal.getName()):new User());
	}
	@GetMapping("/")
	public String Dashboard(Model m){
		m.addAttribute("title", "admin dashboard");
		return "normal/index";
	}

	// user home dashboard
	@GetMapping("/account")
	public String UserDashboard(@PathVariable("page") Integer page, Model model, Principal principal) {
		model.addAttribute("title", "User Dashboard "); 
		User user = userservice.findUserByEmail(principal.getName());
		Pageable pageable = PageRequest.of(page, 6);
		Page<Blog> blogs = this.blogRepo.findBlogsByUser(user.getId(), pageable);
		// System.out.println(user);
		model.addAttribute("blogs", blogs);
		model.addAttribute("currentpage", page);
		model.addAttribute("totalpage", blogs.getTotalPages());
		return "normal/user_dashboard";
	}

	// user profile
	@GetMapping("/profile")
	public String userprofile(Model model) {
		model.addAttribute("title", "user profile info");
		return "normal/myaccount";
	}

	@PostMapping("/profile/update")
	public String do_register(@ModelAttribute("user") User user, Principal principal, Model m, HttpSession session) {
		String pageurl = "redirect:/user/";
		try {

			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			String dat = sdf.format(date);

			user.setUpdate_at(dat);

			User u = this.userservice.createUser(user);
			System.out.println(u);

			session.setAttribute("message", new Message("Successfully registered !!", "alert-success"));

			return pageurl;

		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

	// add new post
	@GetMapping("/add-post")
	public String addNewPost(Model model) {
		model.addAttribute("title", "add new post selling ");
		model.addAttribute("blog", new Blog());
		return "normal/addpost";
	}

	// add new post
	@PostMapping("/process-post")
	public String processpost(@ModelAttribute Blog blog, Principal principal,
			HttpSession session/* ,@RequestParam("image") MultipartFile image */) {
		MultipartFile image = blog.getMultipartFile();
		MultipartFile image1 = blog.getMultipartFile1();
		MultipartFile image2 = blog.getMultipartFile2();
		MultipartFile image3 = blog.getMultipartFile3();
		MultipartFile image4 = blog.getMultipartFile4();
		MultipartFile image5 = blog.getMultipartFile5();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		String dat = sdf.format(date);

		try {

			/*
			 * System.out.println("file 1==" + image.getOriginalFilename());
			 * System.out.println("file 2==" + image1.getOriginalFilename());
			 * System.out.println("file 3==" + image2.getOriginalFilename());
			 * System.out.println("file 4==" + image3.getOriginalFilename());
			 * System.out.println("file 5==" + image4.getOriginalFilename());
			 * System.out.println("file 6==" + image5.getOriginalFilename());
			 */
			// String username = principal.getName();
			User user = this.userservice.findUserByEmail(principal.getName());

			/*
			 * if (result.hasErrors()) { return "normal/addpost"; }
			 */
			File savefile = new ClassPathResource("static/image").getFile();
			if (image.isEmpty()) {
				return "normal/addpost";
			} else {
				// first image file
				blog.setImage(image.getOriginalFilename());

				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + image.getOriginalFilename());
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				// System.out.println("image real path
				// "+ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(image.getOriginalFilename()).toUriString());
			}

			if (!image1.isEmpty()) {
				// second image file
				blog.setImage2(image1.getOriginalFilename());
				Files.copy(image1.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + image1.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				blog.setImage2("");
			}

			if (!image2.isEmpty()) {
				// third image file
				blog.setImage3(image2.getOriginalFilename());
				Files.copy(image2.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + image2.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				blog.setImage3("");
			}

			if (!image3.isEmpty()) {
				// fourth image file
				blog.setImage4(image3.getOriginalFilename());
				Files.copy(image3.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + image3.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				blog.setImage4("");
			}

			if (!image4.isEmpty()) {
				// fifth image file
				blog.setImage5(image4.getOriginalFilename());
				Files.copy(image4.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + image4.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				blog.setImage5("");
			}

			if (!image5.isEmpty()) {
				// six image file
				blog.setImage6(image5.getOriginalFilename());
				Files.copy(image5.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + image5.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);

				System.out.println("image upload successfully");

			} else {
				blog.setImage6("");
			}

			blog.setCreate_at(dat);
			blog.setUpdate_at(dat);

			user.getBlog().add(blog);
			blog.setUser(user);
			this.userservice.createUser(user);
			session.setAttribute("message", new Message("Your post is added ! add more", "alert-success"));
			// System.out.println("data" + user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("message", new Message("Something went wrong ! try again !", "alert-danger"));
		}
		return "normal/addpost";
	}

	@GetMapping("/delete/{id}")
	public String deleteblog(@PathVariable("id") Integer id, Model m, HttpSession session, Principal principal) {
		Optional<Blog> optionalblog = this.blogRepo.findById(id);
		Blog blog = optionalblog.get();

		// check login user
		// User user = this.userservice.findUserByEmail(principal.getName());
		if (userservice.findUserByEmail(principal.getName()).getId() == blog.getUser().getId()) {
			m.addAttribute("blogs", blog);
			blog.setUser(null);
			this.blogRepo.delete(blog);
		}
		session.setAttribute("message", new Message("disabled user successfully", "alert-success"));
		return "redirect:/user/";
	}

	@PostMapping("/edit/{id}")
	public String updateblog(@PathVariable("id") Integer id, Model m, HttpSession session, Principal principal) {
		// Optional<Blog> optionalblog=this.blogRepo.findById(id);
		Blog blog = this.blogRepo.findById(id).get();
		m.addAttribute("blog", blog);
		// //check login user
		// String usrname = principal.getName();
		// User user = userRepo.getUserByUserName(usrname);
		// if(user.getId()==blog.getUser().getId()) {
		// m.addAttribute("blog",blog);
		//
		// }
		return "normal/updateblog";
	}

	@PostMapping("/post/update")
	public String updatepost(@ModelAttribute("blog") Blog blog, Principal principal,
			Model m, HttpSession session) {
		try {
			MultipartFile img = blog.getMultipartFile();
			MultipartFile img1 = blog.getMultipartFile1();
			MultipartFile img2 = blog.getMultipartFile2();
			MultipartFile img3 = blog.getMultipartFile3();
			MultipartFile img4 = blog.getMultipartFile4();
			MultipartFile img5 = blog.getMultipartFile5();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dat = sdf.format(date);

			File savefile = new ClassPathResource("static/image").getFile();
			if (img.isEmpty()) {
				return "redirect:/user/";
			} else {
				// first image file
				blog.setImage(img.getOriginalFilename());

				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + img.getOriginalFilename());
				Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				// System.out.println("image real path
				// "+ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(image.getOriginalFilename()).toUriString());
			}
			blog.setImage2("");
			if (!img1.isEmpty()) {
				// second image file
				blog.setImage2(img1.getOriginalFilename());
				Files.copy(img1.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + img1.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				blog.setImage2("");
			}
			blog.setImage3("");
			if (!img2.isEmpty()) {
				// third image file
				blog.setImage3(img2.getOriginalFilename());
				Files.copy(img2.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + img2.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				blog.setImage3("");
			}
			blog.setImage4("");
			if (!img3.isEmpty()) {
				// fourth image file
				blog.setImage4(img3.getOriginalFilename());
				Files.copy(img3.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + img3.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				blog.setImage4("");
			}
			blog.setImage5("");
			if (!img4.isEmpty()) {
				// fifth image file
				blog.setImage5(img4.getOriginalFilename());
				Files.copy(img4.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + img4.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				blog.setImage5("");
			}
			blog.setImage6("");
			if (!img5.isEmpty()) {
				// six image file
				blog.setImage6(img5.getOriginalFilename());
				Files.copy(img5.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + img5.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);

				System.out.println(" update successfully");

			} else {
				blog.setImage6("");
			}
			System.out.println(blog);
			blog.setUpdate_at(dat);
			User user = this.userservice.findUserByEmail(principal.getName());
			blog.setUser(user);
			// user.getBlog().add(blog);
			// blog.setUser(user);
			this.blogRepo.save(blog);
			session.setAttribute("message", new Message("update blog successfully", "alert-success"));

			return "redirect:/user/";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("message", new Message("Something went wrong ! try again !", "alert-danger"));
		}
		return "redirect:/user/";
	}

	// @GetMapping("/users")
	// public ResponseEntity<List<User>> getUsers() {
	// List<User> list = userservice.getAllUsers();
	// if (list.size() <= 0) {
	// return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	// }
	// return ResponseEntity.status(HttpStatus.CREATED).body(list);
	// }

	// @GetMapping("/users/{id}")
	// public ResponseEntity<User> getUser(@PathVariable("id") int id) {
	// System.out.println(id);
	// User user = userservice.getUserById(id);
	// if (user == null) {
	// return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	// }
	// return ResponseEntity.of(Optional.of(user));
	// }

	// @PostMapping("/users")
	// public ResponseEntity<User> addUser(@RequestBody User user) {
	// User u = null;
	// try {
	// u = this.userservice.addUsers(user);
	// return ResponseEntity.of(Optional.of(u));
	// } catch (Exception e) {

	// }
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	// }

	// @DeleteMapping("/users/{id}")
	// public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
	// try {
	// this.userservice.deleteUsers(id);
	// return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	// } catch (Exception e) {
	// e.printStackTrace();
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// }
	// }

	// @PutMapping("/users/{id}")
	// public ResponseEntity<User> updateUser(@RequestBody User user,
	// @PathVariable("id") int id) {
	// try {
	// this.userservice.updateUsers(user, id);
	// return ResponseEntity.ok().body(user);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// }

	// }
}
