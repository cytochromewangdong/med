package com.students.mum.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Student {
	
	@Id
	private String number;
	private String barcode;
	
	@OneToOne
	private LoginUser loginUser;

}
