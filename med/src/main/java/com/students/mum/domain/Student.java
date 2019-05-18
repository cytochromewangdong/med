package com.students.mum.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Student {

	@Id
	private String number;
	@Column(unique = true)
	private String barcode;

	
	private String name;
	@OneToOne(cascade = CascadeType.ALL)
	private LoginUser loginUser;

	@ManyToMany(mappedBy = "studentList")
	private List<Section> sectionList = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
	private List<TmCheckRetreat> tmCheckRetreatList = new ArrayList<>();

	private int testGroup;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
	private List<MeditationRecord> meditationRecordList = new ArrayList<>();

	@OneToMany(mappedBy = "student")
	private List<BlockStudentStatistics> blockStudentStatistics = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="ENTRY_ID")
	private Entry entry;
}
