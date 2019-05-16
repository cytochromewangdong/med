package com.students.mum.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.students.mum.domain.Course;
import com.students.mum.repository.CourseRepository;

@Component
public class CourseFormatter implements Formatter<Course> {

	@Override
	public String print(Course object, Locale locale) {
		return String.valueOf(object.getId());
	}

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public Course parse(String text, Locale locale) throws ParseException {
		Long id = Long.parseLong(text);
		return courseRepository.getOne(id);
	}

}
