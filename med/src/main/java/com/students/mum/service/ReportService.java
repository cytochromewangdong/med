package com.students.mum.service;

import com.students.mum.dto.GroupReport;
import com.students.mum.dto.StudentDetailReport;

public interface ReportService {
	StudentDetailReport summaryStudentDetailReport(StudentDetailReport reportParam);

	GroupReport summaryEntryReport(GroupReport reportParam);

	GroupReport summaryBlockReport(GroupReport reportParam);
}
