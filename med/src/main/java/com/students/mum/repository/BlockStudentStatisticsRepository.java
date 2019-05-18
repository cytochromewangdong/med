package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.BlockStudentStatistics;
@Repository
public interface BlockStudentStatisticsRepository extends JpaRepository<BlockStudentStatistics, Long> {

}
