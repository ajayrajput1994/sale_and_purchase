package com.olxseller.olx.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.olxseller.olx.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class MyConfig{
	
	@Autowired
	private CustomLoginSuccessHandler successhandler;

	@Autowired
    private DataSource dataSource;
		
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
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
		
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		String[] adminAccess={"/admin/**","/user/**"};
		http
				.csrf(csrf-> csrf.disable())
				.cors(cors-> cors.disable())
				.authorizeRequests(auth -> auth
					.antMatchers(adminAccess).hasRole("ADMIN")
					.antMatchers("/user/**").hasRole("USER")
					.antMatchers("/**").permitAll())
				.formLogin(form -> form
					.loginPage("/signin").loginProcessingUrl("/login_process").successHandler(successhandler))
				.authenticationProvider(authenticatinProvider())
				.sessionManagement(session-> session.maximumSessions(1)
				.expiredUrl("/signin?expired-session")
				.maxSessionsPreventsLogin(true))
				.sessionManagement()
				.sessionFixation().none()
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.and()
				.rememberMe()
						.rememberMeParameter("remember-me")
						.key("uniqueAndSecret")
						.tokenValiditySeconds(31536000) // 1 day 86400 // 1 year 31536000
						.tokenRepository(persistentTokenRepository());
		return http.build();
	}
	
	
}
