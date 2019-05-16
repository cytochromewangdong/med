package com.students.mum.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.students.mum.domain.Faculty;
import com.students.mum.repository.FacultyRepository;

@Component
public class ProfessorFormatter implements Formatter<Faculty> {

	@Autowired
	private FacultyRepository facultyRepository;

	@Override
	public String print(Faculty object, Locale locale) {
		return String.valueOf(object.getId());
	}

	@Override
	public Faculty parse(String text, Locale locale) throws ParseException {
		return facultyRepository.getOne(Long.parseLong(text));
	}

}
