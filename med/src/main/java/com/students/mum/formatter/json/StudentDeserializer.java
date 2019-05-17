package com.students.mum.formatter.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.students.mum.domain.Student;
import com.students.mum.formatter.StudentFormatter;

@Component
public class StudentDeserializer extends StdDeserializer<Student> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7337085342539911425L;
	@Autowired
	private StudentFormatter studentFormatter;

	public StudentDeserializer() {
		super(Student.class);
	}

	@Override
	public Student deserialize(JsonParser parser, DeserializationContext deserializer) {
		ObjectCodec codec = parser.getCodec();
		JsonNode node;
		try {
			node = codec.readTree(parser);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// try catch block
//		JsonNode studentNode = node.get("student");
//		if (studentNode == null) {
//			return null;
//		}
		String studentId = node.asText();
		return studentFormatter.convert(studentId);
	}
}
