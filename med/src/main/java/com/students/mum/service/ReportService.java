package com.students.mum.service;

import java.util.List;

import com.students.mum.dto.GroupReport;
import com.students.mum.dto.SectionBlock;
import com.students.mum.dto.StudentDetailReport;

public interface ReportService {
	StudentDetailReport summaryStudentDetailReport(StudentDetailReport reportParam);

	GroupReport summaryEntryReport(GroupReport reportParam);

	GroupReport summaryBlockReport(GroupReport reportParam);
	
	void calculateAll();
	
	List<SectionBlock> getSectionList();
}
