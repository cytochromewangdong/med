package com.students.mum.mock;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.mum.domain.Block;
import com.students.mum.domain.Faculty;
import com.students.mum.domain.Faculty.FacultyType;
import com.students.mum.domain.LoginUser;
import com.students.mum.repository.BlockRepository;
import com.students.mum.repository.CourseRepository;
import com.students.mum.repository.FacultyRepository;
import com.students.mum.repository.RoleRepository;
import com.students.mum.service.UserService;

@Service
public class HandlerOne {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserService userService;

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private FacultyRepository facultyRepository;
	@Autowired
	private BlockRepository blockRepository;

	@Transactional
	public void mockBlockData() {
		Faculty faculty = new Faculty();
		faculty.setName("Prof1");
		faculty.setType(FacultyType.professor);
		LoginUser user = new LoginUser();
		user.setUsername("2");
		user.setPassword("2");
		user.setRoles(roleRepository.findAll());
		userService.signUp(user);

		faculty.setLoginUser(user);

		Block block = new Block();
		block.setCourse(courseRepository.findAll().get(0));
		block.setProfessor(faculty);
//		faculty.getBlockList().add(block);
		facultyRepository.save(faculty);
		blockRepository.save(block);
	

	}
}
