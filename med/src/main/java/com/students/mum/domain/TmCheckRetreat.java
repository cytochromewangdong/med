package com.students.mum.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class TmCheckRetreat {
	public enum CheckRetreatType {
		check, retreat
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private CheckRetreatType type;

	@ManyToOne
	@JoinColumn(name = "student_no")
	private Student student;

	private LocalDate date;

}
