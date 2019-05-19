package com.students.mum.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.students.mum.domain.Student;
import com.students.mum.repository.StudentRepository;

@Component
public class StudentFormatter implements Formatter<Student>, Converter<String, Student> {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public String print(Student object, Locale locale) {
		return object.getNumber();
	}

	@Override
	public Student parse(String text, Locale locale) throws ParseException {
		if (StringUtils.isEmpty(text)) {
			return null;
		}
		try {
			return studentRepository.findById(text).orElse(null);
		} catch (Exception e) {
			return null;

		}
	}

	@Override
	public Student convert(String source) {
		return studentRepository.getOne(source);
	}

}
