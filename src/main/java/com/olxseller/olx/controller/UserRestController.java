package com.olxseller.olx.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.model.Blog;
import com.olxseller.olx.model.User;
import com.olxseller.olx.model.UserAddress;
import com.olxseller.olx.service.BlogService;
import com.olxseller.olx.service.UserAddressService;
import com.olxseller.olx.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {
  
  @Autowired
  private UserService userService;
  @Autowired
  private UserAddressService addressService;
  @Autowired
  public ResponseData responseData;
	@Autowired
	private BlogService blogService;

  	@PostMapping("/info")
	public ResponseEntity<?> createUpdateUserInfo(@RequestBody User user,Principal principal) {
		System.out.println("user:" + user);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		// String dat = sdf.format(new Date());
		try {
      // user.setId(userService.findUserByEmail(principal.getName()).getId());
			if (user.getId() > 0) {
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", userService.updateUser(user, userService.findUserByEmail(principal.getName()).getId())),
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
	public ResponseEntity<?> changePassword(@RequestBody User user,Principal principal) {
		System.out.println("user password:" + user);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		// String dat = sdf.format(new Date());
		try {
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", userService.updatePassword(user, userService.findUserByEmail(principal.getName()).getId())),
						HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
  	@PostMapping("/Address/create")
	public ResponseEntity<?> createUpdateUserAddress(@RequestBody UserAddress address,Principal principal) {
		System.out.println("social:" + address);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		// String dat = sdf.format(new Date());
		try {
      address.setUser(userService.findUserByEmail(principal.getName()));
			System.out.println("social id :" + address.getId());
			if (address.getId() > 0) {
				UserAddress add = addressService.AddAddress(address);
				System.out.println("updated" + add);
				return new ResponseEntity<>(responseData.jsonSimpleResponse("SUCCESS", "Successfuly Update", "UPDATE", addressService.UpdateAddress(address,address.getId(), 0)),
						HttpStatus.OK);
			}
			return new ResponseEntity<>(
					responseData.jsonSimpleResponse("SUCCESS", "Successfuly Created", "CREATE", addressService.AddAddress(address)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
  	@PostMapping("/Blog/create")
	public ResponseEntity<?> createUpdateArticle(@RequestBody Blog blog,Principal principal) {
		// System.out.println("blog:" + blog);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		String dat = sdf.format(new Date());
		User user=userService.findUserByEmail(principal.getName());
		blog.setUser(user);
		blog.setUpdate_at(dat);
		try {
			if (blog.getId() > 0) {
				return new ResponseEntity<>(
					responseData.jsonSimpleBlogResponse("SUCCESS", "Successfuly Update", "UPDATE",blogService.updateBlogs(blog, blog.getId())),
					HttpStatus.OK);
				}
				blog.setCreate_at(dat); 
				return new ResponseEntity<>(
					responseData.jsonSimpleBlogResponse("SUCCESS", "Successfuly Created", "CREATE", blogService.addBlogs(blog)),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// return null;
	}
}
