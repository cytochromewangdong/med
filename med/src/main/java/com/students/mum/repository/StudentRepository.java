package com.students.mum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

//	@Query("SELECT o FROM Student o JOIN FETCH o.sectionList i WHERE o.testGroup = ?1")
	List<Student> findByTestGroup(int testGroup);
}
