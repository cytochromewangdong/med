package com.students.mum.domain.base;

import java.time.LocalDate;

import lombok.Data;

@Data
public abstract class BaseEnity {
	private LocalDate createDate;
	private LocalDate modifyDate;
}
