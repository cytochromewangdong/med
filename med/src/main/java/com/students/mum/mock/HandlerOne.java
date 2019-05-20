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
import com.students.mum.domain.Role;
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
		// Faculty faculty = new Faculty();
		// faculty.setName("Prof1");
		// faculty.setType(FacultyType.professor);
		// LoginUser user = new LoginUser();
		// user.setUsername("4");
		// user.setPassword("2");
		// user.setRoles(roleRepository.findAll());
		// userService.signUp(user);
		//
		// faculty.setLoginUser(user);
		//
		// Block block = new Block();
		// Section section = new Section();
		// section.setCourse(courseRepository.findAll().get(0));
		// section.setProfessor(faculty);
		// section.setBlock(block);
		// faculty.getSectionList().add(section);
		//
		// List<Student> studentList = studentRepository.findByTestGroup(0);
		// section.getStudentList().addAll(studentList);
		// studentList.forEach(s -> s.getSectionList().add(section));
		//
		// block.setStartDate(LocalDate.of(2018, 10, 01));
		// block.setEndDate(LocalDate.of(2018, 10, 30));
		//// block.setSessionDays(22);
		// block.getSectionList().add(section);
		// facultyRepository.save(faculty);
		// blockRepository.save(block);
		// sectionRepository.save(section);

		// 1,10/13/18,EAM,DB
		// MeditationImportFile importFile = new MeditationImportFile();
		// importFile.setFileName("test.data");
		// importFile.setType(MeditationImportFileType.scan);
		//
		// MeditationImportFile importFile2 = new MeditationImportFile();
		// importFile2.setFileName("test2.data");
		// importFile2.setType(MeditationImportFileType.mannually);
		// meditationImportFileRepository.save(importFile);
		// importFile = new MeditationImportFile();
		// importFile.setFileName("test.data");
		// importFile.setType(MeditationImportFileType.scan);
		// meditationImportFileRepository.save(importFile);
		// meditationImportFileRepository.save(importFile2);
	}

	public void handleAll() {
		Role admin = createAdminRole();
		Role student = createStudentRole();
		Role faculty = createFacultyRole();

		Course c1 = createCourse("Web Application Architecture", "CS545", 4);
		Course c2 = createCourse("Fundamenatl Programing Practises", "CS390", 4);
		Course c3 = createCourse("Modern Programing Practises", "CS401", 4);

		Course c4 = createCourse("Algorithm", "CS472", 4);

		Block b1 = createBlock("201901Block", LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 25));
		Block b2 = createBlock("201902Block", LocalDate.of(2019, 2, 1), LocalDate.of(2019, 2, 25));
		Block b3 = createBlock("201904Block", LocalDate.of(2019, 4, 1), LocalDate.of(2019, 4, 25));
		// LoginUser user = new LoginUser();
		// user.setUsername("123");
		// user.setPassword("123");
		// //user.getRoles().add(student);
		// // loginUserRepository.save(user);
		// userService.signUp(user);

		// Course course = new Course();
		// course.setCode("CS545");
		// course.setName("(WAA)Web application architecture");
		// course.setCredit(4);
		// Course course2 = new Course();
		// course2.setCode("CS572");
		// course2.setName("(MWA)Modern web application");
		// course2.setCredit(4);
		// List<Course> sourseList = Arrays.asList(course, course2);
		//
		// courseRepository.saveAll(sourseList);

		Student s1 = createStudent("1001", "9001", "Balindra Rayamajhi", student, admin);
		Student s2 = createStudent("1002", "9002", "Dong  Wang", student);
		Student s3 = createStudent("1003", "9003", "Brhane Gerbreweld", student);
		Student s4 = createStudent("1004", "9004", "Robel Gebreweld", student);
		Student s5 = createStudent("1005", "9005", "Backhome", student);
		Student s6 = createStudent("1006", "9006", "Bqrtre", student);
		Student s7 = createStudent("1007", "9007", "qbcde", student);
		Student s8 = createStudent("1008", "9008", "xyz", student);
		Student s9 = createStudent("1009", "9009", "jkl", student);
		Student s10 = createStudent("1010", "9010", "asdhji", student);
		Student s11 = createStudent("1011", "9011", "uirest", student);

		Faculty f1 = createFaculty("Rujian Xing", FacultyType.professor);
		Faculty f2 = createFaculty("Paul Corozza", FacultyType.professor);
		Faculty f3 = createFaculty("Greg Guthire", FacultyType.professor);

		Section sec1 = createSection(b1, c1, f1, Arrays.asList(s1, s2, s3));
		Section sec2 = createSection(b1, c2, f2, Arrays.asList(s4, s5, s6));

		mockBlockData();
	}

	private Student createStudent(String number, String barcode, String name, Role... studentRole) {
		Student student = new Student();
		student.setNumber(number);
		student.setBarcode(barcode);
		student.setName(name);
		LoginUser user2 = new LoginUser();
		user2.setUsername(student.getNumber());
		user2.setPassword(bCryptPasswordEncoder.encode("2"));
		user2.getRoles().addAll(Arrays.asList(studentRole));
		student = studentRepository.save(student);
		student.setLoginUser(user2);
		user2.setTag(student.getNumber());
		return student;

	}

	private Faculty createFaculty(String name, FacultyType type) {
		Faculty faculty = new Faculty();
		faculty.setName(name);
		faculty.setType(type);

		LoginUser user2 = new LoginUser();
		user2.setUsername(faculty.getName().toLowerCase());
		user2.setPassword(bCryptPasswordEncoder.encode("3"));
		// user2.setRoles(createStudentRole());
		// userService.signUp(user2);
		faculty = facultyRepository.save(faculty);
		return faculty;

	}

	public Role createAdminRole() {
		return createRole("ADMIN");

	}

	public Role createFacultyRole() {
		return createRole("FACULTY");
	}

	public Role createStudentRole() {
		return createRole("STUDENT");
	}

	private Role createRole(String roleName) {
		Role r = new Role();
		r.setRole(roleName);
		return roleRepository.save(r);
	}

	private Course createCourse(String name, String code, int credit) {
		Course course = new Course();
		course.setName(name);
		course.setCode(code);
		course.setCredit(credit);
		return courseRepository.save(course);

	}

	private Block createBlock(String name, LocalDate start, LocalDate end) {
		Block block = new Block();
		block.setStartDate(start);
		block.setEndDate(end);
		block.setName(name);
		return blockRepository.save(block);

	}

	private Section createSection(Block block, Course course, Faculty faculty, List<Student> students) {
		Section section = new Section();
		section.setBlock(block);
		section.setCourse(course);
		section.setProfessor(faculty);
		section.setStudentList(students);
		block.getSectionList().add(section);
		faculty.getSectionList().add(section);
		students.forEach(s -> s.getSectionList().add(section));
		return sectionRepository.save(section);

	}

}
