package com.pablo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pablo.services.UserService;

@Controller
@RequestMapping("/account/login")
public class LoginController extends UserAccountController {

  public LoginController(UserService userService) {
    super(userService);
  }

  @GetMapping()
  public String login() {
    return "login";
  }
}
