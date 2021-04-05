package com.pablo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pablo.domain.User;
import com.pablo.exceptions.UnmatchingUserCredentialsException;
import com.pablo.helpers.ExecutionStatus;
import com.pablo.services.UserService;

@Controller
@RequestMapping("/account/*")
public class UserAccountController {

  private AuthenticationProvider authenticationProvider;

  private UserService userService;

  private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);

  @Autowired
  public UserAccountController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping
  public String login() {
    return "login";
  }

  /**
   * Template signup method
   * 
   * @return
   */
  @GetMapping("/signup")
  public String signup() {
    // TODO: Expand this section
    return "signup";
  }

  /**
   * Template method
   * 
   * @return
   */
  @GetMapping("/forgotpassword")
  public String forgotpassword() {
    // TODO: Expand this section
    return "forgotpassword";
  }

  @PostMapping(value = "/login/process", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody ExecutionStatus processLogin(ModelMap model, @RequestBody User reqUser) {

    User user = null;
    try {
      user = userService.isValidUser(reqUser.getEmail(), reqUser.getPassword());
    } catch (UnmatchingUserCredentialsException ex) {
      logger.debug(ex.getMessage(), ex);
    }

    if (user == null) {
      return new ExecutionStatus("USER_LOGIN_UNSUCCESSFUL",
          "Username or password is incorrect. Please try again!");
    }

    return new ExecutionStatus("USER_LOGIN_SUCCESSFUL", "Login Succesful!");
  }


  @PostMapping(value = "/login")
  public @ResponseBody ExecutionStatus processLoginSpringSecurity(@RequestBody User reqUser,
      HttpServletRequest request) {
    Authentication authentication = null;
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword());

    try {
      //
      // Delegate authentication check to a custom Authentication provider
      //
      authentication = this.authenticationProvider.authenticate(token);
      //
      // Store the authentication object in SecurityContextHolder
      SecurityContextHolder.getContext().setAuthentication(authentication);
      User user = (User) authentication.getPrincipal();
      user.setPassword(null);
      return new ExecutionStatus("USER_LOGIN_SUCCESSFUL", "Login Sucessful!", user);

    } catch (BadCredentialsException e) {
      return new ExecutionStatus("USER_LOGIN_UNSUCCESSFUL",
          "Username or password is incorrect. Please try again!");
    }

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

  @GetMapping("/logout")
  public ExecutionStatus logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return new ExecutionStatus("USER_LOGOUT_SUCCESSFUL", "User is logged out");
  }
}
