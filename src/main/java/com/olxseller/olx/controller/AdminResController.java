package com.olxseller.olx.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.City;
import com.olxseller.olx.model.MainCategory;
import com.olxseller.olx.model.RegionState;
import com.olxseller.olx.model.SubCategory;
import com.olxseller.olx.model.User;
import com.olxseller.olx.model.WebPage;
import com.olxseller.olx.model.WebSiteAddress;
import com.olxseller.olx.service.CategoryService;
import com.olxseller.olx.service.CityService;
import com.olxseller.olx.service.GenricService;
import com.olxseller.olx.service.StateService;
import com.olxseller.olx.service.SubCategoryService;
import com.olxseller.olx.service.UserService;
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
	private GenricService<WebSiteAddress> webservice;
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
			MainCategory category = catService.getCategoryById(id);
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
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", subcatService.updateSubCategory(sub, sub.getSubId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", subcatService.createSubCategory(sub)),
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
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", stateService.updaateState(st, st.getStateId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", stateService.createState(st)),
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
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", cityService.updateCity(city, city.getCityId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", cityService.createCity(city)),
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
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", pageService.updateWebPage(page,page.getId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", pageService.creatWebPage(page)),
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
	public ResponseEntity<?> createUpdateUser(@RequestBody User  user) {
		System.out.println("User:" + user);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    String dat = sdf.format(new Date());
    user.setCreate_at(dat);
    user.setUpdate_at(dat);
		try {
			if (user.getId() > 0) {
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", userService.updateUser(user, user.getId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", userService.createUser(user)),
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

	@PostMapping("/address/create")
	public ResponseEntity<?> createUpdateWebsiteAddress(@RequestBody WebSiteAddress  address) {
		System.out.println(address);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    String dat = sdf.format(new Date());
		try {
			if (address.getId() > 0) {
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", webservice.update(address, address.getId())),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", webservice.create(address)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@GetMapping("/address/delete/{id}")
	public ResponseEntity<?> deleteWebSiteAddressById(@PathVariable("id") int id) {
		try {
			webservice.delete(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Deleted", "DELETE", id),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
}
