package com.students.mum.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.students.mum.domain.GlobalLock;
import com.students.mum.repository.GlobalLockRepository;

@Service
public class GlobalLockService {
	@Autowired
	private GlobalLockRepository globalLockRepository;

	public final static String TASK_LOCK = "importTaskLock";
	@Value("${instanceId:onlyOne}")
	private String instanceId;

	@PostConstruct
	void cleanLock() {
		Optional<GlobalLock> optLock = globalLockRepository.findById(TASK_LOCK);
		if (optLock.isPresent() && instanceId.equals(optLock.get().getOccupyId())) {
			globalLockRepository.deleteById(TASK_LOCK);
		}
	}

	@Transactional
	public boolean captureLock(Long threadId) {
		Optional<GlobalLock> optLock = globalLockRepository.findById(TASK_LOCK);
		if (!optLock.isPresent()) {
			GlobalLock lock = new GlobalLock();
			lock.setLockName(TASK_LOCK);
			lock.setOccupyId(instanceId);
			lock.setLockTime(LocalDateTime.now());
			lock.setThreadId(threadId);
			globalLockRepository.save(lock);
			return true;
		} else {
			if (optLock.get().getOccupyId() == null) {
				optLock.get().setOccupyId(instanceId);
				optLock.get().setLockTime(LocalDateTime.now());
				optLock.get().setThreadId(threadId);
				return true;
			} else {
				return instanceId.equals(optLock.get().getOccupyId()) && threadId.equals(optLock.get().getThreadId());
			}
		}
		// globalLockRepository.getOne(TASK_LOCK);
	}

	@Transactional
	public void releaseLock(boolean locked, Long threadId) {
		if (!locked) {
			return;
		}
		Optional<GlobalLock> optLock = globalLockRepository.findById(TASK_LOCK);
		if (!optLock.isPresent()) {
			return;
		} else {
			if (instanceId.equals(optLock.get().getOccupyId()) && threadId.equals(optLock.get().getThreadId())) {
				optLock.get().setOccupyId(null);
				optLock.get().setThreadId(null);
			}
		}
		// globalLockRepository.getOne(TASK_LOCK);
	}

}
