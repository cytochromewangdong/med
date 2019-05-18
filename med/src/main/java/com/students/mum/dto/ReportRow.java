package com.students.mum.dto;

import lombok.Data;

@Data
public class ReportRow {
	private String studentNumber;
	private String studentName;
	private double percent;
	private int present;
	private int possible;
	private double score;
	
}
