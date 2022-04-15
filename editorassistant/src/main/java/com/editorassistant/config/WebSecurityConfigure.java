package com.editorassistant.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) {
		try {
			http.cors().and().csrf().disable()
		      .formLogin().disable();
			
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
