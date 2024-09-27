package com.olxseller.olx.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olxseller.olx.helper.ResponseData;
import com.olxseller.olx.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {
  
  @Autowired
  private UserService userService;
  @Autowired
  public ResponseData responseData;

}
