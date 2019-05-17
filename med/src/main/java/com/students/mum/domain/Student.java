package com.students.mum.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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

	@ManyToMany(mappedBy = "studentList")
	private List<Block> blockList = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
	private List<TmCheckRetreat> tmCheckRetreatList = new ArrayList<>();

}
