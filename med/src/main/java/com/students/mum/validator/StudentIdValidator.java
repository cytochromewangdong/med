package com.students.mum.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.students.mum.domain.Student;
import com.students.mum.repository.StudentRepository;

@Component
public class StudentIdValidator implements ConstraintValidator<StudentId, String> {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public void initialize(StudentId arg0) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isNullOrEmpty(value)) {
			return false;
		}
		Student product = null;
		try {
			product = studentRepository.getOne(value);
		} catch (Exception e) {
			System.out.println("Couldn't find Student...");
		}
		return product == null ? true : false;
	}

}
