package com.students.mum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.students.mum.domain.Student;
import com.students.mum.formatter.CourseFormatter;
import com.students.mum.formatter.ProfessorFormatter;
import com.students.mum.formatter.StudentFormatter;
import com.students.mum.formatter.json.StudentDeserializer;
import com.students.mum.formatter.json.StudentSerializer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
	@Autowired
	private CourseFormatter courseFormatter;
	@Autowired
	private StudentFormatter studentFormatter;
	@Autowired
	private ProfessorFormatter professorFormatter;

	@Autowired
	private StudentDeserializer studentDeserializer;

	@Autowired
	private StudentSerializer studentSerializer;
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(courseFormatter);
		registry.addFormatter(studentFormatter);
//		registry.addConverter(studentFormatter);
		registry.addFormatter(professorFormatter);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
		mapper.registerModule(new JavaTimeModule());
		SimpleModule module = new SimpleModule("Custom", new Version(1, 0, 0, null, null, null));
		module.addDeserializer(Student.class, studentDeserializer);
		module.addSerializer(studentSerializer);
		mapper.registerModule(module);

		return mapper;
	}
}
