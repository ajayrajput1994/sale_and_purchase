package com.olxseller.olx.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.DTO.UserAddressDTO;
import com.olxseller.olx.config.MyConfig;
import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.User;
import com.olxseller.olx.model.UserAddress;
import com.olxseller.olx.service.BlogService;
import com.olxseller.olx.service.UserAddressService;
import com.olxseller.olx.service.UserService;

@RestController
@RequestMapping("/Api")
public class ApiController {
	@Autowired
	public MyConfig myConfig;
	@Autowired
	public ResponseData responseData;
	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private UserAddressService addressService;

	@GetMapping("/home")
	public ResponseEntity<?> getHomeData() {
		System.out.println("Prepare home data....");
		try {
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Loaded", "LOADED", blogService.getAllBlogs()),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/MobLogin")
	public ResponseEntity<?> UserLogin(@RequestParam("email") String email, @RequestParam("password") String password) {
		System.out.println("tracking user...." + email);
		System.out.println("tracking user...." + password);
		User user = userService.findUserByEmail(email);
		try {
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Logged in", "LOADED", user),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// return null;
	}

	@PostMapping("/MobSignup")
	public ResponseEntity<?> UserSignup(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("phone") String phone) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		String dat = sdf.format(new Date());
		System.out.println("name:" + name);
		System.out.println("email:" + email);
		System.out.println("phone:" + phone);
		System.out.println("password:" + password);
		try {
			User user = new User(email, myConfig.passwordEncoder().encode(password), dat, dat);
			user.setName(name);
			// user.setEmail(email);
			user.setPhone(phone);
			user.setAgreed(true);
			user.setEnabled(true);
			user.setRole("ROLE_USER");
			user.setImage("default.png");
			user.setOther_phone("");
			user.setPasswordStr(user.getPassword());
			user.setPassword(myConfig.passwordEncoder().encode(user.getPassword()));
			user.setCreate_at(dat);
			user.setUpdate_at(dat);
			// user = this.userService.createUser(user);
			System.out.println("user creating....." + user);
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "New Account Create Successfuly", "LOADED", user),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/Address/create")
	public ResponseEntity<?> createUpdateUserAddress(@RequestBody UserAddressDTO address, Principal principal) {
		System.out.println("social:" + address);
		System.out.println("principal.getName():" + principal.getName());
		System.out.println(userService.findUserByEmail(principal.getName()));
		address.setUserId(userService.findUserByEmail(principal.getName()).getId());
		address.setActive("");
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		// String dat = sdf.format(new Date());
		try {
			System.out.println("address id :" + address.getId());
			String action = "UPDATE";
			if (address.getId() == 0) {
				action = "CREATE";
			}
			address = addressService.createAddress(address);
			System.out.println("address:" + address.toString());
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly " + action, action, address),
					action == "CREATE" ? HttpStatus.CREATED : HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println("Error:" + address.toString() + "" + ex);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@PostMapping("/info")
	public ResponseEntity<?> createUpdateUserInfo(@RequestBody User user, Principal principal) {
		System.out.println("user:" + user);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		// String dat = sdf.format(new Date());
		try {
			// user.setId(userService.findUserByEmail(principal.getName()).getId());
			if (user.getId() > 0) {
				return new ResponseEntity<>(
						responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
								userService.updateUser(user, userService.findUserByEmail(principal.getName()).getId())),
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

	@PostMapping("/password")
	public ResponseEntity<?> changePassword(@RequestBody User user, Principal principal) {
		System.out.println("user password:" + user);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		// String dat = sdf.format(new Date());
		try {
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE",
							userService.updatePassword(user, userService.findUserByEmail(principal.getName()).getId())),
					HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}

	@GetMapping("/Address/{id}")
	public ResponseEntity<?> setDefaultAddress(@PathVariable("id") int id) {
		System.out.println("setDefaultAddress:" + id);
		try {
			addressService.setDefaultAddress(id);
			return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", id),
					HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
}
