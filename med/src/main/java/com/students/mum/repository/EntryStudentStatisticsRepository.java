package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.EntryStudentStatistics;
@Repository
public interface EntryStudentStatisticsRepository extends JpaRepository<EntryStudentStatistics, Long> {

}