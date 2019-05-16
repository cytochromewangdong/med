package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
