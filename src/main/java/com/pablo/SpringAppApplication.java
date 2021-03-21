package com.pablo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@SpringBootApplication
public class SpringAppApplication {

  /**
   * RequestParam is used to retrieve value of parameter, name
   * 
   * @param model
   * @param nickname
   * @return
   */
  @GetMapping("/")
  String usingRequestParam(Model model,
      @RequestParam(value = "name", required = false) String nickname) {
    model.addAttribute("nickname", nickname);
    return "index";
  }

  /**
   * PathVariable is used to bind a method parameter to a URI template variable
   * 
   * @param model
   * @param nickname
   * @return
   */
  @GetMapping("/{nickname}")
  public String home(ModelMap model, @PathVariable String nickname) {
    model.addAttribute("name", nickname);
    return "index";
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringAppApplication.class, args);
  }

}
