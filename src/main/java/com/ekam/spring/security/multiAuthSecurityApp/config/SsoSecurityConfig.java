package com.ekam.spring.security.multiAuthSecurityApp.config;

import static java.lang.Boolean.TRUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import com.ekam.spring.security.multiAuthSecurityApp.services.UserService;

@Configuration
@EnableWebSecurity
@Order(1)
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	@PreAuthorize("ADMIN")
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().addFilterAfter(siteMinderFilter(), RequestHeaderAuthenticationFilter.class).authenticationProvider(preAuthProvider()).authorizeRequests()
				.antMatchers("/internal/login", "/logout", "/swagger-resources/**", "/swagger-ui.html",
						"/v2/api-docs","/error")
				.permitAll().and().antMatcher("/").authorizeRequests().and().exceptionHandling().accessDeniedPage("/error");
		

	}

	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Autowired
	@Override	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.authenticationProvider(preAuthProvider());
		DaoAuthenticationProvider userDetailsBasedAuthenticationProvider = new DaoAuthenticationProvider();
		userDetailsBasedAuthenticationProvider.setUserDetailsService(userService);
		auth.authenticationProvider(userDetailsBasedAuthenticationProvider);
	}
	
	private AuthenticationProvider preAuthProvider() {
		PreAuthenticatedAuthenticationProvider preAuthProvider =
				new PreAuthenticatedAuthenticationProvider();
		preAuthProvider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(userService));
		return preAuthProvider;
	}
	
	@Autowired
	protected GenericFilterBean siteMinderFilter() throws Exception {
		RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
		//filter.setPrincipalRequestHeader("SM_USER"); // Default value is SM_USER 
		filter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
		filter.setCheckForPrincipalChanges(TRUE); // If user token value in header changes, re-validate
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}
	

}