package com.students.mum.formatter.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.students.mum.domain.Student;

@Component
public class StudentSerializer extends StdSerializer<Student> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4832332447387695299L;

	public StudentSerializer() {
		super(Student.class);
	}

	// public StudentSerializer(Class<Student> t) {
	// super(t);
	// }

	@Override
	public void serialize(Student student, JsonGenerator jsonGenerator, SerializerProvider serializer) {
		try {
			// jsonGenerator.writeStartObject();
			// jsonGenerator.writeStringField("student", student.getNumber());
			jsonGenerator.writeString(student.getNumber());
			// jsonGenerator.writeEndObject();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}