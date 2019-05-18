package com.students.mum.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.AggregatingTaskDate;

@Repository
public interface AggregatingTaskDateRepository extends JpaRepository<AggregatingTaskDate, LocalDate> {

}
