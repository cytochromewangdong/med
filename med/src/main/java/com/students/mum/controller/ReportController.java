package com.students.mum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.students.mum.domain.Block;
import com.students.mum.dto.GroupReport;
import com.students.mum.dto.StudentDetailReport;
import com.students.mum.security.UserDetailAndTag;
import com.students.mum.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@RequestMapping("/entry")
	public String entryReport(Model model, @RequestParam("entryId") Long entryId) {
		GroupReport report = new GroupReport();
		report.setGroupId(entryId);
		model.addAttribute("report", reportService.summaryEntryReport(report));
		return "entryReport";
	}

	@RequestMapping("/block")
	public String blockReport(Model model, @RequestParam("blockId") Long blockId) {
		GroupReport report = new GroupReport();
		report.setGroupId(blockId);
		model.addAttribute("report", reportService.summaryBlockReport(report));
		return "blockReport";
	}

	
	@PreAuthorize("hasAuthority('STUDENT')")
	@RequestMapping("/student")
	public String studentReport(Model model, @RequestParam(value = "blockId", required = false) Long blockId) {
		// , @RequestParam("studentNumber") String studentNumber
		UserDetailAndTag tag=(UserDetailAndTag) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		StudentDetailReport report = new StudentDetailReport();
		report.setStudentNum(tag.getTag());
		report.setBlockId(blockId);
		model.addAttribute("report", reportService.summaryStudentDetailReport(report));
		return "studentReport";
	}
}
