package com.ekam.spring.security.multiAuthSecurityApp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;

    @RequestMapping("/filterChain")
    public @ResponseBody Map<Integer, Map<Integer, String>> getSecurityFilterChainProxy(){
        return this.getSecurityFilterChainProxy1();
    }
    
    @RequestMapping("/authenticationprovider")
    public @ResponseBody List<String> getAuthenticationProviders(){
    	Map<String, ProviderManager> authManagerMap = applicationContext.getBeansOfType(ProviderManager.class);
    	List<String> result = new ArrayList<String>();
    	for(String key : authManagerMap.keySet()){
    		for(AuthenticationProvider provider : authManagerMap.get(key).getProviders()){
    			result.add(provider.getClass().getName());
    		}
    	}
    	return result;
    }

    public Map<Integer, Map<Integer, String>> getSecurityFilterChainProxy1(){
    	FilterChainProxy filterChainProxy =applicationContext.getBean(FilterChainProxy.class);
        Map<Integer, Map<Integer, String>> filterChains= new HashMap<Integer, Map<Integer, String>>();
        int i = 1;
        for(SecurityFilterChain secfc :  filterChainProxy.getFilterChains()){
            //filters.put(i++, secfc.getClass().getName());
            Map<Integer, String> filters = new HashMap<Integer, String>();
            int j = 1;
            for(Filter filter : secfc.getFilters()){
                filters.put(j++, filter.getClass().getName());
            }
            filterChains.put(i++, filters);
        }
        
        return filterChains;
    }

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		applicationContext = arg0;
		
	}

}
