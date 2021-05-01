package com.pablo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	/**
	 * This is the index and first page!!
	 * 
	 * @param model The model
	 * @param nickname the nickname to show to the user
	 * @return The name of the page
	 */
	@GetMapping("/")
	public String usingRequestParam(Model model,
			@RequestParam(value = "name", required = false) String nickname) {
		model.addAttribute("nickname", nickname);
		return "index"; // because of the suffix this will look up for the "index.jsp"
						// file
	}

	/**
	 * "@PathVariable" is used to bind a method parameter (String nickname) to a URI
	 * template variable (/{nickname}). Method that returns a simple index page that shows
	 * the nickname sent as a parameter in the GET request.
	 * 
	 * An example to show how the PathVariable annotation works.
	 * 
	 * @param model
	 * @param nickname
	 * @return
	 */
	@GetMapping("/{nickname}")
	public String home(ModelMap model, @PathVariable String nickname) {
		model.addAttribute("name", nickname);
		return "index"; // because of the suffix this will look up for the "index.jsp"
						// file
	}
}
