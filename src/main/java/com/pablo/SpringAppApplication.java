package com.pablo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@SpringBootApplication
public class SpringAppApplication extends SpringBootServletInitializer {

  /**
   * "@RequestParam" is used to retrieve value of parameter, name
   * 
   * @param model
   * @param nickname
   * @return
   */
  @GetMapping("/")
  public String usingRequestParam(Model model,
      @RequestParam(value = "name", required = false) String nickname) {
    model.addAttribute("nickname", nickname);
    return "index"; // because of the suffix this will look up for the "index.jsp" file
  }

  /**
   * "@PathVariable" is used to bind a method parameter (String nickname) to a URI template variable
   * (/{nickname}). Method that returns a simple index page that shows the nickname sent as a
   * parameter in the GET request.
   * 
   * @param model
   * @param nickname
   * @return
   */
  @GetMapping("/{nickname}")
  public String home(ModelMap model, @PathVariable String nickname) {
    model.addAttribute("name", nickname);
    return "index"; // because of the suffix this will look up for the "index.jsp" file
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SpringAppApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringAppApplication.class, args);

    // Sample code to test MYSQL database connection
    // String url = "jdbc:mysql://localhost:3306/healthapp";
    // String username = "root";
    // String password = "root";
    //
    // System.out.println("Connecting database...");
    //
    // try {
    // Connection connection = DriverManager.getConnection(url, username, password);
    // System.out.println("Database connected!");
    // } catch (SQLException e) {
    // throw new IllegalStateException("Cannot connect to the database");
    // }
  }

}
