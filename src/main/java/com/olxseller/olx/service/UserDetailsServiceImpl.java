package com.olxseller.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.olxseller.olx.config.CustomUserDetails;
import com.olxseller.olx.config.MyConfig;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private MyConfig myConfig;
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepo.getUserByUserName(username);
		if(user == null) {
		 throw new UsernameNotFoundException("could not found user !!");
		}
    System.out.println("Password matches: " + myConfig.passwordEncoder().matches(user.getPasswordStr(), user.getPassword()));
		return new CustomUserDetails(user);
	}

}
