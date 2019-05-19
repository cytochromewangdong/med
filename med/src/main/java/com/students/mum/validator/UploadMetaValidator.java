package com.students.mum.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.students.mum.dto.UploadMed;

@Component
public class UploadMetaValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return UploadMed.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		UploadMed fileUpload = (UploadMed) target;

		MultipartFile multipartFile = fileUpload.getDataFile();

		if (multipartFile.getSize() == 0) {
			errors.rejectValue("dataFile", "missing.file");
		}
		if (fileUpload.getType() == null) {
			errors.rejectValue("type", "missing.type");
		}

	}

}
