package com.pablo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.pablo.interceptors.SignupInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SignupInterceptor()).addPathPatterns("/account/signup/process");
  }
}
