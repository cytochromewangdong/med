package com.students.mum.job;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.mum.domain.Block;
import com.students.mum.domain.BlockStudentStatistics;
import com.students.mum.domain.Entry;
import com.students.mum.domain.EntryStudentStatistics;
import com.students.mum.domain.MeditationRecord;
import com.students.mum.repository.AggregatingTaskDateRepository;
import com.students.mum.repository.BlockRepository;
import com.students.mum.repository.BlockStudentStatisticsRepository;
import com.students.mum.repository.EntryRepository;
import com.students.mum.repository.EntryStudentStatisticsRepository;
import com.students.mum.repository.ExcludedDateRepository;
import com.students.mum.repository.MeditationRecordRepository;
import com.students.mum.security.JwtAuthorizationFilter;

@Service
@Transactional
public class AggregatingService {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

	@Autowired
	private MeditationRecordRepository meditationRecordRepository;
	@Autowired
	private AggregatingTaskDateRepository aggregatingTaskDateRepository;
	@Autowired
	private ExcludedDateRepository excludedDateRepository;
	@Autowired
	private EntryStudentStatisticsRepository entryStudentStatisticsRepository;
	@Autowired
	private BlockStudentStatisticsRepository blockStudentStatisticsRepository;

	@Autowired
	private EntryRepository entryRepository;

	@Autowired
	private BlockRepository blockRepository;

	public void aggretating(LocalDate date) {
		if (excludedDateRepository.existsById(date)) {
			log.info("The date {} is excluded!", date);
			return;
		}

		if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			log.info("The date {} is Sunday!", date);
			return;
		}
		if (aggregatingTaskDateRepository.existsById(date)) {
			log.info("The date {}: has been done!", date);
			return;
		}
		List<MeditationRecord> recordsForDay = meditationRecordRepository.findByDateIn(Arrays.asList(date));
		if (recordsForDay.size() == 0) {
			log.info("There are not any data for {}!", date);
		}
		Set<String> studentMedRecordSet = recordsForDay.stream().map(r -> r.getStudent().getNumber())
				.collect(Collectors.toSet());
		aggregateEntry(studentMedRecordSet);

		Optional<Block> optBlock = blockRepository.findCurrentBlock(date);
		if (!optBlock.isPresent()) {
			log.info("There is not any block for {}, no need to do statistics!", date);
			return;
		}
		Block block = optBlock.get();
		Map<String, BlockStudentStatistics> blockStaStudentSet = block.getBlockStudentStatisticsList().stream()
				.collect(Collectors.toMap(e -> e.getStudent().getNumber(), e -> e));
		block.getSectionList().forEach(s -> {
			s.getStudentList().forEach(student -> {
				BlockStudentStatistics curr = blockStaStudentSet.get(student.getNumber());
				if (curr != null) {
					if (studentMedRecordSet.contains(student.getNumber())) {
						curr.setMedBlockCount(curr.getMedBlockCount() + 1);
					}
				} else {
					curr = new BlockStudentStatistics();
					curr.setBlock(block);

					if (studentMedRecordSet.contains(student.getNumber())) {
						curr.setMedBlockCount(1);
					}
					// Unidirectional
					curr.setStudent(student);
					block.getBlockStudentStatisticsList().add(curr);
					blockStudentStatisticsRepository.save(curr);
				}
			});
		});
	}

	private void aggregateEntry(Set<String> studentMedRecordSet) {
		List<Entry> allEntries = entryRepository.findAll();
		allEntries.forEach(entry -> {
			List<EntryStudentStatistics> entryStaList = entry.getEntryStudentStatisticsList();
			Map<String, EntryStudentStatistics> entryStaStudentSet = entryStaList.stream()
					.collect(Collectors.toMap(e -> e.getStudent().getNumber(), e -> e));
			entry.getStudentList().forEach(student -> {
				EntryStudentStatistics curr = entryStaStudentSet.get(student.getNumber());
				if (curr != null) {
//					curr.setPossibleMeditation(curr.getPossibleMeditation() + 1);
					if (studentMedRecordSet.contains(student.getNumber())) {
						curr.setRealMeditation(curr.getRealMeditation() + 1);
					}
				} else {
					curr = new EntryStudentStatistics();
					curr.setEntry(entry);
//					curr.setPossibleMeditation(1);
					if (studentMedRecordSet.contains(student.getNumber())) {
						curr.setRealMeditation(1);
					}
					// Unidirectional
					curr.setStudent(student);
					entry.getEntryStudentStatisticsList().add(curr);
					entryStudentStatisticsRepository.save(curr);
				}
			});
		});
	}
}
