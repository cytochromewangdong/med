package com.students.mum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.students.mum.domain.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

}
