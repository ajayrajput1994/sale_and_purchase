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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.olxseller.olx.helper.Message;
import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.Banner;
import com.olxseller.olx.model.Blog;
import com.olxseller.olx.model.City;
import com.olxseller.olx.model.HomeSeo;
import com.olxseller.olx.model.Logo;
import com.olxseller.olx.model.MainCategory;
import com.olxseller.olx.model.RegionState;
import com.olxseller.olx.model.SubCategory;
import com.olxseller.olx.model.User;
import com.olxseller.olx.model.WebPage;
import com.olxseller.olx.model.WebSiteAddress;
import com.olxseller.olx.model.WebSiteSocial;
import com.olxseller.olx.repository.BannerRepository;
import com.olxseller.olx.repository.BlogRepository;
import com.olxseller.olx.repository.CityRepository;
import com.olxseller.olx.repository.HomeSeoRepository;
import com.olxseller.olx.repository.LogoRepository;
import com.olxseller.olx.repository.MainCatRepository;
import com.olxseller.olx.repository.RegionStateRepository;
import com.olxseller.olx.repository.SubCatRepository;
import com.olxseller.olx.repository.UserRepository;
import com.olxseller.olx.repository.WebPageRepositoy;
import com.olxseller.olx.repository.WebSiteAddressRepository;
import com.olxseller.olx.repository.WebSiteSocialRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private MainCatRepository mainRepo;

	@Autowired
	private SubCatRepository subRepo;

	@Autowired
	private RegionStateRepository stateRepo;

	@Autowired
	private CityRepository cityRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BlogRepository blogRepo;

	@Autowired
	private HomeSeoRepository homeRepo;

	@Autowired
	private WebSiteAddressRepository websiteRepo;

	@Autowired
	private WebSiteSocialRepository websocialRepo;

	@Autowired
	private WebPageRepositoy webpageRepo;

	@Autowired
	private LogoRepository logoRepo;

	@Autowired
	private BannerRepository bannerRepo;

	@ModelAttribute
	public void getCommonData(Model m) {
		Logo logo = this.logoRepo.getSiteLogo();
		if (logo == null) {
			m.addAttribute("homelogo", new Logo());
		} else {
			m.addAttribute("homelogo", logo);
		}
		m.addAttribute("maincat", new MainCategory());
		m.addAttribute("subcat", new SubCategory());
		m.addAttribute("webpage", new WebPage());
		// all main calegories
		List<MainCategory> mainCats = this.mainRepo.getMainCatalogs();
		m.addAttribute("mainCates", mainCats);

		// all state
		List<RegionState> regstate = this.stateRepo.getAllStates();
		m.addAttribute("allstates", regstate);

	}

	@GetMapping("/")
	public String dashboard(Model m) {
		m.addAttribute("ttl", "admin dashboard");
		return "admin/admin_dashboard";
	}

	@GetMapping("/home-seo")
	public String homeSeo(Model m) {
		m.addAttribute("title", "home setup ");
		try {
			HomeSeo home = this.homeRepo.getHomeSeo();
			WebSiteAddress address = this.websiteRepo.getSiteAddress();
			WebSiteSocial social = this.websocialRepo.getWebSocial();
			Banner banner = this.bannerRepo.getHomeBanner();
			m.addAttribute("homeseo", home == null ? new HomeSeo() : home);
			m.addAttribute("webaddress", address == null ? new WebSiteAddress() : address);
			m.addAttribute("websocial", social == null ? new WebSiteSocial() : social);
			m.addAttribute("homebanner", banner == null ? new Banner() : banner);

		} catch (Exception e) {
			System.out.println(e);
		}
		return "admin/homeSeo";
	}

	@GetMapping("/hello")
	public String demo(Model m) {
		m.addAttribute("ttl", "this is layout demo");
		m.addAttribute("desc", "this is description");
		return "fragments/demo";
	}

	@GetMapping("/pages-setup")
	public String pageSetup(Model m) {
		m.addAttribute("title", "page setup");
		return "admin/pageSetup";
	}

	@GetMapping("/all-users/{page}")
	public String AllUser(@PathVariable("page") Integer page, Model m) {
		m.addAttribute("title", "all users");
		Pageable pageable = PageRequest.of(page, 6);
		Page<User> users = this.userRepo.getAllUsers(pageable);
		m.addAttribute("users", users);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalpage", users.getTotalPages());
		return "admin/allUser";
	}

	@GetMapping("/all-posts/{page}")
	public String AllPost(@PathVariable("page") Integer page, Model m) {
		m.addAttribute("title", "all posts");
		Pageable pageable = PageRequest.of(page, 6);
		Page<Blog> blogs = this.blogRepo.getAllBlogs(pageable);
		m.addAttribute("blogs", blogs);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalpage", blogs.getTotalPages());
		return "admin/allPost";
	}

	@GetMapping("/post/delete/{id}")
	public String deletepost(@PathVariable("id") Integer id, Model m, HttpSession session) {
		Optional<Blog> optionalblog = this.blogRepo.findById(id);
		Blog blog = optionalblog.get();
		blog.setUser(null);
		this.blogRepo.delete(blog);
		session.setAttribute("message", new Message("delete blog successfully", "alert-success"));
		return "redirect:/admin/all-posts/0";
	}


	// main catalogs
	@GetMapping("/categories/{page}")
	public String AllCategories(@PathVariable("page") Integer page, Model m) {
		ResponseData responseData=new ResponseData();
		m.addAttribute("title", "all catalogs");
		Pageable pageable = PageRequest.of(page, 6);
		Page<MainCategory> catalog = this.mainRepo.getAllCatalogs(pageable);
		m.addAttribute("catalogs", catalog);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalpage", catalog.getTotalPages());
		m.addAttribute("maincat", new MainCategory());
		m.addAttribute("demodata", responseData.jsonSimpleResponse("","","",mainRepo.findAll()));
		return "admin/categories";
	}
	
	// sub catalogs
	@GetMapping("/sub-category/{page}")
	public String subCategories(@PathVariable("page") Integer page, Model m) {
		m.addAttribute("title", "all catalogs");
		Pageable pageable = PageRequest.of(page, 6);
		Page<SubCategory> catalog = this.subRepo.getAllSubCatalogs(pageable);
		m.addAttribute("catalogs", catalog);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalpage", catalog.getTotalPages());
		m.addAttribute("subcat", new SubCategory());
		List<MainCategory> mainCats = this.mainRepo.getMainCatalogs();
		m.addAttribute("mainCategories", mainCats);
		return "admin/subcategory";
	}


	// all states
	@GetMapping("/all-states/{page}")
	public String AllStates(@PathVariable("page") Integer page, Model m) {
		m.addAttribute("title", "all states");
		Pageable pageable = PageRequest.of(page, 6);
		Page<RegionState> locate = this.stateRepo.getAllRegionState(pageable);
		m.addAttribute("locate", locate);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalpage", locate.getTotalPages());
		m.addAttribute("regionState", new RegionState());
		return "admin/allStates";

	}


	// all cities
	@GetMapping("/all-cities/{page}")
	public String Allcities(@PathVariable("page") Integer page, Model m) {
		m.addAttribute("title", "all Cities");
		Pageable pageable = PageRequest.of(page, 6);
		Page<City> locate = this.cityRepo.getAllCity(pageable);
		m.addAttribute("locate", locate);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalpage", locate.getTotalPages());
		m.addAttribute("cities", new City());
		List<RegionState> regstate = this.stateRepo.getAllStates();
		m.addAttribute("allstates", regstate);
		return "admin/allCities";

	}

	// open form to edit post
	@PostMapping("/post/edit/{id}")
	public String updateblog(@PathVariable("id") Integer id, Model m, HttpSession session, Principal principal) {
		// Optional<Blog> optionalblog=this.blogRepo.findById(id);
		Blog blog = this.blogRepo.findById(id).get();
		m.addAttribute("blog", blog);
		session.setAttribute("useremail", blog.getUser().getEmail());
		return "admin/updateblog";
	}

	// update post
	@PostMapping("/post/update")
	public String deletepost(@ModelAttribute("blog") Blog blog, Principal principal,
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
				return "redirect:/admin/all-posts/0";
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

			} else {
				blog.setImage6("");
			}
			blog.setCreate_at(dat);
			blog.setUpdate_at(dat);
			// check login user
			String username = session.getAttribute("useremail").toString();
			User user = this.userRepo.getUserByUserName(username);
			blog.setUser(user);
			this.blogRepo.save(blog);
			session.setAttribute("useremail", "");
			session.setAttribute("message", new Message("update blog successfully", "alert-success"));

			return "redirect:/admin/all-posts/0";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("message", new Message("Something went wrong ! try again !", "alert-danger"));
		}
		return "redirect:/admin/all-posts/0";
	}

	// create new main catalogs
	@PostMapping("/create_maincategory")
	public String createNewMainCategory(@RequestBody MainCategory maincategory, Model m,
			HttpSession session) {
		System.out.println("category:"+maincategory);
		String pageurl = "admin/categories";
		try {
			// System.out.println(maincategory);
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return pageurl;
			// }
			maincategory.setImage("default.png");
			maincategory.setPath(maincategory.getMainCatalog());

			MainCategory mainCatalog = this.mainRepo.save(maincategory);

			m.addAttribute("maincat", new MainCategory());

			session.setAttribute("message", new Message("Successfully submited !!", "alert-success"));

			return pageurl;
		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("maincat", maincategory);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

	// create new sub catalog
	@PostMapping("/create_subcategory")
	public String createNewSubCategory(@ModelAttribute("subcat") SubCategory subcategory, Model m, HttpSession session) {
		String pageurl = "admin/subcategory";
		try {
			// System.out.println(subcategory);
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return pageurl;
			// }
			subcategory.setImage("default.png");
			subcategory.setPath(subcategory.getSubCatalog());

			SubCategory sub = this.subRepo.save(subcategory);

			m.addAttribute("subcat", new SubCategory());

			session.setAttribute("message", new Message("Successfully submited !!", "alert-success"));

			return pageurl;
		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("subcat", subcategory);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

	// create new state
	@PostMapping("/create_regionState")
	public String createNewState(@ModelAttribute("regionState") RegionState regionState, Model m, HttpSession session) {
		String pageurl = "admin/allStates";
		try {
			// System.out.println(RegionState);
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return pageurl;
			// }
			regionState.setImage("default.png");
			regionState.setPath(regionState.getStateName());

			RegionState region = this.stateRepo.save(regionState);

			m.addAttribute("regionState", new RegionState());

			session.setAttribute("message", new Message("Successfully submited !!", "alert-success"));

			return pageurl;
		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("regionState", regionState);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

	// create new city
	@PostMapping("/create_city")
	public String createNewCity(@ModelAttribute("cities") City cities, Model m, HttpSession session) {
		String pageurl = "admin/allCities";
		try {
			// System.out.println(cities);
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return pageurl;
			// }
			cities.setImage("default.png");
			cities.setPath(cities.getCityName());

			City c = this.cityRepo.save(cities);

			m.addAttribute("cities", new City());

			session.setAttribute("message", new Message("Successfully submited !!", "alert-success"));

			return pageurl;
		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("cities", cities);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

	// create new page
	@PostMapping("/create_webpage")
	public String createNewWebPage(@ModelAttribute("webpage") WebPage webpage, Model m, HttpSession session) {
		String pageurl = "admin/pageSetup";
		try {
			// System.out.println(cities);
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return pageurl;
			// }
			webpage.setImage("default.png");
			webpage.setPath(webpage.getName());

			WebPage c = this.webpageRepo.save(webpage);

			m.addAttribute("webpage", new WebPage());

			session.setAttribute("message", new Message("Successfully submited !!", "alert-success"));

			return pageurl;
		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("webpage", webpage);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

	// Setup home Seo
	@PostMapping("/setup_homeSeo")
	public String setupHomeSeo(@ModelAttribute("homeseo") HomeSeo homeseo, Model m, HttpSession session) {
		String pageurl = "redirect:/admin/home-seo";
		try {
			// System.out.println(subcategory);
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return pageurl;
			// }
			homeseo.setId(1);
			HomeSeo sub = this.homeRepo.save(homeseo);

			m.addAttribute("homeseo", new HomeSeo());

			session.setAttribute("message", new Message("Successfully submited !!", "alert-success"));

			return pageurl;
		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("homeseo", homeseo);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

	// Setup home Address
	@PostMapping("/setup_homeAddress")
	public String setupHomeAddress(@ModelAttribute("webaddress") WebSiteAddress webaddress, Model m,
			HttpSession session) {
		String pageurl = "redirect:/admin/home-seo";
		try {
			// System.out.println(subcategory);
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return pageurl;
			// }
			webaddress.setId(1);
			WebSiteAddress sub = this.websiteRepo.save(webaddress);

			m.addAttribute("webaddress", new WebSiteAddress());

			session.setAttribute("message", new Message("Successfully submited !!", "alert-success"));

			return pageurl;
		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("webaddress", webaddress);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

	// Setup home Address
	@PostMapping("/setup_homesocial")
	public String setupHomeSocial(@ModelAttribute("websocial") WebSiteSocial websocial, Model m, HttpSession session) {
		String pageurl = "redirect:/admin/home-seo";
		try {
			// System.out.println(subcategory);
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return pageurl;
			// }
			websocial.setId(1);
			WebSiteSocial sub = this.websocialRepo.save(websocial);

			m.addAttribute("websocial", new WebSiteSocial());

			session.setAttribute("message", new Message("Successfully submited !!", "alert-success"));

			return pageurl;
		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("websocial", websocial);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

	// Setup home log
	@PostMapping("/setup_homeLogo")
	public String setupHomeLogo(@ModelAttribute("homelogo") Logo homelogo, Model m, HttpSession session) {
		String pageurl = "redirect:/admin/home-seo";
		try {
			MultipartFile logo = homelogo.getMultipartfile();
			// System.out.println(subcategory);
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return pageurl;
			// }
			if (logo.isEmpty()) {
				return pageurl;
			} else {
				// first image file
				homelogo.setLogo(logo.getOriginalFilename());
				File savefile = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + logo.getOriginalFilename());
				Files.copy(logo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			homelogo.setId(1);
			Logo sub = this.logoRepo.save(homelogo);

			m.addAttribute("homelogo", new Logo());

			session.setAttribute("message", new Message("Successfully submited !!", "alert-success"));

			return pageurl;
		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("homelogo", homelogo);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

	// Setup home log
	@PostMapping("/setup_homebanner")
	public String setupHomeBanner(@ModelAttribute("homebanner") Banner homebanner, Model m, HttpSession session) {
		String pageurl = "redirect:/admin/home-seo";
		try {
			MultipartFile image = homebanner.getMultipartfile();
			// System.out.println(subcategory);
			// if(result.hasErrors()) {
			// System.out.print(result);
			// return pageurl;
			// }
			if (image.isEmpty()) {
				return pageurl;
			} else {
				// first image file
				homebanner.setBanner(image.getOriginalFilename());
				File savefile = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + image.getOriginalFilename());
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			homebanner.setId(1);
			Banner sub = this.bannerRepo.save(homebanner);

			m.addAttribute("homebanner", new Banner());

			session.setAttribute("message", new Message("Successfully submited !!", "alert-success"));

			return pageurl;
		} catch (Exception e) {
			e.printStackTrace();

			m.addAttribute("homebanner", homebanner);
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "alert-danger"));

			return pageurl;
		}

	}

}
