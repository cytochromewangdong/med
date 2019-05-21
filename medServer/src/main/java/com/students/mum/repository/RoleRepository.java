package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
