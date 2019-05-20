package com.students.mum.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.students.mum.domain.Block;
import com.students.mum.domain.Entry;
import com.students.mum.domain.ExcludedDate;
import com.students.mum.domain.Faculty;
import com.students.mum.domain.MeditationRecord;
import com.students.mum.domain.Student;
import com.students.mum.domain.TmCheckRetreat;
import com.students.mum.domain.TmCheckRetreat.CheckRetreatType;
import com.students.mum.dto.GroupReport;
import com.students.mum.dto.ReportRow;
import com.students.mum.dto.SectionBlock;
import com.students.mum.dto.StudentDetailReport;
import com.students.mum.dto.StudentDetailReport.MeditationResult;
import com.students.mum.job.AggregatingService;
import com.students.mum.repository.BlockRepository;
import com.students.mum.repository.EntryRepository;
import com.students.mum.repository.ExcludedDateRepository;
import com.students.mum.repository.FacultyRepository;
import com.students.mum.repository.MeditationRecordRepository;
import com.students.mum.repository.StudentRepository;
import com.students.mum.security.UserDetailAndTag;

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
		reportParam.setOverallPresent(
				stu.getBlockStudentStatistics().stream().mapToInt((b -> b.getMedBlockCount())).sum());
		reportParam.setOverallPossible(
				countAvailableSessionDays(stu.getEntry().getMeditationStartDate(), LocalDate.now()));
		reportParam.setOverallPercent(
				Math.round((reportParam.getOverallPresent() * 1000 / (double) reportParam.getOverallPossible()))
						/ 10.0);
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
					.map(MeditationRecord::getDate).sorted().collect(Collectors.toSet());
			reportParam.setPresent(medRecordDateSet.size());
			reportParam.setRecordListForBlock(makeList(block.getStartDate(), block.getEndDate(), medRecordDateSet));
			reportParam.setPossible(reportParam.getRecordListForBlock().size());
			reportParam.setPercent(
					Math.round((reportParam.getPresent() * 1000 / (double) reportParam.getRecordListForBlock().size()))
							/ 10.0);

			// private int overallPossible;
			// private int overallPresent;
			// private double overallPercent;
			// 70%andabove: .5%EC(16daysinastandardblock)
			// 80%andabove: 1%EC(18days inastandardblock)
			// 90% and above: 1.5% EC (20 days in a standard block)
			double percent = reportParam.getPercent();
			double score = calculateScore(percent);
			reportParam.setScore(score);
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
			double percent = row.getPercent();
			double score = calculateScore(percent);
			row.setScore(score);
			return row;
		}).collect(Collectors.toList()));
		return reportParam;
	}

	private double calculateScore(double percent) {
		double[] scoreList = new double[10];
		scoreList[7] = 0.5;
		scoreList[8] = 1;
		scoreList[9] = 1.5;
		int index = ((int) percent) / 10;
		index = Math.min(index, 9);
		double score = scoreList[index];
		return score;
	}

	@Autowired
	private AggregatingService AggregatingService;
	@Autowired
	private MeditationRecordRepository meditationRecordRepository;

	@Override
	public void calculateAll() {
		meditationRecordRepository.findDistinctDate().forEach(AggregatingService::aggretating);
	}

	@Autowired
	private FacultyRepository facultyRepository;

	@Override
	public List<SectionBlock> getSectionList() {
		UserDetailAndTag tag = (UserDetailAndTag) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long id = Long.parseLong(tag.getTag());
		Faculty faculty = facultyRepository.getOne(id);
		return faculty.getSectionList().stream().map(f -> {
			SectionBlock sb = new SectionBlock();
			sb.setBlockId(f.getBlock().getId());
			sb.setCombinName(f.getCourse().getName() + "(" + f.getBlock().getName() + ")");
			return sb;
		}).collect(Collectors.toList());
	}
}
