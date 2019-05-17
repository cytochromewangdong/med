package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.students.mum.domain.GlobalLock;

public interface GlobalLockRepository extends JpaRepository<GlobalLock, String> {

}
