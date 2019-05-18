package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.Entry;
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

}