package com.olxseller.olx.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.config.MyConfig;
import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.User;
import com.olxseller.olx.service.BlogService;
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
	public ResponseEntity<?> UserLogin(@RequestParam("email") String email,@RequestParam("password") String password) {
		System.out.println("tracking user...."+email);
		System.out.println("tracking user...."+password);
		User user= userService.findUserByEmail(email);  
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
	public ResponseEntity<?> UserSignup(@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("name") String name,@RequestParam("phone") String phone) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		String dat = sdf.format(new Date());
		System.out.println("name:"+name);
		System.out.println("email:"+email);
		System.out.println("phone:"+phone);
		System.out.println("password:"+password);
		try { 
		User user=new User(email,myConfig.passwordEncoder().encode(password),dat,dat);  
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
		System.out.println("user creating....."+user);
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "New Account Create Successfuly", "LOADED", user),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	} 
  
}
