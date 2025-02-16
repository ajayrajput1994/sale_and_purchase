package com.olxseller.olx.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Override
	protected void handle(HttpServletRequest request,HttpServletResponse response,Authentication authentication) throws IOException {
		
		String target=determineTargetUrl(authentication);
		System.out.println("Target URL: " + target); 
		if(response.isCommitted()) {
			return;
		}
		RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request, response, target);
		
	}
	
	protected String determineTargetUrl(Authentication authentication) {
		String url="/login?error=true"; 
		System.out.println("login user: "+authentication.getName());   
		Collection<? extends GrantedAuthority> authorties=authentication.getAuthorities();
		List<String> roles=new ArrayList<String>();
		for(GrantedAuthority a: authorties) {
			roles.add(a.getAuthority());
		}
		//check user role and decide the redirect url
		System.out.println("role:"+roles);
		if(roles.contains("ROLE_USER")) {
			url="/user/";
		}
		else if(roles.contains("ROLE_ADMIN")) {
			url="/admin/";
		}
		return url;
		
	}
}
