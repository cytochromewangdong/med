package com.students.mum.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AggregatingTaskDate {
	@Id
	private LocalDate date;
	private int processedRecord;
}
