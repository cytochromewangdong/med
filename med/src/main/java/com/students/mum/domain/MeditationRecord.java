package com.students.mum.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.students.mum.domain.base.DefaultPrimaryKeyEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "STUDENT_ID", "DATE" }) })
public class MeditationRecord extends DefaultPrimaryKeyEntity {

	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;

	@Column(name = "DATE")
	private LocalDate date;
}
