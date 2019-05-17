package com.students.mum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.MeditationImportFile;

@Repository
public interface MeditationImportFileRepository extends JpaRepository<MeditationImportFile, Long> {
	List<MeditationImportFile> findByFinished(boolean finished);
}
