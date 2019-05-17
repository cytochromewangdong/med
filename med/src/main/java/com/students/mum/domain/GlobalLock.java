package com.students.mum.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GlobalLock {
	@Id
	private String lockName;
	private String occupyId;
	
	private LocalDateTime lockTime;
	private Long threadId;
	@Version
	private int version;
}
