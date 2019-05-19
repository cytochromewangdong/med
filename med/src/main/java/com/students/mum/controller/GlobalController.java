package com.students.mum.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GlobalController {
	@RequestMapping(value = "/error/access-denied", method = RequestMethod.GET)
	public String accessDenied() {
		return "403";
	}

	@RequestMapping("/")
	public String root() {
		return "redirect:/index";
	}

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STUDENT')")
	// @PreAuthorize("hasRole('FUCK')")
	@RequestMapping("/index")
	public String index() {

		return "index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}
}
