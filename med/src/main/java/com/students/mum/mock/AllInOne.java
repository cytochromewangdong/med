package com.students.mum.mock;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.mum.domain.Course;
import com.students.mum.domain.LoginUser;
import com.students.mum.repository.BlockRepository;
import com.students.mum.repository.CourseRepository;
import com.students.mum.repository.FacultyRepository;
import com.students.mum.repository.RoleRepository;
import com.students.mum.service.UserService;

@Service
public class AllInOne {

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

	@Autowired
	private HandlerOne handlerOne;

	@PostConstruct
	@Transactional
	public void createData() {
		LoginUser user = new LoginUser();
		user.setUsername("123");
		user.setPassword("123");
		user.setRoles(roleRepository.findAll());
		// loginUserRepository.save(user);
		userService.signUp(user);

		Course course = new Course();
		course.setCode("CS545");
		course.setName("(WAA)Web application architecture");
		course.setCredit(4);
		Course course2 = new Course();
		course2.setCode("CS572");
		course2.setName("(MWA)Modern web application");
		course2.setCredit(4);
		List<Course> sourseList = Arrays.asList(course, course2);

		courseRepository.saveAll(sourseList);
		handlerOne.mockBlockData();
	}

}
