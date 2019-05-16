package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

}
