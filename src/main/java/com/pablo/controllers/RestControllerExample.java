package com.pablo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.pablo.domain.User;
import com.pablo.helpers.HelloMessage;

@RestController
public class RestControllerExample {

  @GetMapping("/rest")
  String home() {
    return "Hello World. This is a health application";
  }

  /**
   * ResponseBody indicates that the value returned by the method will form the body of the
   * response. When the value returned is an object, the object is converted into an appropiate JSON
   * or XML format by HttpMessageConverters. The form is decided by the value of "produces"
   * 
   * @param user
   * @return
   */
  @PostMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public HelloMessage getHelloMessage(@RequestBody User user) {
    HelloMessage helloMessage = new HelloMessage();
    String name = user.getFirstname();
    helloMessage.setMessage("Hello " + name + "! How are you doing?");
    helloMessage.setName(name);
    return helloMessage;
  }

}
