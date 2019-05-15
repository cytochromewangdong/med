package com.students.mum.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.TmCheckRetreat;

@Repository
public interface TmCheckRetreatRepository extends JpaRepository<TmCheckRetreat, Long> {
	List<TmCheckRetreat> findByDateBetweenOrderByIdDesc(LocalDate start, LocalDate end);
}
