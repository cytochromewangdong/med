package com.students.mum.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.students.mum.domain.Student;
import com.students.mum.domain.TmCheckRetreat.CheckRetreatType;
import com.students.mum.validator.RetreatDateStudent;

import lombok.Data;

@Data
@RetreatDateStudent(message = "The Student has done the check for the same day.")
public class TmCheckRetreatDto {
	private Long id;
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent
	private LocalDate date;
	// @NotNull
	// private String studentNo;
	@NotNull
	private CheckRetreatType type;
	@NotNull
//	@StudentId
	private Student student;
}
