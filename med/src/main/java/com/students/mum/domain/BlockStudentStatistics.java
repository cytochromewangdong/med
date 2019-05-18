package com.students.mum.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.students.mum.domain.base.DefaultPrimaryKeyEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BlockStudentStatistics extends DefaultPrimaryKeyEntity {

	@ManyToOne
	@JoinColumn(name="BLOCK_ID")
	private Block block;
	
	private int medBlockCount;
	
	@ManyToOne
	@JoinColumn(name="STUDENT_ID")
	private Student student;
}
