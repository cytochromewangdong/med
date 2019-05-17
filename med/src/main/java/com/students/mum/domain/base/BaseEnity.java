package com.students.mum.domain.base;

import java.time.LocalDate;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEnity {
	private LocalDate createDate;
	private LocalDate modifyDate;
}
