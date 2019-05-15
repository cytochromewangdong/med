package com.students.mum.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.mum.domain.Student;
import com.students.mum.domain.TmCheckRetreat;
import com.students.mum.domain.TmCheckRetreat.CheckRetreatType;
import com.students.mum.repository.StudentRepository;
import com.students.mum.repository.TmCheckRetreatRepository;

@Service
@Transactional
public class TmCheckRetreatService {

	@Autowired
	private TmCheckRetreatRepository tmCheckRetreatRepository;

	@Autowired
	private StudentRepository studentRepository;

	public TmCheckRetreat addTmCheckRetreat(String studentNo, LocalDate date, CheckRetreatType type) {
		Optional<Student> optionalStudent = studentRepository.findById(studentNo);
		if (!optionalStudent.isPresent()) {
			// can be optimized
			return null;
		}
		TmCheckRetreat tmCheckRetreat = new TmCheckRetreat();
		tmCheckRetreat.setDate(date);
		tmCheckRetreat.setType(type);
		tmCheckRetreat.setStudent(optionalStudent.get());
		return tmCheckRetreatRepository.save(tmCheckRetreat);

	}

	public void removeTmCheckRetreat(long id) {
		tmCheckRetreatRepository.deleteById(id);
	}

	public TmCheckRetreat updateTmCheckRetreat(long id, String studentNo, LocalDate date, CheckRetreatType type) {
		Optional<Student> optionalStudent = studentRepository.findById(studentNo);
		if (!optionalStudent.isPresent()) {
			// can be optimized
			return null;
		}
		TmCheckRetreat tmCheckRetreat = tmCheckRetreatRepository.getOne(id);
		tmCheckRetreat.setDate(date);
		tmCheckRetreat.setType(type);
		tmCheckRetreat.setStudent(optionalStudent.get());
		return tmCheckRetreat;
	}

	public List<TmCheckRetreat> findTmCheckRetreatByDate(LocalDate startDate, LocalDate endDate) {
		return tmCheckRetreatRepository.findByDateBetweenOrderByIdDesc(startDate, endDate);
	}
}
