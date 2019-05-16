package com.students.mum.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.students.mum.domain.base.BaseEnity;
import com.students.mum.validator.BlockDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
//@BlockDate(min = 5, maximum = 50)
public class Block extends BaseEnity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

//	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Course course;

//	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Future
	private LocalDate startDate;

//	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Future
	private LocalDate endDate;

//	@NotNull
//	@Min(5)
	private Integer sessionDays;

//	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE)
	private Faculty professor;
}
