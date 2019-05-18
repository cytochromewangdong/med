package com.students.mum.mock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.students.mum.domain.Block;
import com.students.mum.domain.Course;
import com.students.mum.domain.Faculty;
import com.students.mum.domain.Faculty.FacultyType;
import com.students.mum.domain.LoginUser;
import com.students.mum.domain.MeditationImportFile;
import com.students.mum.domain.MeditationImportFile.MeditationImportFileType;
import com.students.mum.domain.Section;
import com.students.mum.domain.Student;
import com.students.mum.repository.BlockRepository;
import com.students.mum.repository.CourseRepository;
import com.students.mum.repository.FacultyRepository;
import com.students.mum.repository.MeditationImportFileRepository;
import com.students.mum.repository.RoleRepository;
import com.students.mum.repository.SectionRepository;
import com.students.mum.repository.StudentRepository;
import com.students.mum.service.UserService;

@Service

@Transactional
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
	@Autowired
	private SectionRepository sectionRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private MeditationImportFileRepository meditationImportFileRepository;

	public void mockBlockData() {
		Faculty faculty = new Faculty();
		faculty.setName("Prof1");
		faculty.setType(FacultyType.professor);
		LoginUser user = new LoginUser();
		user.setUsername("4");
		user.setPassword("2");
		user.setRoles(roleRepository.findAll());
		userService.signUp(user);

		faculty.setLoginUser(user);

		Block block = new Block();
		Section section = new Section();
		section.setCourse(courseRepository.findAll().get(0));
		section.setProfessor(faculty);
		section.setBlock(block);
		faculty.getSectionList().add(section);

		List<Student> studentList = studentRepository.findByTestGroup(0);
		section.getStudentList().addAll(studentList);
		studentList.forEach(s -> s.getSectionList().add(section));

		block.setStartDate(LocalDate.of(2018, 10, 01));
		block.setEndDate(LocalDate.of(2018, 10, 30));
//		block.setSessionDays(22);
		block.getSectionList().add(section);
		facultyRepository.save(faculty);
		blockRepository.save(block);
		sectionRepository.save(section);

		// 1,10/13/18,EAM,DB
		MeditationImportFile importFile = new MeditationImportFile();
		importFile.setFileName("test.data");
		importFile.setType(MeditationImportFileType.scan);

		MeditationImportFile importFile2 = new MeditationImportFile();
		importFile2.setFileName("test2.data");
		importFile2.setType(MeditationImportFileType.mannually);
		meditationImportFileRepository.save(importFile);
		importFile = new MeditationImportFile();
		importFile.setFileName("test.data");
		importFile.setType(MeditationImportFileType.scan);
		meditationImportFileRepository.save(importFile);
		meditationImportFileRepository.save(importFile2);
	}

	public void handleAll() {
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
		user2.setPassword(bCryptPasswordEncoder.encode("2"));
		user2.setRoles(roleRepository.findAll());
		// userService.signUp(user2);
		student = studentRepository.save(student);
		student.setLoginUser(user2);
	}

	private void createStudent2() {
		Student student = new Student();
		student.setNumber("2");
		student.setBarcode("2");
		LoginUser user2 = new LoginUser();
		user2.setUsername("3");
		user2.setPassword(bCryptPasswordEncoder.encode("3"));
		user2.setRoles(roleRepository.findAll());
		// userService.signUp(user2);
		student = studentRepository.save(student);
		student.setLoginUser(user2);
	}

}
