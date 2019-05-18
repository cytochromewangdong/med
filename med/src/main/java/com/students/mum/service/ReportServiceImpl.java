package com.students.mum.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.mum.domain.Block;
import com.students.mum.domain.ExcludedDate;
import com.students.mum.domain.MeditationRecord;
import com.students.mum.domain.Student;
import com.students.mum.domain.TmCheckRetreat;
import com.students.mum.domain.TmCheckRetreat.CheckRetreatType;
import com.students.mum.dto.StudentDetailReport;
import com.students.mum.dto.StudentDetailReport.MeditationResult;
import com.students.mum.repository.BlockRepository;
import com.students.mum.repository.ExcludedDateRepository;
import com.students.mum.repository.StudentRepository;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

	@Autowired
	private BlockRepository blockRepository;

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ExcludedDateRepository excludedDateRepository;

	public StudentDetailReport summaryStudentDetailReport(StudentDetailReport report) {

		Student stu = studentRepository.getOne(report.getStudentNum());
		report.setRetreatList(stu.getTmCheckRetreatList().stream().filter(t -> t.getType() == CheckRetreatType.retreat)
				.map(TmCheckRetreat::getDate).collect(Collectors.toList()));
		report.setCheckList(stu.getTmCheckRetreatList().stream().filter(t -> t.getType() == CheckRetreatType.check)
				.map(TmCheckRetreat::getDate).collect(Collectors.toList()));

		if (report.getBlockId() != null) {
			Block block = blockRepository.getOne(report.getBlockId());
			report.setBlockSessionSize(countAvailableSessionDays(block.getStartDate(), block.getEndDate()));
			LocalDate startDateExcluding = block.getStartDate().minusDays(1);
			LocalDate endDateExcluding = block.getEndDate().plusDays(1);
			// MeditationResult
			List<MeditationRecord> medRecord = stu.getMeditationRecordList();
			Set<LocalDate> medRecordDateSet = medRecord.stream()
					.filter(r -> r.getDate().isAfter(startDateExcluding) && r.getDate().isBefore(endDateExcluding))
					.sorted().map(MeditationRecord::getDate).collect(Collectors.toSet());
			report.setMeditationBlockCount(medRecordDateSet.size());

			report.setRecordListForBlock(makeList(block.getStartDate(), block.getEndDate(), medRecordDateSet));
		}
		return report;
	}

	private int countAvailableSessionDays(LocalDate startDate, LocalDate endDate) {
		LocalDate currentDate = startDate;
		int count = 0;
		Set<LocalDate> execludedSet = excludedDateRepository.findAll().stream().map(ExcludedDate::getDate)
				.collect(Collectors.toSet());
		while (currentDate.compareTo(endDate) <= 0) {
			if (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY && !execludedSet.contains(currentDate)) {

				count++;
			}
			currentDate = currentDate.plusDays(1);
		}
		return count;
	}

	private List<MeditationResult> makeList(LocalDate startDate, LocalDate endDate, Set<LocalDate> medRecordDateSet) {
		LocalDate currentDate = startDate;
		List<MeditationResult> result = new ArrayList<>();
		while (currentDate.compareTo(endDate) <= 0) {
			result.add(new MeditationResult(currentDate, medRecordDateSet.contains(currentDate)));
			currentDate = currentDate.plusDays(1);
		}
		return result;
	}
}
