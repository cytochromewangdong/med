package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.students.mum.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
