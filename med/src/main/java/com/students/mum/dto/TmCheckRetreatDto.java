package com.students.mum.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.students.mum.domain.TmCheckRetreat.CheckRetreatType;

import lombok.Data;

@Data
public class TmCheckRetreatDto {
	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private LocalDate date;
	@NotNull
	String studentNo;
	@NotNull
	CheckRetreatType type;
	
}
