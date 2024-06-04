package com.olxseller.olx.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.olxseller.olx.helper.Keys;
import com.olxseller.olx.helper.Message;
import com.olxseller.olx.model.Blog;
import com.olxseller.olx.model.City;
import com.olxseller.olx.model.Comments;
import com.olxseller.olx.model.HomeSeo;
import com.olxseller.olx.model.MainCategory;
import com.olxseller.olx.model.RegionState;
import com.olxseller.olx.model.SubCategory;
import com.olxseller.olx.model.User;
import com.olxseller.olx.model.UserAndPost;
import com.olxseller.olx.model.WebSiteAddress;
import com.olxseller.olx.model.WebSiteSocial;
import com.olxseller.olx.repository.BlogRepository;
import com.olxseller.olx.repository.CityRepository;
import com.olxseller.olx.repository.CommentsRepository;
import com.olxseller.olx.repository.HomeSeoRepository;
import com.olxseller.olx.repository.MainCatRepository;
import com.olxseller.olx.repository.RegionStateRepository;
import com.olxseller.olx.repository.SubCatRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.repository.WebSiteAddressRepository;
import com.olxseller.olx.repository.WebSiteSocialRepository;
import com.olxseller.olx.service.BlogService;

@Controller
public class BlogController {

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Keys keys;
	
	@Autowired
	private BlogService blogservice;
	
	@Autowired
	private BlogRepository blogRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MainCatRepository mainRepo;
	
	@Autowired
	private SubCatRepository subRepo;
	
	@Autowired
	private RegionStateRepository stateRepo;
	
	@Autowired
	private CommentsRepository commentRepo;
	
	@Autowired
	private CityRepository cityRepo;
	
	@Autowired
	private HomeSeoRepository homeRepo;

	@Autowired
	private WebSiteSocialRepository websocialRepo;
	
	@Autowired
	private WebSiteAddressRepository webaddressRepo;
	
	@ModelAttribute
	public void getCommonData(Model m) {
		//all main calegories
		List<MainCategory> mainCats=this.mainRepo.getMainCatalogs();
		m.addAttribute("mainCates", mainCats);
		
		//all state 
		List<RegionState> regstate=this.stateRepo.getAllStates();
		m.addAttribute("allstates", regstate);	
		
		m.addAttribute("comment",new Comments());
		
		WebSiteSocial social=websocialRepo.getWebSocial();
		m.addAttribute("social",social);
		WebSiteAddress address=webaddressRepo.getSiteAddress();
		m.addAttribute("address",address);
	
	}
	
	@GetMapping("/{title}")
	public String singlePostPage(@PathVariable("title") String ttl,Model m) {
		String url=blogservice.getPageUrl(ttl);
			if(url.equals("post")) {
			  Blog blog=this.blogRepo.getBlogByName(ttl);
			  m.addAttribute("title",blog.getCity()+"|"+blog.getRegionState()+"|"+blog.getRegion()+"|"+blog.getCategory()+"|One to Z|"+blog.getTitle());
				m.addAttribute("keyword",blog.getCity()+"|"+blog.getRegionState()+"|"+blog.getRegion()+"|"+blog.getCategory()+"|One to Z|"+blog.getTitle());
				m.addAttribute("description",blog.getDescription());
			  m.addAttribute("blog",blog);
			  return "post";
			  
			}else if(url.equals("citypage")) { 
				/* title keyword description */
				HomeSeo home=this.homeRepo.getHomeSeo();
				m.addAttribute("title",home.getTitle());
				m.addAttribute("keyword",home.getKeyword());
				m.addAttribute("description",home.getDescription());
				
				List<Blog> blogs=this.blogRepo.getBlogByCategory(ttl,ttl);
				List<Blog> blog=this.blogRepo.getBlogByMainCatalog(ttl);
				m.addAttribute("blogs",blogs);
				//m.addAttribute("blogs",blog);
				if(blogs.isEmpty()) {
					System.out.print(blogs);
					m.addAttribute("blogs",blog);
				}
				return "citypage";
				
			}
			try{
				HomeSeo home=this.homeRepo.getHomeSeo();
				m.addAttribute("home",home);
				m.addAttribute("title",home.getTitle()!=null?home.getTitle():"");
				m.addAttribute("keyword",home.getKeyword()!=null?home.getKeyword():"");
				m.addAttribute("description",home.getDescription()!=null?home.getDescription():"");
				List<Blog> bls=this.blogRepo.getBlogs();
				m.addAttribute("blogs",bls);
			}catch(Exception e){
				System.out.println("error:"+e);
			}
			//all main calegories
			return "index";
	}
	//search containt
	 @GetMapping("/search")
		public String serchContent(@Param("keyword") String keyword,Model m) {
		 HomeSeo home=this.homeRepo.getHomeSeo();
			m.addAttribute("home",keyword);
			m.addAttribute("title",keyword);
			m.addAttribute("keyword",home.getKeyword());
			m.addAttribute("description",home.getDescription());
			List<Blog> blogs=this.blogRepo.getBlogSearchByContent(keyword);
			m.addAttribute("blogs",blogs);
			return "citypage";
		}
	 
	// add new post
	@GetMapping("/new-post")
	public String addNewPost(Model m) {
		m.addAttribute("title","cteate new post for selling ");
		m.addAttribute("keyword","cteate new post for selling ");
		m.addAttribute("description","cteate new post for selling ");
		m.addAttribute("blog", new UserAndPost());
		return "newpost";
	}
	
	//ajax call this function
	@GetMapping("/getSubByCat")
	public @ResponseBody List<SubCategory> fetchCats(@RequestParam("cat") String cat){
		List<SubCategory> regstate=this.subRepo.getAllSubCatalogs(cat);
		return regstate;
	}
	//ajax call this function
	@GetMapping("/getCityByState")
	public @ResponseBody List<City> fetchCity(@RequestParam("state") String state){
		List<City> cities=this.cityRepo.getAllCities(state);
		return cities;
	}
	//call by js this function by geolocatoin
	@GetMapping("/geolocation/{city}")
	public ResponseEntity<?> getBlogByCity(@PathVariable("city") String city) {
		List<Blog> blog=this.blogRepo.getBlogByCity(city);
		return ResponseEntity.ok(blog);
	}
	
	//ajax call this function
		@PostMapping("/createCommentOnPost")
		public @ResponseBody String createComment(@PathVariable("postid") String postid,@PathVariable("name") String name,@PathVariable("email") String email,@PathVariable("title") String title,@PathVariable("desc") String desc){
			Comments c=new Comments();
			c.setPostid(postid);
			c.setName(name);
			c.setEmail(email);
			c.setSubject(title);
			c.setDescription(desc);
			Comments cc=this.commentRepo.save(c);
			System.out.println(cc);
			return cc.toString();
			
		}
		
		
	// add new post
		@PostMapping("/process-post")
		public String processpost(@ModelAttribute("blog") UserAndPost blog,
				Model m,HttpSession session/* ,@RequestParam("image") MultipartFile image */) {
			//System.out.println(blog);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dat=sdf.format(date);
			MultipartFile image = blog.getMultipartFile();
			MultipartFile image1 = blog.getMultipartFile1();
			MultipartFile image2 = blog.getMultipartFile2();
			MultipartFile image3 = blog.getMultipartFile3();
			MultipartFile image4 = blog.getMultipartFile4();
			MultipartFile image5 = blog.getMultipartFile5();
			
			Blog b=new Blog();
				b.setImage2("");b.setImage3("");
				b.setImage4("");b.setImage5("");
				b.setImage6("");
			
			try {
				User u=new User();
				u.setName(blog.getName());
				u.setEmail(blog.getEmail());
				u.setPhone(blog.getPhone());
				u.setOther_phone(blog.getOther_number());
				u.setAgreed(true);
				u.setEnabled(true);
				u.setRole("ROLE_USER");
				u.setImage("default.png");
				u.setPassword(passwordEncoder.encode(blog.getEmail()));
				u.setCreate_at(dat);
				u.setUpdate_at(dat);
				User usr=this.userRepo.save(u);
				//System.out.print("Saved user data ::::"+usr);
				
				
				
				
				b.setAddress(blog.getAddress());
				b.setCategory(blog.getCategory());
				b.setCity(blog.getCity());
				b.setDescription(blog.getDescription());
				b.setPrice(blog.getPrice());
				b.setTitle(blog.getTitle());
				b.setRegion(blog.getRegion());
				b.setRegionState(blog.getRegionState());
				b.setMainCategory(blog.getMainCategory());
				b.setCreate_at(dat);
				b.setUpdate_at(dat);
				/*
				 * System.out.println("file 1==" + image.getOriginalFilename());
				 * System.out.println("file 2==" + image1.getOriginalFilename());
				 * System.out.println("file 3==" + image2.getOriginalFilename());
				 * System.out.println("file 4==" + image3.getOriginalFilename());
				 * System.out.println("file 5==" + image4.getOriginalFilename());
				 * System.out.println("file 6==" + image5.getOriginalFilename());
				 */
				
				//String username = principal.getName();
				User user = this.userRepo.getUserByUserName(usr.getEmail());
				
				if (result.hasErrors()) {
					return "newpost";
				}
				//set image path
				File savefile = new ClassPathResource("static/image").getFile();
				if (image.isEmpty()) {
					return "newpost";
				} else {
					//first image file
					b.setImage(image.getOriginalFilename());
					
					Path path = Paths.get(savefile.getAbsolutePath() + File.separator + image.getOriginalFilename());
					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					//System.out.println("image real path "+ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(image.getOriginalFilename()).toUriString());
				}
				if (!image1.isEmpty()) {
					//second image file
					b.setImage2(image1.getOriginalFilename());
					Files.copy(image1.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + image1.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				}	
				if (!image2.isEmpty()) {
					//third image file
					b.setImage3(image2.getOriginalFilename());
					Files.copy(image2.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + image2.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				}
				if (!image3.isEmpty()) {
					//fourth image file
					b.setImage4(image3.getOriginalFilename());
					Files.copy(image3.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + image3.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				}
				if (!image4.isEmpty()) {
					//fifth image file
					b.setImage5(image4.getOriginalFilename());
					Files.copy(image4.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + image4.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				}
				if (!image5.isEmpty()) {
					//six image file
					b.setImage6(image5.getOriginalFilename());
					Files.copy(image5.getInputStream(), Paths.get(savefile.getAbsolutePath() + File.separator + image5.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				}
					
					/*
					 * String generateFilePath = keys.generateFilePath();
					 * System.out.println("generateFilePath     :  "+generateFilePath); File
					 * savefile = new File(generateFilePath);
					 * System.out.println("savefile     :  "+savefile); Path path =
					 * Paths.get(savefile.getAbsolutePath() + File.separator +
					 * image.getOriginalFilename()); System.out.println("path     :  "+path);
					 * Files.copy(image.getInputStream(), path,
					 * StandardCopyOption.REPLACE_EXISTING); b.setImage(path.toString());
					 * System.out.println("path set     :  "+path.toString());
					 * //System.out.println("image real path "+ServletUriComponentsBuilder.
					 * fromCurrentContextPath().path("/image/").path(image.getOriginalFilename()).
					 * toUriString());
					 * 
					 * //second image file
					 * b.setImage2(generateFilePath+image1.getOriginalFilename());
					 * Files.copy(image1.getInputStream(), Paths.get(savefile.getAbsolutePath() +
					 * File.separator + image1.getOriginalFilename()),
					 * StandardCopyOption.REPLACE_EXISTING);
					 * 
					 * //third image file
					 * b.setImage3(generateFilePath+image2.getOriginalFilename());
					 * Files.copy(image2.getInputStream(), Paths.get(savefile.getAbsolutePath() +
					 * File.separator + image2.getOriginalFilename()),
					 * StandardCopyOption.REPLACE_EXISTING);
					 * 
					 * //fourth image file
					 * b.setImage4(generateFilePath+image3.getOriginalFilename());
					 * Files.copy(image3.getInputStream(), Paths.get(savefile.getAbsolutePath() +
					 * File.separator + image3.getOriginalFilename()),
					 * StandardCopyOption.REPLACE_EXISTING);
					 * 
					 * //fifth image file
					 * b.setImage5(generateFilePath+image4.getOriginalFilename());
					 * Files.copy(image4.getInputStream(), Paths.get(savefile.getAbsolutePath() +
					 * File.separator + image4.getOriginalFilename()),
					 * StandardCopyOption.REPLACE_EXISTING);
					 * 
					 * //six image file b.setImage6(generateFilePath+image5.getOriginalFilename());
					 * Files.copy(image5.getInputStream(), Paths.get(savefile.getAbsolutePath() +
					 * File.separator + image5.getOriginalFilename()),
					 * StandardCopyOption.REPLACE_EXISTING);
					 */
					session.setAttribute("message",new Message("Successfully posted !!","alert-success"));

				
					System.out.println(b);
					
				user.getBlog().add(b);
				b.setUser(user);
				this.userRepo.save(user);
				//this.blogservice.addBlogs(blog);
				session.setAttribute("message", new Message("Your post is added ! add more","alert-success"));
				//System.out.println("data" + user);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				m.addAttribute("blog",blog);
				session.setAttribute("message", new Message("Something went wrong ! try again !","alert-danger"));
			}
			return "newpost";
		}
		
	
		
	@GetMapping("/blogs")
	public ResponseEntity<List<Blog>> getBlogs() {
		List<Blog> list =blogservice.getAllBlogs();
		if(list.size()<=0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(list);
	}
	
	@GetMapping("/blogs/{id}")
	public ResponseEntity<Blog> getBlog(@PathVariable("id") int id) {
		Blog blog=blogservice.getBlogById(id);
		if(blog==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(blog));
	}
	
	@PostMapping("/blogs") 
	public ResponseEntity<Blog> addBlog(@RequestBody Blog blog) {
		Blog b=null;
		try {
		b=this.blogservice.addBlogs(blog);
		return ResponseEntity.of(Optional.of(b));
		}catch(Exception e) {
			
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		
	}
	
	@DeleteMapping("/blog/{id}")
	public ResponseEntity<Void> deleteBlog(@PathVariable("id") int id) {
	 try {
		 this.blogservice.deleteBlogs(id);
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	 }catch(Exception e) {
		 e.printStackTrace();
		 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	 }	
	}
	
	@PutMapping("/blogs/{id}")
	public ResponseEntity<Blog> updateBlog(@RequestBody Blog blog,@PathVariable("id") int id) {
		try {
			this.blogservice.updateBlogs(blog, id);
			return ResponseEntity.ok().body(blog);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
		
	}
}
