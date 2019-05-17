package com.students.mum.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;

import org.springframework.format.annotation.DateTimeFormat;

import com.students.mum.domain.base.BaseEnity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
// @BlockDate(min = 5, maximum = 50)
public class Block extends BaseEnity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// @NotNull
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Course course;

	// @NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Future
	private LocalDate startDate;

	// @NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Future
	private LocalDate endDate;

	// @NotNull
	// @Min(5)
	private Integer sessionDays;

	// @NotNull
	@ManyToOne
	@JoinColumn(name = "PROFESSOR_ID")
	private Faculty professor;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable
	private List<Student> studentList = new ArrayList<>();
}
