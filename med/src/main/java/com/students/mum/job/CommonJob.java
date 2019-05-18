package com.students.mum.job;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.students.mum.service.GlobalLockService;

@Component
public class CommonJob {

	@Autowired
	GlobalLockService globalLockService;

	@Autowired
	ImportService importService;

	@Autowired
	AggregatingService aggregatingService;

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

		while (importService.importData()) {

		}
	}

	@Scheduled(cron = "0 0 23 * * *")
	public void fixTimeTask() {
		aggregatingService.aggretating(LocalDate.now());
	}
}
