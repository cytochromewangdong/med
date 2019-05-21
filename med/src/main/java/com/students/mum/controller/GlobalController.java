package com.students.mum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.students.mum.dto.DateDto;

@Controller
public class GlobalController {
	@RequestMapping(value = "/error/access-denied", method = RequestMethod.GET)
	public String accessDenied() {
		return "403";
	}

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/")
	public String root() {
		return "redirect:/index";
	}

	@PreAuthorize("hasAuthority('STUDENT') or hasAuthority('ADMIN') or hasAuthority('FACULTY')")
	// @PreAuthorize("hasRole('FUCK')")
	@RequestMapping("/index")
	public String index() {
		try {
			DateDto dto = restTemplate.getForEntity("http://localhost:8081/time", DateDto.class).getBody();
			System.out.println(dto.getDateTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
