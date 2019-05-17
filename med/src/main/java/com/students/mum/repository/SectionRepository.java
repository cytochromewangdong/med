package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

}
