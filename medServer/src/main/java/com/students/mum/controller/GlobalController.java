package com.students.mum.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.students.mum.dto.DateDto;

@Controller
public class GlobalController {
	@RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public void accessDenied() {
		System.out.println("-----");
	}

	@RequestMapping("/login")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void login() {

	}

	@RequestMapping("/login-error")
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void loginError() {

	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/time")
	@ResponseBody
	public DateDto time() {
		return new DateDto(LocalDateTime.now());
	}
}
