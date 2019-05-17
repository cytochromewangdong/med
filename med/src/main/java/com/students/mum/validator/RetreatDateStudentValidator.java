package com.students.mum.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.students.mum.dto.TmCheckRetreatDto;
import com.students.mum.service.TmCheckRetreatService;

@Component
public class RetreatDateStudentValidator implements ConstraintValidator<RetreatDateStudent, TmCheckRetreatDto> {

	@Autowired
	private TmCheckRetreatService tmCheckRetreatService;

	@Override
	public boolean isValid(TmCheckRetreatDto value, ConstraintValidatorContext context) {
		return tmCheckRetreatService.checkDuplicate(value);
	}

}
