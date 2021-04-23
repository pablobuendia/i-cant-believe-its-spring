package com.pablo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication // <-- Look it says Spring Boot, that's what I'm using! Isn't it nice?
public class SpringAppApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SpringAppApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringAppApplication.class, args);
  }

}
