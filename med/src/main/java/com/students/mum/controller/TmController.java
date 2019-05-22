package com.students.mum.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.students.mum.domain.TmCheckRetreat;
import com.students.mum.dto.TmCheckRetreatDto;
import com.students.mum.service.TmCheckRetreatService;

@Controller
@RequestMapping("/tm")
// @Secured("ADMIN")
public class TmController {

	@PreAuthorize("hasAuthority('ADMIN')")
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	// @Secured("ROLE_ADMIN")
	@RequestMapping("/retreatCreate")
	public String display(Model model, @RequestParam(value = "id", required = false) Long id) {
		// @ModelAttribute("record") TmCheckRetreatDto record
		if (id == null) {
			model.addAttribute("tmCheckRetreatDto", new TmCheckRetreatDto());
		} else {
			model.addAttribute("tmCheckRetreatDto", tmCheckRetreatService.findTmCheckRetreatById(id));	
		}
		// Collection<SimpleGrantedAuthority> authorities =
		// (Collection<SimpleGrantedAuthority>)
		// SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		// System.out.println(authorities);
		return "tmCreate";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/retreatCreate")
	public String save(@Valid TmCheckRetreatDto dto, BindingResult br, Model model) {
		if (br.hasErrors()) {
			return "tmCreate";
		}
		tmCheckRetreatService.saveTmCheckRetreat(dto);
		return "redirect:/tm/retreatList";
	}

	@Autowired
	private TmCheckRetreatService tmCheckRetreatService;

	// @PreAuthorize("hasAuthority('ADMIN')")
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/retreatList")
	public String search(
			// @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern
			// = "MM/dd/yyyy") LocalDate startDate,
			// @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern =
			// "MM/dd/yyyy") LocalDate endDate,
			Model model) {
		List<TmCheckRetreat> list = findRetreat(null, null);
		model.addAttribute("list", list);
		return "tmList";
	}

	private List<TmCheckRetreat> findRetreat(LocalDate startDate, LocalDate endDate) {
		if (startDate == null) {
			startDate = LocalDate.now().minusYears(10);
		}
		if (endDate == null) {
			endDate = LocalDate.now().plusYears(10);
		}
		List<TmCheckRetreat> list = tmCheckRetreatService.findTmCheckRetreatByDate(startDate, endDate);
		return list;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/retreatDelete")
	public String retreatDelete(@RequestParam("id") Long id) {
		tmCheckRetreatService.removeTmCheckRetreat(id);
		return "redirect:/tm/retreatList";
	}

	// @PostMapping("/rsave")
	// @ResponseBody
	// public TmCheckRetreat rsave(@Valid @RequestBody TmCheckRetreatDto dto) {
	// return tmCheckRetreatService.saveTmCheckRetreat(dto);
	// }
	//
	// @PostMapping("/rsearch")
	// @ResponseBody
	// public List<TmCheckRetreat> rsearch(
	// @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern
	// = "MM/dd/yyyy") LocalDate startDate,
	// @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern =
	// "MM/dd/yyyy") LocalDate endDate) {
	// return findRetreat(startDate, endDate);
	// }

}
