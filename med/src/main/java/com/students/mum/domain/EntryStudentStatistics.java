package com.students.mum.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.students.mum.domain.base.DefaultPrimaryKeyEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EntryStudentStatistics extends DefaultPrimaryKeyEntity {
	@ManyToOne
	@JoinColumn(name="ENTRY_ID")
	private Entry entry;
	
//	private int possibleMeditation;
	
	private int realMeditation;
	
	@OneToOne
	@JoinColumn(name="STUDENT_ID")
	private Student student;
}
