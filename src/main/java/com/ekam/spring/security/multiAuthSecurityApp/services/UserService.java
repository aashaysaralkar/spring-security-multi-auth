package com.ekam.spring.security.multiAuthSecurityApp.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if("user".equalsIgnoreCase(username)){
			return User.withUsername(username).password("password").authorities("ROLE_USER").build();
		}
		return null;
	}

}
