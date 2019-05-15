package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.Student;

@Repository
public interface StudentRepository  extends JpaRepository<Student, String> {

}
