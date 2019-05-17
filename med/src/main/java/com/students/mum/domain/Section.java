package com.students.mum.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.students.mum.domain.base.DefaultPrimaryKeyEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Section extends DefaultPrimaryKeyEntity {

	// @NotNull
	@ManyToOne
	@JoinColumn(name = "COURSE_ID")
	private Course course;

	// @NotNull
	@ManyToOne
	@JoinColumn(name = "PROFESSOR_ID")
	private Faculty professor;

	@ManyToMany
	@JoinTable
	private List<Student> studentList = new ArrayList<>();

	@ManyToOne // (cascade = CascadeType.None)
	@JoinColumn(name = "BLOCK_ID")
	private Block block;
}
