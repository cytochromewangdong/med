package com.students.mum.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.students.mum.domain.TmCheckRetreat;
import com.students.mum.dto.TmCheckRetreatDto;
import com.students.mum.dto.UploadMed;
import com.students.mum.service.TmCheckRetreatService;
import com.students.mum.validator.UploadMetaValidator;

@Controller
@RequestMapping("/tm")
// @Secured("ADMIN")
public class TmController {

	@PreAuthorize("hasAuthority('ADMIN')")
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	// @Secured("ROLE_ADMIN")
	@RequestMapping("/display")
	public String display() {
		// Collection<SimpleGrantedAuthority> authorities =
		// (Collection<SimpleGrantedAuthority>)
		// SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		// System.out.println(authorities);
		return "tmDisplay";
	}

	@Autowired
	private TmCheckRetreatService tmCheckRetreatService;

	// @PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/search")
	public String search(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate endDate,
			Model model) {
		List<TmCheckRetreat> list = findRetreat(startDate, endDate);
		model.addAttribute("list", list);
		return "tmDisplay";
	}

	private List<TmCheckRetreat> findRetreat(LocalDate startDate, LocalDate endDate) {
		if (startDate == null) {
			startDate = LocalDate.now().minusYears(10);
		}
		if (endDate == null) {
			endDate = LocalDate.now();
		}
		List<TmCheckRetreat> list = tmCheckRetreatService.findTmCheckRetreatByDate(startDate, endDate);
		return list;
	}

	@PostMapping("/save")
	public String save(@Valid TmCheckRetreatDto dto, BindingResult br, Model model) {
		if (br.hasErrors()) {
			return "tmForm";
		}
		tmCheckRetreatService.saveTmCheckRetreat(dto);
		return "tmDisplay";
	}

	@PostMapping("/rsave")
	@ResponseBody
	public TmCheckRetreat rsave(@Valid @RequestBody TmCheckRetreatDto dto) {
		return tmCheckRetreatService.saveTmCheckRetreat(dto);
	}

	@PostMapping("/rsearch")
	@ResponseBody
	public List<TmCheckRetreat> rsearch(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate endDate) {
		return findRetreat(startDate, endDate);
	}

	@RequestMapping("/updIndex")
	public String updIndex(@ModelAttribute UploadMed uploadMed) {
		return "upload";
	}

	@Autowired
	private UploadMetaValidator fileValidator;

	@InitBinder
	protected void initBinderFileBucket(WebDataBinder binder) {
		binder.setValidator(fileValidator);
	}

	@PostMapping("/updIndex")
	public String handlUploading(@Valid UploadMed uploadMed, BindingResult br) {
		if (br.hasErrors()) {
			return "upload";
		}
		tmCheckRetreatService.uploadMedDataFileService(uploadMed);
		return "redirect:/tm/uploadList";
	}
	
	@RequestMapping("/uploadList")
	public String uploadList(Model model) {
		model.addAttribute("uploadList", tmCheckRetreatService.getMeditationImportFileList());
		return "uploadList";
	}
}
