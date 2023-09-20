package com.olxseller.olx.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.olxseller.olx.helper.Message;
import com.olxseller.olx.model.Blog;
import com.olxseller.olx.model.MainCategory;
import com.olxseller.olx.model.RegionState;
import com.olxseller.olx.model.User;
import com.olxseller.olx.model.WebSiteAddress;
import com.olxseller.olx.model.WebSiteSocial;
import com.olxseller.olx.repository.BlogRepository;
import com.olxseller.olx.repository.MainCatRepository;
import com.olxseller.olx.repository.RegionStateRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.repository.WebSiteAddressRepository;
import com.olxseller.olx.repository.WebSiteSocialRepository;
import com.olxseller.olx.service.UserService;

import net.bytebuddy.asm.Advice.This;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService userservice;
	
	@Autowired
	private BlogRepository blogRepo;
	@Autowired
	private MainCatRepository mainRepo;
	@Autowired
	private RegionStateRepository stateRepo;
	@Autowired
	private WebSiteAddressRepository websiteRepo;
	@Autowired
	private WebSiteSocialRepository websocialRepo;
	@Autowired
	private BCryptPasswordEncoder bycript;

	@ModelAttribute
	public void addCommonData(Model m, Principal principal) {
		//all main calegories
	List<MainCategory> mainCats=this.mainRepo.getMainCatalogs();
	m.addAttribute("mainCates", mainCats);
	
	//all state 
	List<RegionState> regstate=this.stateRepo.getAllStates();
	m.addAttribute("allstates", regstate);
		String usrname = principal.getName();
//		System.out.println(usrname);
		User user = userRepo.getUserByUserName(usrname);
		m.addAttribute("user", user);
		WebSiteSocial social=websocialRepo.getWebSocial();
		m.addAttribute("social",social);
		WebSiteAddress address=websiteRepo.getSiteAddress();
		m.addAttribute("address",address);
	}

	//user home dashboard
	@GetMapping("/index/{page}")
	public String UserDashboard(@PathVariable("page") Integer page ,Model model,Principal principal) {
		model.addAttribute("title", "User Dashboard ");
		String usrname = principal.getName();
//		System.out.println(usrname);
		User user = userRepo.getUserByUserName(usrname);
		Pageable pageable=PageRequest.of(page, 6);
		Page<Blog> blogs=this.blogRepo.findBlogsByUser(user.getId(),pageable);
	//	System.out.println(user);
		model.addAttribute("blogs",blogs);
		model.addAttribute("currentpage",page);
		model.addAttribute("totalpage",blogs.getTotalPages());
		return "normal/user_dashboard";
	}

	// user profile
	@GetMapping("/profile")
	public String userprofile(Model model) {
		model.addAttribute("title", "user profile info");
		return "normal/myaccount";
	}
	
		
	@PostMapping("/profile/update")
	public String do_register(@Valid @ModelAttribute("user") User  user,Principal principal, Model m,HttpSession session) {
		String pageurl="redirect:/user/index/0";
		try {
			
			System.out.println("user value="+user);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			String dat=sdf.format(date);
			
			user.setUpdate_at(dat);
			
			User u=this.userRepo.save(user);
			//System.out.println(u);
			
			  session.setAttribute("message",new Message("Successfully registered !!","alert-success"));
			
			return pageurl;
			
		}catch(Exception e) {
			e.printStackTrace();
			
			  m.addAttribute("user",user); session.setAttribute("message",new Message("Something went wrong!"+e.getMessage(),"alert-danger"));
			
			return pageurl;
		}
		
	}
	// user password
		@GetMapping("/update-password")
		public String userpassword(Model model) {
			model.addAttribute("title", "update your password");
			return "normal/update_password";
		}	
		
		
		@PostMapping("/profile/update_password")
	public String updatepassword(@RequestParam("new_password") String new_password,Principal principal, Model m,HttpSession session) {
			String pageurl="redirect:/user/index/0";
			String username = principal.getName();
			User user = this.userRepo.getUserByUserName(username);
			try {
				
				boolean is=bycript.matches(new_password,user.getPassword());
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
				String dat=sdf.format(date);
				if(is){
				session.setAttribute("message",new Message("You Entered same password !!","alert-danger"));
				}else {
				user.setUpdate_at(dat);
				user.setPassword(this.bycript.encode(new_password));
				User u=this.userRepo.save(user);
				/* System.out.println(u); */
				session.setAttribute("message",new Message("Successfully update !!","alert-success"));
				}
				  
				return pageurl;
				
			}catch(Exception e) {
				e.printStackTrace();
			    m.addAttribute("user",user); session.setAttribute("message",new Message("Something went wrong!"+e.getMessage(),"alert-danger"));
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
	public String processpost(@Valid @ModelAttribute Blog blog, Principal principal,
			BindingResult result,HttpSession session/* ,@RequestParam("image") MultipartFile image */) {
		MultipartFile image = blog.getMultipartFile();
		MultipartFile image1 = blog.getMultipartFile1();
		MultipartFile image2 = blog.getMultipartFile2();
		MultipartFile image3 = blog.getMultipartFile3();
		MultipartFile image4 = blog.getMultipartFile4();
		MultipartFile image5 = blog.getMultipartFile5();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		String dat=sdf.format(date);
		
		try {
			
			
			/*
			 * System.out.println("file 1==" + image.getOriginalFilename());
			 * System.out.println("file 2==" + image1.getOriginalFilename());
			 * System.out.println("file 3==" + image2.getOriginalFilename());
			 * System.out.println("file 4==" + image3.getOriginalFilename());
			 * System.out.println("file 5==" + image4.getOriginalFilename());
			 * System.out.println("file 6==" + image5.getOriginalFilename());
			 */
			String username = principal.getName();
			User user = this.userRepo.getUserByUserName(username);
			
			if (result.hasErrors()) {
				return "normal/addpost";
			}
			File savefile = new ClassPathResource("static/image").getFile();
			if (image.isEmpty()) {
				return "normal/addpost";
			} else {
				//first image file
				blog.setImage(image.getOriginalFilename());
				
				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + image.getOriginalFilename());
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				//System.out.println("image real path "+ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(image.getOriginalFilename()).toUriString());
				}
			
				if(!image1.isEmpty()) {
				//second image file
				blog.setImage2(image1.getOriginalFilename());
				Files.copy(image1.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + image1.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				}else {blog.setImage2("");}
				
				if (!image2.isEmpty()) {
				//third image file
				blog.setImage3(image2.getOriginalFilename());
				Files.copy(image2.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + image2.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				}else {blog.setImage3("");}
		
				if (!image3.isEmpty()) {
				//fourth image file
				blog.setImage4(image3.getOriginalFilename());
				Files.copy(image3.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + image3.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				}else {blog.setImage4("");}
		
				if (!image4.isEmpty()) {
				//fifth image file
				blog.setImage5(image4.getOriginalFilename());
				Files.copy(image4.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + image4.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				}else {blog.setImage5("");}
				
				if (!image5.isEmpty()) {	
				//six image file
				blog.setImage6(image5.getOriginalFilename());
				Files.copy(image5.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + image5.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				
				System.out.println("image upload successfully");

			}else {blog.setImage6("");}
				
				blog.setCreate_at(dat);
				blog.setUpdate_at(dat);
				
			user.getBlog().add(blog);
			blog.setUser(user);
			this.userRepo.save(user);
			session.setAttribute("message", new Message("Your post is added ! add more","alert-success"));
			//System.out.println("data" + user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("message", new Message("Something went wrong ! try again !","alert-danger"));
		}
		return "normal/addpost";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteblog(@PathVariable("id") Integer id,Model m,HttpSession session,Principal principal) {
		Optional<Blog> optionalblog=this.blogRepo.findById(id);
		Blog blog=optionalblog.get();
		
		//check login user 
		String usrname = principal.getName();
		User user = userRepo.getUserByUserName(usrname);
		if(user.getId()==blog.getUser().getId()) {
			m.addAttribute("blogs",blog);
			blog.setUser(null);
			this.blogRepo.delete(blog);
		}
		session.setAttribute("message", new Message("disabled user successfully","alert-success"));
		return "redirect:/user/index/0";
	}
	
	@PostMapping("/edit/{id}")
	public String updateblog(@PathVariable("id") Integer id,Model m,HttpSession session,Principal principal) {
		//Optional<Blog> optionalblog=this.blogRepo.findById(id);
		Blog blog=this.blogRepo.findById(id).get();
		m.addAttribute("blog",blog);
//		//check login user 
//		String usrname = principal.getName();
//		User user = userRepo.getUserByUserName(usrname);
//		if(user.getId()==blog.getUser().getId()) {
//			m.addAttribute("blog",blog);
//			
//		}
		return "normal/updateblog";
	}
	
	@PostMapping("/post/update")
	public String updatepost(@Valid @ModelAttribute("blog") Blog blog,BindingResult result,Principal principal,
			Model m,HttpSession session) {
		try {
			MultipartFile img = blog.getMultipartFile();
			MultipartFile img1= blog.getMultipartFile1();
			MultipartFile img2 = blog.getMultipartFile2();
			MultipartFile img3 = blog.getMultipartFile3();
			MultipartFile img4 = blog.getMultipartFile4();
			MultipartFile img5 = blog.getMultipartFile5();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dat=sdf.format(date);
			
			File savefile = new ClassPathResource("static/image").getFile();
			if (img.isEmpty()) {
				return "redirect:/user/index/0";
			} else {
				//first image file
				blog.setImage(img.getOriginalFilename());
				
				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + img.getOriginalFilename());
				Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				//System.out.println("image real path "+ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(image.getOriginalFilename()).toUriString());
			}
			blog.setImage2("");
			if (!img1.isEmpty()) {	
				//second image file
				blog.setImage2(img1.getOriginalFilename());
				Files.copy(img1.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + img1.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			}else {blog.setImage2("");}
			blog.setImage3("");
			if (!img2.isEmpty()) {
				//third image file
				blog.setImage3(img2.getOriginalFilename());
				Files.copy(img2.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + img2.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			}else {blog.setImage3("");}
			blog.setImage4("");
			if (!img3.isEmpty()) {
				//fourth image file
				blog.setImage4(img3.getOriginalFilename());
				Files.copy(img3.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + img3.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			}else {blog.setImage4("");}
			blog.setImage5("");
			if (!img4.isEmpty()) {
				//fifth image file
				blog.setImage5(img4.getOriginalFilename());
				Files.copy(img4.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + img4.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			}else {blog.setImage5("");}
			blog.setImage6("");
			if (!img5.isEmpty()) {
				//six image file
				blog.setImage6(img5.getOriginalFilename());
				Files.copy(img5.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + img5.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				
				System.out.println(" update successfully");

			}else {blog.setImage6("");}
			System.out.println(blog);
			blog.setCreate_at(blog.getCreate_at());
			blog.setUpdate_at(dat);
		//check login user 
			String username = principal.getName();
			User user = this.userRepo.getUserByUserName(username);
			blog.setUser(user);
//			user.getBlog().add(blog);
//			blog.setUser(user);
			this.blogRepo.save(blog);
			session.setAttribute("message", new Message("update blog successfully","alert-success"));
		
		return "redirect:/user/index/0";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("message", new Message("Something went wrong ! try again !","alert-danger"));
		}
		return "redirect:/user/index/0";
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		List<User> list = userservice.getAllUsers();
		if (list.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(list);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		System.out.println(id);
		User user = userservice.getUserById(id);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(user));
	}

	@PostMapping("/users")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User u = null;
		try {
			u = this.userservice.addUsers(user);
			return ResponseEntity.of(Optional.of(u));
		} catch (Exception e) {

		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
		try {
			this.userservice.deleteUsers(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") int id) {
		try {
			this.userservice.updateUsers(user, id);
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
