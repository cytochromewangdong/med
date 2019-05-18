package com.students.mum.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class StudentDetailReport {
	@Getter
	@Setter
	@AllArgsConstructor
	public static class MeditationResult{
		private LocalDate date;
		private boolean taken;
	}
	@NotNull
	private String studentNum;
	private Long blockId;
	private List<MeditationResult> recordListForBlock = new ArrayList<>();
	private int meditationBlockCount = 0;
	private int blockSessionSize;
	private List<LocalDate> retreatList = new ArrayList<>();
	private List<LocalDate> checkList = new ArrayList<>();
}
