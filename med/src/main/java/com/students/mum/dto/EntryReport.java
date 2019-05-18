package com.students.mum.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EntryReport {
	@NotNull
	private Long entryId;
}
