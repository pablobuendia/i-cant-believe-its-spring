package com.pablo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account/*")
public class UserAccountController {

  @RequestMapping
  public String login() {
    return "login";
  }

  @GetMapping("/signup")
  public String signup() {
    return "signup";
  }

  @GetMapping("/forgotpassword")
  public String forgotpassword() {
    return "forgotpassword";
  }

  /**
   * ModelAndView is a container object to hold both Model and View. With ModelAndView as a return
   * object, the controller returns the both model and view as a single return value.
   * 
   * @param nickname
   * @param emailAddress
   * @param password
   * @param model
   * @return
   */
  @RequestMapping("/signup/process")
  public ModelAndView processSignup(@RequestParam("nickname") String nickname,
      @RequestParam("emailaddress") String emailAddress, @RequestParam("password") String password,
      ModelMap model) {
    model.addAttribute("login", true);
    model.addAttribute("nickname", nickname);
    model.addAttribute("message", "Have a great day ahead.");
    return new ModelAndView("index", model);
  }
}
