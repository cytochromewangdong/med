package com.students.mum.domain.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class DefaultPrimaryKeyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
}
