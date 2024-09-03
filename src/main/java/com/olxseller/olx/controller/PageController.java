package com.olxseller.olx.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.olxseller.olx.helper.Message;
import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.Blog;
import com.olxseller.olx.model.ContactUs;
import com.olxseller.olx.model.HomeSeo;
import com.olxseller.olx.model.MainCategory;
import com.olxseller.olx.model.RegionState;
import com.olxseller.olx.model.User;
import com.olxseller.olx.model.WebPage;
import com.olxseller.olx.model.WebSiteAddress;
import com.olxseller.olx.model.WebSiteSocial;
import com.olxseller.olx.repository.BlogRepository;
import com.olxseller.olx.repository.ContactUsRepository;
import com.olxseller.olx.repository.HomeSeoRepository;
import com.olxseller.olx.repository.MainCatRepository;
import com.olxseller.olx.repository.RegionStateRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.repository.WebPageRepositoy;
import com.olxseller.olx.repository.WebSiteAddressRepository;
import com.olxseller.olx.repository.WebSiteSocialRepository;
import com.olxseller.olx.service.EmailService;
import com.olxseller.olx.service.UserService;

@Controller
public class PageController {

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private MainCatRepository mainRepo;

	@Autowired
	private EmailService mailService;

	@Autowired
	private HomeSeoRepository homeRepo;

	@Autowired
	private WebPageRepositoy webRepo;

	@Autowired
	private RegionStateRepository stateRepo;

	@Autowired
	private WebSiteSocialRepository websocialRepo;

	@Autowired
	private WebSiteAddressRepository webaddressRepo;

	@Autowired
	private ContactUsRepository contactRepo;
	
	@Autowired
	public ResponseData responseData;

	@ModelAttribute
	public void commondata(Model m) {
		// all main calegories
		List<MainCategory> mainCats = this.mainRepo.getMainCatalogs();
		System.out.println(responseData.jsonCategoryResponse("SUCCESS", "load categories", mainCats));
		var dta= responseData.jsonCategoryResponse("SUCCESS", "load categories", mainCats);
		m.addAttribute("mainCat",dta.get("data"));
		m.addAttribute("mainCates",mainCats);

		// all state
		List<RegionState> regstate = this.stateRepo.getAllStates();
		m.addAttribute("allstates", regstate);

		WebSiteSocial social = websocialRepo.getWebSocial();
		m.addAttribute("social", social);
		WebSiteAddress address = webaddressRepo.getSiteAddress();
		m.addAttribute("address", address);
	}

	@GetMapping("/about")
	public String about(Model model) {
		WebPage web = this.webRepo.getWebPageByName("about");
		model.addAttribute("title", web.getTitle());
		model.addAttribute("keyword", web.getKeyword());
		model.addAttribute("description", web.getDescription());
		model.addAttribute("name", "ajay rajput");
		model.addAttribute("is", true);

		model.addAttribute("subtitle", LocalDateTime.now().toString());
		return "about";
	}

	@GetMapping("/contact")
	public String contact(Model m) {
		WebPage web = this.webRepo.getWebPageByName("contact");
		m.addAttribute("title", web.getTitle());
		m.addAttribute("keyword", web.getKeyword());
		m.addAttribute("description", web.getDescription());
		m.addAttribute("cantactdata", new ContactUs());
		return "contact";
	}

	@GetMapping("/privacy-policy")
	public String privacyPolicy(Model m) {
		WebPage web = this.webRepo.getWebPageByName("privacy-policy");
		m.addAttribute("title", web.getTitle());
		m.addAttribute("keyword", web.getKeyword());
		m.addAttribute("description", web.getDescription());
		return "privacy-policy";
	}

	@GetMapping("/term-and-condition")
	public String termCondition(Model m) {
		WebPage web = this.webRepo.getWebPageByName("term-and-condition");
		m.addAttribute("title", web.getTitle());
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

	@PostMapping("/login_process")
	public String doLogin(@ModelAttribute("logindata") User logindata, Model model) {
		// if(result.hasErrors()) {
		// // System.out.println("error"+result);
		// return "signin";
		// }
		/* System.out.println(logindata); */

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
		m.addAttribute("blogs", blogRepository.getBlogs());
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

			session.setAttribute("message", new Message("Successfully registered !!", "alert-success"));
			// boolean flag=this.mailService.sendEmail(subject, mess, email);
			// if(flag)
			// {
			// session.setAttribute("email", email);

			// }else
			// {
			// session.setAttribute("message", new Message("mail not send check your email
			// !!","alert-success"));

			// }
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
			ContactUs u = this.contactRepo.save(cantactdata);
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

}
