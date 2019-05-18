package com.students.mum.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.students.mum.domain.base.DefaultPrimaryKeyEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
// @BlockDate(min = 5, maximum = 50)
public class Block extends DefaultPrimaryKeyEntity {

	// @NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	// @Future
	private LocalDate startDate;

	// @NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	// @Future
	private LocalDate endDate;

	// @NotNull
	// @Min(5)
//	private Integer sessionDays;

	@OneToMany(mappedBy = "block") // cascade = CascadeType.MERGE,
	private List<Section> sectionList = new ArrayList<>();

	@OneToMany(mappedBy = "block")
	private List<BlockStudentStatistics> blockStudentStatisticsList;
}
