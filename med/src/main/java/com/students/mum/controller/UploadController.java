package com.students.mum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.students.mum.dto.UploadMed;
import com.students.mum.service.TmCheckRetreatService;
import com.students.mum.validator.UploadMetaValidator;

@Controller
@RequestMapping("/tm")
public class UploadController {
	@Autowired
	private UploadMetaValidator fileValidator;
	@Autowired
	private TmCheckRetreatService tmCheckRetreatService;

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
