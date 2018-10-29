package com.ekam.spring.security.multiAuthSecurityApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(2)
public class FormLoginSecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/webjars/**", "/images/**", "/security/**", "/error").permitAll().and()
				.formLogin().loginPage("/internal/login").permitAll().defaultSuccessUrl("/landing", true).and().logout()
				.logoutUrl("/internal/logout").logoutSuccessUrl("/internal/login?logout").permitAll().and().csrf().disable().authorizeRequests().anyRequest().authenticated();

	}

}