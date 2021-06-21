package com.pablo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public String usingRequestParam(Model model,
			@RequestParam(value = "name", required = false) String nickname) {
		return "index"; // because of the suffix this will look up for the "index.jsp"
						// file
	}

	@GetMapping("/{nickname}")
	public String home(ModelMap model, @PathVariable String nickname) {
		model.addAttribute("name", nickname);
		return "index";
	}
}
