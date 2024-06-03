package com.olxseller.olx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.olxseller.olx.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class MyConfig{
	
	@Autowired
	private CustomLoginSuccessHandler successhandler;

	@Bean
	public UserDetailsService getUserDetailsSerice() { return new
	UserDetailsServiceImpl(); }
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	
	return new BCryptPasswordEncoder(10); }
	
	@Bean
	public DaoAuthenticationProvider authenticatinProvider() {
	DaoAuthenticationProvider daoAuthenticationProvider=new
	DaoAuthenticationProvider();
	
	daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsSerice());
	daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
	
	return daoAuthenticationProvider;
	
	}
	
		//Configure method
	
	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws
	// Exception { auth.authenticationProvider(this.authenticatinProvider());
	
	// }
	
	//configure http method
	
	// @Override protected void configure(HttpSecurity http) throws Exception { http
	// .authorizeRequests() .antMatchers("/admin/**").hasRole("ADMIN")
	// .antMatchers("/user/**").hasRole("USER") 
	// .antMatchers("/**").permitAll()
	// .and().formLogin().loginPage("/signin").loginProcessingUrl("/login_process").successHandler(successhandler)
	// .and().csrf().disable(); }
//	  .loginPage("/signin").loginProcessingUrl("/login_process") .defaultSuccessUrl("/users/index")
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception{
		return auth.getAuthenticationManager();
	}
  
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
				.csrf(csrf-> csrf.disable())
				.cors(cors-> cors.disable())
				.authorizeRequests(auth -> auth
					.antMatchers("/admin/**").hasRole("ADMIN")
					.antMatchers("/user/**").hasRole("USER")
					.antMatchers("/**").permitAll())
				.formLogin(form -> form
					.loginPage("/signin").loginProcessingUrl("/login_process").successHandler(successhandler))
				.authenticationProvider(authenticatinProvider());
		return http.build();
	}
	
}
