package com.students.mum.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.MeditationRecord;

@Repository
public interface MeditationRecordRepository extends JpaRepository<MeditationRecord, Long> {
	List<MeditationRecord> findByDateIn(Collection<LocalDate> dateList);
}
