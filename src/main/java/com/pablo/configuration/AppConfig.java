package com.pablo.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // To indicate the class can have one or more @Bean annotated methods. Is
				// meta-annotated with @Component
@EntityScan("com.pablo.domain") // Used to configure the base packages, which are used to
								// scan the packages for JPA entity classes.
@EnableTransactionManagement // This is used to register one or more Spring components,
								// which are used to power annotation-driven transaction
								// management capabilites.
@PropertySource("classpath:application.properties") // These are used in conjuction with a
													// @Configuration method.
@EnableAutoConfiguration
public class AppConfig implements WebMvcConfigurer {

}
