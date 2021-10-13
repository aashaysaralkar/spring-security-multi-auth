package com.ekam.spring.security.multiAuthSecurityApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Customizations to Auto MVC Configurations
 *<p>Refer to https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#features.developing-web-applications.spring-mvc.auto-configuration</p>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("landing");
		registry.addViewController("/landing").setViewName("landing");
		registry.addViewController("/sso/landing").setViewName("landing");
		registry.addViewController("/internal/login").setViewName("login");
		registry.addViewController("/access-denied").setViewName("403");
	}
	
	

}
