package com.students.mum.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GroupReport {
	@NotNull
	private Long groupId;
	
	private List<ReportRow> data;
}
