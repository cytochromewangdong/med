package com.students.mum.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.students.mum.dto.TmCheckRetreatDto;
import com.students.mum.service.TmCheckRetreatService;

@Controller
@RequestMapping("/tm")
public class TmController {

	@RequestMapping("/display")
	public String display() {
		return "tmDisplay";
	}

	@Autowired
	private TmCheckRetreatService tmCheckRetreatService;

	@PostMapping("/search")
	public String search(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate endDate,
			Model model) {
		if (startDate == null) {
			startDate = LocalDate.now().minusYears(10);
		}
		if (endDate == null) {
			endDate = LocalDate.now();
		}
		model.addAttribute("list", tmCheckRetreatService.findTmCheckRetreatByDate(startDate, endDate));
		return "tmDisplay";
	}

	@PostMapping("/add")
	public String add(@Valid TmCheckRetreatDto dto, BindingResult br, Model model) {
		if(br.hasErrors())
		{
			return "tmForm";
		}
		tmCheckRetreatService.addTmCheckRetreat(dto.getStudentNo(), dto.getDate(), dto.getType());
		return "tmDisplay";
	}
}
