package com.ekam.spring.security.multiAuthSecurityApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.ekam.spring.security.multiAuthSecurityApp.services.UserService;

@Configuration
@Order(2)
public class FormLoginSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserService userService;

	public FormLoginSecurityConfig(UserService userService) {
		this.userService = userService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http.authorizeRequests()
		.antMatchers("/favicon.ico","/webjars/**", "/images/**", "/js/**","/css/**","/error")
			.permitAll()
			.anyRequest().authenticated();
		
		http.formLogin()
			.loginPage("/internal/login")
				.permitAll()
			.defaultSuccessUrl("/landing", true)
		.and()
			.logout().logoutUrl("/internal/logout")
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(Boolean.TRUE)
			.permitAll();
		
		http.csrf().disable();
		//@formatter:on

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Autowired
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		DaoAuthenticationProvider userDetailsBasedAuthenticationProvider = new DaoAuthenticationProvider();
		userDetailsBasedAuthenticationProvider.setUserDetailsService(userService);
		auth.authenticationProvider(userDetailsBasedAuthenticationProvider);
	}

}