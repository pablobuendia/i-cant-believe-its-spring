package com.pablo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account/login")
public class LoginController {

	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return "login";
	}
}
