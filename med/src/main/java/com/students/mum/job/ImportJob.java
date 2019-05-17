package com.students.mum.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.students.mum.service.GlobalLockService;

@Component
public class ImportJob {

	@Autowired
	GlobalLockService globalLockService;

	@Autowired
	ImportService importService;

	@Scheduled(fixedRate = 5000, initialDelay = 10000)
	public void regularJobInLock() {
		boolean locked = globalLockService.captureLock(Thread.currentThread().getId());
		try {
			if (locked) {
				importData();
			}
		} finally {
			globalLockService.releaseLock(locked, Thread.currentThread().getId());
		}
	}

	public void importData() {

		while(importService.importData()) {
			
		}
	}
}
