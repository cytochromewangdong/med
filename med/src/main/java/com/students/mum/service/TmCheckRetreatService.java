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
import com.students.mum.dto.TmCheckRetreatDto;
import com.students.mum.repository.StudentRepository;
import com.students.mum.repository.TmCheckRetreatRepository;

@Service
@Transactional
public class TmCheckRetreatService {

	@Autowired
	private TmCheckRetreatRepository tmCheckRetreatRepository;

	@Autowired
	private StudentRepository studentRepository;

	public TmCheckRetreat saveTmCheckRetreat(TmCheckRetreatDto dto) {
		TmCheckRetreat tmCheckRetreat = dto.getId() == null ? new TmCheckRetreat()
				: tmCheckRetreatRepository.getOne(dto.getId());
		tmCheckRetreat.setDate(dto.getDate());
		tmCheckRetreat.setType(dto.getType());
		if (tmCheckRetreat.getStudent() != null) {
			tmCheckRetreat.getStudent().getTmCheckRetreatList().remove(tmCheckRetreat);
		}
		Student student = studentRepository.getOne(dto.getStudent().getNumber());
		tmCheckRetreat.setStudent(student);
		student.getTmCheckRetreatList().add(tmCheckRetreat);
		return tmCheckRetreatRepository.save(tmCheckRetreat);

	}

	public void removeTmCheckRetreat(long id) {
		TmCheckRetreat tmCheckRetreat = tmCheckRetreatRepository.getOne(id);
		tmCheckRetreat.setStudent(null);
		tmCheckRetreat.getStudent().getTmCheckRetreatList().remove(tmCheckRetreat);
	}

	public List<TmCheckRetreat> findTmCheckRetreatByDate(LocalDate startDate, LocalDate endDate) {
		return tmCheckRetreatRepository.findByDateBetweenOrderByIdDesc(startDate, endDate);
	}

	public boolean checkDuplicate(TmCheckRetreatDto dto) {
		return !studentRepository.getOne(dto.getStudent().getNumber()).getTmCheckRetreatList().stream()
				.filter(tm -> !tm.getId().equals(dto.getId()) && tm.getDate().equals(dto.getDate())).findAny()
				.isPresent();
	}
}
