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
import com.students.mum.domain.Entry;
import com.students.mum.domain.ExcludedDate;
import com.students.mum.domain.MeditationRecord;
import com.students.mum.domain.Student;
import com.students.mum.domain.TmCheckRetreat;
import com.students.mum.domain.TmCheckRetreat.CheckRetreatType;
import com.students.mum.dto.GroupReport;
import com.students.mum.dto.ReportRow;
import com.students.mum.dto.StudentDetailReport;
import com.students.mum.dto.StudentDetailReport.MeditationResult;
import com.students.mum.repository.BlockRepository;
import com.students.mum.repository.EntryRepository;
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

	@Autowired
	private EntryRepository entryRepository;

	public StudentDetailReport summaryStudentDetailReport(StudentDetailReport reportParam) {

		Student stu = studentRepository.getOne(reportParam.getStudentNum());
		reportParam.setRetreatList(
				stu.getTmCheckRetreatList().stream().filter(t -> t.getType() == CheckRetreatType.retreat)
						.map(TmCheckRetreat::getDate).collect(Collectors.toList()));
		reportParam.setCheckList(stu.getTmCheckRetreatList().stream().filter(t -> t.getType() == CheckRetreatType.check)
				.map(TmCheckRetreat::getDate).collect(Collectors.toList()));

		if (reportParam.getBlockId() != null) {
			Block block = blockRepository.getOne(reportParam.getBlockId());
			reportParam.setBlockSessionSize(countAvailableSessionDays(block.getStartDate(), block.getEndDate()));
			LocalDate startDateExcluding = block.getStartDate().minusDays(1);
			LocalDate endDateExcluding = block.getEndDate().plusDays(1);
			// MeditationResult
			List<MeditationRecord> medRecord = stu.getMeditationRecordList();
			Set<LocalDate> medRecordDateSet = medRecord.stream()
					.filter(r -> r.getDate().isAfter(startDateExcluding) && r.getDate().isBefore(endDateExcluding))
					.sorted().map(MeditationRecord::getDate).collect(Collectors.toSet());
			reportParam.setMeditationBlockCount(medRecordDateSet.size());

			reportParam.setRecordListForBlock(makeList(block.getStartDate(), block.getEndDate(), medRecordDateSet));
		}
		return reportParam;
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

	@Override
	public GroupReport summaryEntryReport(GroupReport reportParam) {
		Entry entry = entryRepository.getOne(reportParam.getGroupId());
		int count = this.countAvailableSessionDays(entry.getMeditationStartDate(), LocalDate.now());
		reportParam.setData(entry.getEntryStudentStatisticsList().stream().map(s -> {
			ReportRow row = new ReportRow();
			row.setStudentName(s.getStudent().getName());
			row.setStudentNumber(s.getStudent().getNumber());
			row.setPresent(s.getRealMeditation());
			row.setPossible(count);
			row.setPercent(Math.round((row.getPresent() * 1000 / (double) count)) / 10.0);
			return row;
		}).collect(Collectors.toList()));
		return reportParam;
	}

	@Override
	public GroupReport summaryBlockReport(GroupReport reportParam) {
		Block entry = blockRepository.getOne(reportParam.getGroupId());
		int count = this.countAvailableSessionDays(entry.getStartDate(), entry.getEndDate());
		reportParam.setData(entry.getBlockStudentStatisticsList().stream().map(s -> {
			ReportRow row = new ReportRow();
			row.setStudentName(s.getStudent().getName());
			row.setStudentNumber(s.getStudent().getNumber());
			row.setPresent(s.getMedBlockCount());
			row.setPossible(count);
			row.setPercent(Math.round((row.getPresent() * 1000 / (double) count)) / 10.0);
			// 70%andabove: .5%EC(16daysinastandardblock)
			// 80%andabove: 1%EC(18days inastandardblock)
			// 90% and above: 1.5% EC (20 days in a standard block)
			double[] scoreList = new double[10];
			scoreList[7] = 0.5;
			scoreList[8] = 1;
			scoreList[9] = 1.5;
			int index = ((int) row.getPercent()) / 10;
			index = Math.min(index, 9);
			row.setScore(scoreList[index]);
			return row;
		}).collect(Collectors.toList()));
		return reportParam;
	}
}
