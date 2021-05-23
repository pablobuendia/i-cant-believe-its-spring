package com.pablo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.httpBasic().disable();
		security.cors().and().csrf().disable();

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Web resources
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/scripts/**");
		web.ignoring().antMatchers("/built/**");
		web.ignoring().antMatchers("/**");
	}
}
