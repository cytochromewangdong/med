package com.students.mum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.students.mum.dto.GroupReport;
import com.students.mum.dto.StudentDetailReport;
import com.students.mum.security.UserDetailAndTag;
import com.students.mum.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/entry")
	public String entryReport(Model model, @RequestParam(value = "groupId", required = false) Long entryId) {
		GroupReport report = new GroupReport();
		report.setGroupId(entryId);
		if (entryId == null) {
			model.addAttribute("report", report);
		} else {
			model.addAttribute("report", reportService.summaryEntryReport(report));
		}

		return "entryReport";
	}

	@PreAuthorize("hasAuthority('FACULTY')")
	@RequestMapping("/block")
	public String blockReport(Model model, @RequestParam(value = "groupId", required = false) Long sectionId) {
		GroupReport report = new GroupReport();
		model.addAttribute("sectionList", reportService.getSectionList());
		report.setGroupId(sectionId);
		if (sectionId == null) {
			model.addAttribute("report", report);
		} else {
			model.addAttribute("report", reportService.summaryBlockReport(report));
		}
		return "blockReport";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/calculateAll")
	@ResponseStatus(HttpStatus.OK)
	public void calculateAll() {
		reportService.calculateAll();
	}

	@PreAuthorize("hasAuthority('STUDENT') or hasAuthority('ADMIN') or hasAuthority('FACULTY')")
//	@PreAuthorize("hasAuthority('*')")
	@RequestMapping("/student")
	public String studentReport(Model model, @RequestParam(value = "blockId", required = false) Long blockId) {
		// , @RequestParam("studentNumber") String studentNumber
		UserDetailAndTag tag = (UserDetailAndTag) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		StudentDetailReport report = new StudentDetailReport();
		report.setStudentNum(tag.getTag());
		report.setBlockId(blockId);
		model.addAttribute("report", reportService.summaryStudentDetailReport(report));
		return "studentReport";
	}
}
