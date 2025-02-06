package com.olxseller.olx.controller;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.olxseller.olx.helper.Message;
import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.Blog;
import com.olxseller.olx.model.ContactUs;
import com.olxseller.olx.model.User;
import com.olxseller.olx.model.UserAndPost;
import com.olxseller.olx.model.WebPage;
import com.olxseller.olx.service.BlogService;
import com.olxseller.olx.service.CategoryService;
import com.olxseller.olx.service.CityService;
import com.olxseller.olx.service.ContactService;
import com.olxseller.olx.service.EmailService;
import com.olxseller.olx.service.SocialService;
import com.olxseller.olx.service.StateService;
import com.olxseller.olx.service.SubCategoryService;
import com.olxseller.olx.service.UserService;
import com.olxseller.olx.service.WebAddressService;
import com.olxseller.olx.service.WebPageService;

@Controller
public class PageController {

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService mailService;
	@Autowired
	private WebPageService pageService;
	@Autowired
	private SocialService socialService;
	@Autowired
	private WebAddressService webaddressService;
	@Autowired
	public ResponseData responseData;
	@Autowired
	private SubCategoryService subcatService;
	@Autowired
	private StateService stateService;
	@Autowired
	private CityService cityService;
	@Autowired
	private CategoryService catService;
	@Autowired
	private BlogService blogService;
	@Autowired
  public ContactService contactService;

	@ModelAttribute
	public void commondata(Model m,Principal principal) {
		Map<String,Object> map=new HashMap<>();
		map.put("cats", catService.getAllMainCategory());
		map.put("states", stateService.getAllStates());
		map.put("cities", cityService.getAllCity());
		map.put("subcats", subcatService.getAllSubcat());
		map.put("blogs", blogService.getAllBlogs());
		var dta= responseData.jsonDataResponse("SUCCESS", "load categories", map);
		m.addAttribute("dta",dta);
		m.addAttribute("allstates", this.stateService.getAllStates());
		m.addAttribute("social",  socialService.getSocialLinks());
		m.addAttribute("address", webaddressService.getAddress());
		m.addAttribute("user",principal!=null? userService.findUserByEmail(principal.getName()): new User());
	}

	@GetMapping("/about")
	public String about(Model model) { 
		WebPage web = this.pageService.getPageByName("about");
		model.addAttribute("title", web!=null?web.getTitle():"");
		model.addAttribute("keyword", web.getKeyword());
		model.addAttribute("description", web.getDescription());
		model.addAttribute("name", "ajay rajput");
		model.addAttribute("is", true);

		model.addAttribute("subtitle", LocalDateTime.now().toString());
		return "about";
	}

	@GetMapping("/contact")
	public String contact(Model m) {
		WebPage web = this.pageService.getPageByName("contact");
		m.addAttribute("title", web!=null?web.getTitle():"");
		m.addAttribute("keyword", web.getKeyword());
		m.addAttribute("description", web.getDescription());
		m.addAttribute("cantactdata", new ContactUs());
		return "contact";
	}

	@GetMapping("/privacy-policy")
	public String privacyPolicy(Model m) {
		WebPage web = this.pageService.getPageByName("privacy policy");
		m.addAttribute("title", web!=null?web.getTitle():"");
		m.addAttribute("keyword", web.getKeyword());
		m.addAttribute("description", web.getDescription());
		return "privacy-policy";
	}

	@GetMapping("/term-and-condition")
	public String termCondition(Model m) {
		WebPage web = this.pageService.getPageByName("terms and conditions");
		m.addAttribute("title", web!=null?web.getTitle():"");
		m.addAttribute("keyword", web.getKeyword());
		m.addAttribute("description", web.getDescription());
		return "term-and-condition";
	}

	// @GetMapping("/post")
	// public String postpage(Model m) {
	// m.addAttribute("title","htis is post page");
	// return "post";
	// }
	@GetMapping("/signin")
	public String login(Model m) {
		m.addAttribute("title", "login here !");
		m.addAttribute("logindata", new User());
		return "signin";
	}
	@GetMapping("/logout")
	public String lotout(Model m,HttpSession session) {
		session.invalidate();
		System.out.println("logout: "+session.getAttribute("username"));
		return "redirect:/";
	}

	@PostMapping("/login_process")
	public String doLogin(@ModelAttribute("logindata") User logindata, Model model,HttpSession session) {
		// if(result.hasErrors()) {
		// // System.out.println("error"+result);
		// return "signin";
		// }
		/* System.out.println(logindata); */
		String activename = (String) session.getAttribute("username");
		if(activename==null){
			session.setAttribute("username", logindata.getEmail());
		}
		System.out.println("wishlist"+logindata.getWishList());
		System.out.println("login:"+activename);
		model.addAttribute("title", logindata.getEmail());
		model.addAttribute("subtitle", logindata.getPassword());
		/*
		 * String username=principal.getName(); System.out.println("user"+username);
		 * User user=this.userRepo.getUserByUserName(username); List<Blog>
		 * blogs=this.blogRepository.findBlogsByUser(user.getId());
		 */

		return "/user/index";
	}
/* 
	@GetMapping({ "", "/" })
	public String home(Model m) {
		try {
			HomeSeo home = this.homeRepo.getHomeSeo();
			m.addAttribute("home", home);
			m.addAttribute("title", home.getTitle());
			m.addAttribute("keyword", home.getKeyword());
			m.addAttribute("description", home.getDescription());
			List<Blog> blogs = this.blogRepository.getBlogs();
			m.addAttribute("blogs", blogs);
			List<MainCategory> mainCats = this.mainRepo.getMainCatalogs();
			m.addAttribute("mainCates", mainCats);
		} catch (Exception e) {
			System.out.println("error:" + e);
		}
		System.out.println("home");
		return "index";
	}
	*/

	@GetMapping({"", "/" })
	public String demo(Model m) {
		m.addAttribute("blogs", blogService.getAllBlogs());
		m.addAttribute("title", "this is home demo keep stay ");
		m.addAttribute("disc", "this is home demo Description keep stay  ");
		return "index";
	}

	@GetMapping({ "/signup", "/register" })
	public String register(Model m) {
		m.addAttribute("title",
				"Home: Onetoz Free Indian Classifieds | Online Classifieds India | Buy &amp; Sell Anything Free");
		m.addAttribute("regdata", new User());
		return "signup";
	}

	@PostMapping("/do_register")
	public String do_register(@ModelAttribute("regdata") User user,
			@RequestParam(value = "agreed", defaultValue = "false") Boolean agreed, Model m, HttpSession session) {

		try {
			if (!agreed) {
				System.out.print("you have not agreed the terms and conditions !");
				throw new Exception("you have not agreed the terms and conditions !");
			}
			// ,
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return "signup";
			// }

			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			String dat = sdf.format(date);

			user.setAgreed(true);
			user.setEnabled(true);
			user.setRole("ROLE_USER");
			user.setImage("default.png");
			user.setOther_phone("");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setCreate_at(dat);
			user.setUpdate_at(dat);

			User u = this.userService.createUser(user);
			System.out.println(u);
			m.addAttribute("regdata", new User());
			String email = u.getEmail();
			String mess = "this is demo email";
			String subject = "demo purpose";
			String username = (String) session.getAttribute("username");
			session.setAttribute("message", new Message("Successfully registered !!", "alert-success"));
			boolean flag=this.mailService.sendEmail(subject, mess, email);
			if(flag)
			{
			session.setAttribute("email", email);

			}else
			{
			session.setAttribute("message", new Message("mail not send check your email!!","alert-success"));
			}
			return "signup";

		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("regdata", user);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return "signup";
		}

	}

	@PostMapping("/do_contactus")
	public String doContact(@ModelAttribute("cantactdata") ContactUs cantactdata, Model m, HttpSession session) {
		try {
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return "contact";
			// }
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");

			cantactdata.setDate(sdf.format(date));
			ContactUs u = contactService.addContact(cantactdata);
			m.addAttribute("cantactdata", new ContactUs());
			session.setAttribute("message", new Message("Successfully send your request !!", "alert-success"));
			boolean flag = this.mailService.sendEmail(cantactdata.getSubject(), cantactdata.getDescription(),
					cantactdata.getEmail());
			if (flag) {
				session.setAttribute("email", cantactdata.getEmail());

			} else {
				session.setAttribute("message", new Message("mail not send check your email !!", "alert-success"));

			}

			return "contact";

		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("cantactdata", cantactdata);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return "contact";
		}

	}
	// add new post
	@GetMapping("/new-post")
	public String addNewPost(Model m) {
		Map<String,Object> map=new HashMap<>();
		map.put("cats", catService.getAllMainCategory());
		map.put("subcat", subcatService.getAllSubcat());
		var dta= responseData.jsonDataResponse("SUCCESS", "load categories", map);
		m.addAttribute("dta",dta);
		m.addAttribute("mainCates",catService.getAllMainCategory());
		m.addAttribute("title","cteate new post for selling ");
		m.addAttribute("keyword","cteate new post for selling ");
		m.addAttribute("description","cteate new post for selling ");
		m.addAttribute("blog", new UserAndPost());
		return "newpost";
	}

	@GetMapping("/{title}")
	public String singlePostPage(@PathVariable("title") String ttl,Model m) {
		String url=blogService.getPageUrl(ttl);
		System.out.println("url:"+url);
		if(url.equals("post")) {
			Blog blog=this.blogService.getBlogDetailByTitle(ttl);
			  m.addAttribute("title",blog.getCity()+"|"+blog.getRegionState()+"|"+blog.getRegion()+"|"+blog.getCategory()+"|One to Z|"+blog.getTitle());
				m.addAttribute("keyword",blog.getCity()+"|"+blog.getRegionState()+"|"+blog.getRegion()+"|"+blog.getCategory()+"|One to Z|"+blog.getTitle());
				m.addAttribute("description",blog.getDescription());
			  m.addAttribute("blog",blog);
			return "post";

		}else if(url.equals("post")) {
		return "citypage";
		}
		return "index";
	}

}
