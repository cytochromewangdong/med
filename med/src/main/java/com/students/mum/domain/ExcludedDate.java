package com.students.mum.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ExcludedDate {

	@Id
	private LocalDate date;
}
