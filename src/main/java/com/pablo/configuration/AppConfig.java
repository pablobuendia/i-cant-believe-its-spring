package com.pablo.configuration;

import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.pablo.interceptors.SignupInterceptor;

@Configuration // To indicate the class can have one or more @Bean annotated methods. Is
               // meta-annotated with @Component
@EntityScan("com.pablo.domain") // Used to configure the base packages, which are used to scan the
                                // packages for JPA entity classes.
@EnableTransactionManagement // This is used to register one or more Spring components, which are
                             // used to power annotation-driven transaction management capabilites.
@PropertySource("classpath:application.properties") // These are used in conjuction with a
                                                    // @Configuration method.
public class AppConfig implements WebMvcConfigurer {

  @Value("${spring.datasource.driverClassName}")
  String driverClassName;
  @Value("${spring.datasource.url}")
  String url;
  @Value("${spring.datasource.username}")
  String username;
  @Value("${spring.datasource.password}")
  String password;

  /**
   * Reperesents the bean definition for the data source to be used with Hibernate Session Factory
   * 
   * @return
   */
  @Bean(name = "dataSource")
  public DataSource getDataSource() {
    DataSource dataSource = DataSourceBuilder.create().username(username).password(password)
        .url(url).driverClassName(driverClassName).build();
    return dataSource;
  }

  /**
   * The following code configures a session factory bean.
   * 
   * @param dataSource
   * @return
   */
  @Bean(name = "sessionFactory")
  public SessionFactory getSessionFactory(DataSource dataSource) {
    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
    sessionBuilder.scanPackages("com.pablo.domain");
    return sessionBuilder.buildSessionFactory();
  }

  /**
   * By configuring a HibernateTransactionManager for SessionFactory would avoid code in DAO classes
   * to explicitly take care of transaction management concerns.
   * 
   * @param sessionFactory
   * @return
   */
  @Bean(name = "transactionManager")
  public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
    HibernateTransactionManager transactionManager =
        new HibernateTransactionManager(sessionFactory);
    return transactionManager;
  }

  /**
   * Adds the interceptor with the appropiate patterns with the addInterceptor method. If the path
   * pattern is not specified, the interceptor is executed for all the requests.
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SignupInterceptor()).addPathPatterns("/account/signup/process");
  }
}
