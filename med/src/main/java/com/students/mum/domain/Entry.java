package com.students.mum.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.students.mum.domain.base.DefaultPrimaryKeyEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Entry extends DefaultPrimaryKeyEntity {
	private String name;
	@OneToMany(mappedBy = "entry")
	private List<Student> studentList;

	private LocalDate meditationStartDate;
	@OneToMany(mappedBy = "entry")
	private List<EntryStudentStatistics> entryStudentStatisticsList;
}
