package com.students.mum.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.ExcludedDate;

@Repository
public interface ExcludedDateRepository  extends JpaRepository<ExcludedDate, LocalDate> {

}
