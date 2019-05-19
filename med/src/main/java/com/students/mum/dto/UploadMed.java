package com.students.mum.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.students.mum.domain.MeditationImportFile.MeditationImportFileType;

import lombok.Data;

@Data
public class UploadMed {
	@NotNull
	private MeditationImportFileType type;
	@NotNull
	private MultipartFile dataFile;
}
