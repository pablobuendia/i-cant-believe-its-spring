package com.pablo.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SignupInterceptor implements HandlerInterceptor {

  /**
   * An implementation of the preHandle method gets executed before the controller method is invoked
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String emailAddress = request.getParameter("emailAddress");
    String password = request.getParameter("password");

    if (ObjectUtils.isEmpty(emailAddress) || StringUtils.containsWhitespace(emailAddress)
        || ObjectUtils.isEmpty(password) || StringUtils.containsWhitespace(password)) {
      throw new Exception("Invalid Email Address or Password. Please try again.");
    }

    return true;
  }

  /**
   * The code is executed after the view gets rendered
   */
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception exception) throws Exception {

  }

  /**
   * The code is executed after the controller method is invoked
   */
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

  }
}
