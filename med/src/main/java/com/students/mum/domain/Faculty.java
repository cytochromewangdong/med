package com.students.mum.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Faculty {
	public enum FacultyType {
		professor, normal
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	private FacultyType type;
	@OneToOne(cascade = CascadeType.ALL)
	private LoginUser loginUser;

	@OneToMany(mappedBy = "professor")
	private List<Section> sectionList = new ArrayList<>();
}
