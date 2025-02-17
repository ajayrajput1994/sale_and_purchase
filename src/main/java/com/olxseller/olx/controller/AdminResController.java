package com.olxseller.olx.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import com.olxseller.olx.service.BlogService;
import com.olxseller.olx.service.CategoryService;
import com.olxseller.olx.service.CityService;
import com.olxseller.olx.service.LogoService;
import com.olxseller.olx.service.SeoService;
import com.olxseller.olx.service.SocialService;
import com.olxseller.olx.service.StateService;
import com.olxseller.olx.service.SubCategoryService;
import com.olxseller.olx.service.UserService;
import com.olxseller.olx.service.WebAddressService;
import com.olxseller.olx.service.WebPageService;

@RestController
@RequestMapping("/admin")
public class AdminResController {
	@Autowired
	public ResponseData responseData;
	@Autowired
	private CategoryService catService;
	@Autowired
	private SubCategoryService subcatService;
	@Autowired
	private StateService stateService;
	@Autowired
	private CityService cityService;
	@Autowired
	private WebPageService pageService;
	@Autowired
	private UserService userService;
	@Autowired
	private WebAddressService webservice;
	@Autowired
	private SeoService seoservice;
	@Autowired
	private SocialService socialService;
	@Autowired
	private LogoService logoService;
	@Autowired
	private BlogService blogService;

	@PostMapping("/category/create")
	public ResponseEntity<?> createCategory(@RequestBody MainCategory cat) {
		System.out.println("category:" + cat);
		try {
			if (cat.getMainId() > 0) {
				catService.updateCategory(cat, cat.getMainId());
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", cat),
						HttpStatus.OK);
			}
			MainCategory category = catService.CreateMainCategory(cat);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", category),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable("id") int id) {
		System.out.println("category id:" + id);
		try {
			catService.getCategoryById(id);
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Loaded", "LOADED", catService.getAllMainCategory()),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/category/update/{id}")
	public ResponseEntity<?> updateCategoryById(@PathVariable("id") int id, @RequestBody MainCategory cat) {
		// System.out.println("category:"+cat);
		try {
			catService.updateCategory(cat, id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", ""),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@GetMapping("/category/delete/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable("id") int id) {
		// System.out.println("category:"+cat);
		try {
			catService.deleteCategoryById(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Deleted", "DELETE", id),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/sub-category/create")
	public ResponseEntity<?> createUpdateSubCategory(@RequestBody SubCategory sub) {
		System.out.println("sub-category:" + sub);
		try {
			if (sub.getSubId() > 0) {
				return new ResponseEntity<>(
						responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
								subcatService.updateSubCategory(sub, sub.getSubId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE",
							subcatService.createSubCategory(sub)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@GetMapping("/sub-category/delete/{id}")
	public ResponseEntity<?> deleteSubCategoryById(@PathVariable("id") int id) {
		// System.out.println("category:"+cat);
		try {
			subcatService.deleteSubcategory(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Deleted", "DELETE", id),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/states/create")
	public ResponseEntity<?> createUpdateStates(@RequestBody RegionState st) {
		System.out.println("State:" + st);
		try {
			if (st.getStateId() > 0) {
				return new ResponseEntity<>(
						responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
								stateService.updaateState(st, st.getStateId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", stateService.createState(st)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@GetMapping("/states/delete/{id}")
	public ResponseEntity<?> deleteStatesById(@PathVariable("id") int id) {
		// System.out.println("category:"+cat);
		try {
			stateService.deleteState(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Deleted", "DELETE", id),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/city/create")
	public ResponseEntity<?> createUpdateCity(@RequestBody City city) {
		System.out.println("City:" + city);
		try {
			if (city.getCityId() > 0) {
				return new ResponseEntity<>(
						responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
								cityService.updateCity(city, city.getCityId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", cityService.createCity(city)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@GetMapping("/city/delete/{id}")
	public ResponseEntity<?> deleteCityById(@PathVariable("id") int id) {
		// System.out.println("category:"+cat);
		try {
			cityService.deleteCity(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Deleted", "DELETE", id),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/page/create")
	public ResponseEntity<?> createUpdateWebPage(@RequestBody WebPage page) {
		System.out.println("City:" + page);
		try {
			if (page.getId() > 0) {
				return new ResponseEntity<>(
						responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
								pageService.updateWebPage(page, page.getId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", pageService.creatWebPage(page)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@GetMapping("/page/delete/{id}")
	public ResponseEntity<?> deleteWebPageById(@PathVariable("id") int id) {
		// System.out.println("category:"+cat);
		try {
			pageService.deleteWebPage(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Deleted", "DELETE", id),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/user/create")
	public ResponseEntity<?> createUpdateUser(@RequestBody User user) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		String dat = sdf.format(new Date());
		user.setCreate_at(dat);
		user.setUpdate_at(dat);
		System.out.println("User:" + user);
		try {
			if (user.getId() > 0) {
				return new ResponseEntity<>(
						responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
								userService.updateUser(user, user.getId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", userService.createUser(user)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@GetMapping("/user/delete/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) {
		// System.out.println("category:"+cat);
		try {
			// service.delete(id);
			userService.deleteUser(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Deleted", "DELETE", id),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
@PostMapping("/user/password")
	public ResponseEntity<?> changePassword(@RequestBody User user,Principal principal) {
		System.out.println("user password:" + user);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		// String dat = sdf.format(new Date());
		try {
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", userService.updatePassword(user, user.getId())),
						HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
	@PostMapping("/webaddress/create")
	public ResponseEntity<?> createUpdateWebsiteAddress(@RequestBody WebSiteAddress address) {
		System.out.println("webaddress:" + address);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		// String dat = sdf.format(new Date());
		try {
			System.out.println("address :" + address.getId());
			// if (address.getId() > 0) {
			WebSiteAddress webSiteAddress = webservice.update(address, address.getId());
			System.out.println("updated" + webSiteAddress);
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", webSiteAddress),
					HttpStatus.OK);
			// }
			// return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS",
			// "Successfuly Created", "CREATE", webservice.create(address)),
			// HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/seo/create")
	public ResponseEntity<?> createUpdateWebsiteSeo(@RequestBody HomeSeo seo) {
		System.out.println("webaddress:" + seo);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		// String dat = sdf.format(new Date());
		try {
			System.out.println("seo id:" + seo.getId());
			if (seo.getId() > 0) {
				HomeSeo webseo = seoservice.update(seo, seo.getId());
				System.out.println("updated" + webseo);
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", webseo),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", seoservice.create(seo)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/social/create")
	public ResponseEntity<?> createUpdateWebsiteSocial(@RequestBody WebSiteSocial social) {
		System.out.println("social:" + social);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		// String dat = sdf.format(new Date());
		try {
			System.out.println("social id :" + social.getId());
			if (social.getId() > 0) {
				WebSiteSocial webseo = socialService.update(social, social.getId());
				System.out.println("updated" + webseo);
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", webseo),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", socialService.create(social)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/logo/update")
	public ResponseEntity<?> createUpdateWebsiteLogo(@RequestBody Logo logo) {
		try {
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
							logoService.updateLogo(logo, logo.getId())),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} 
	}

	@PostMapping("/banner/update")
	public ResponseEntity<?> createUpdateWebsiteBanner(@RequestBody Banner banner) {
		try {
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
							logoService.updateBanner(banner, banner.getId())),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} 
	}

	@PostMapping("/blog/create/{userid}")
	public ResponseEntity<?> createUpdateBlog(@PathVariable("userid") Integer userid,@RequestBody Blog b) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		String dat = sdf.format(new Date());
		User user=userService.getUserByID(userid);
		b.setUser(user);
		b.setCreate_at(dat);
		b.setUpdate_at(dat);  
		try {
			if (b.getId() > 0) {
				return new ResponseEntity<>(
						responseData.jsonSimpleBlogResponse("SUCCESS", "Successfuly Update", "UPDATE",blogService.updateBlogs(b, b.getId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleBlogResponse("SUCCESS", "Successfuly Created", "CREATE", blogService.addBlogs(b)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@GetMapping("/blog/delete/{id}")
	public ResponseEntity<?> deleteBlogById(@PathVariable("id") int id) {
		// System.out.println("category:"+cat);
		try {
			// service.delete(id);
			blogService.deleteBlogs(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Deleted", "DELETE", id),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

}
