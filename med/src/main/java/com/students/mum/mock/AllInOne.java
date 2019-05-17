package com.students.mum.mock;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.mum.domain.Block;
import com.students.mum.domain.Course;
import com.students.mum.domain.Faculty;
import com.students.mum.domain.Faculty.FacultyType;
import com.students.mum.domain.LoginUser;
import com.students.mum.domain.Student;
import com.students.mum.repository.BlockRepository;
import com.students.mum.repository.CourseRepository;
import com.students.mum.repository.FacultyRepository;
import com.students.mum.repository.RoleRepository;
import com.students.mum.repository.StudentRepository;
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
	private StudentRepository studentRepository;

	@Autowired
	private HandlerOne handlerOne;

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
		faculty.getBlockList().add(block);
		facultyRepository.save(faculty);
		blockRepository.save(block);

	}

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

		createStudent1();
		createStudent2();
		mockBlockData();
	}

	private void createStudent1() {
		Student student = new Student();
		student.setNumber("1");
		student.setBarcode("1");
		LoginUser user2 = new LoginUser();
		user2.setUsername("2");
		user2.setPassword("2");
		user2.setRoles(roleRepository.findAll());
		userService.signUp(user2);
		studentRepository.save(student);
	}
	private void createStudent2() {
		Student student = new Student();
		student.setNumber("2");
		student.setBarcode("2");
		LoginUser user2 = new LoginUser();
		user2.setUsername("3");
		user2.setPassword("3");
		user2.setRoles(roleRepository.findAll());
		userService.signUp(user2);
		studentRepository.save(student);
	}

}
