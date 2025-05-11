package com.olxseller.olx.config;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
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
	 
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception{
		return auth.getAuthenticationManager();
	}
  
		
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		// String[] adminAccess={"/admin/**","/user/**"};
		// .antMatchers(adminAccess).hasRole("ADMIN")
		http
				.csrf(csrf-> csrf.disable())
				.cors(cors-> cors.disable())
				.authorizeRequests(auth -> auth
				.antMatchers("/user/**").hasRole("USER")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/Customer/**").hasRole("CUSTOMER")
				.antMatchers("/**").permitAll())
				.formLogin(form -> form
					.loginPage("/signin").loginProcessingUrl("/login_process").successHandler(successhandler))
				.authenticationProvider(authenticatinProvider())
				.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/signin?invalid-session")
                .maximumSessions(1)
                .expiredUrl("/signin?expired-session")
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry())
                .and()
                .sessionFixation().migrateSession());

			return http.build();
	}
	
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	
}
