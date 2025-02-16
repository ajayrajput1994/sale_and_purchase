package com.olxseller.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.olxseller.olx.config.CustomUserDetails;
import com.olxseller.olx.model.User;
import com.olxseller.olx.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepo.getUserByUserName(username);
		if(user == null) {
		 throw new UsernameNotFoundException("could not found user !!");
		}
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String rawPassword = "1234"; 
        String encodedPassword = user.getPassword(); // Get the hashed password from the user object
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("Password matches: " + matches);
		return new CustomUserDetails(user);
	}

}
