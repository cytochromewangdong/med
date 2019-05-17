package com.students.mum.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.mum.domain.MeditationImportFile;
import com.students.mum.domain.MeditationImportFile.MeditationImportFileType;
import com.students.mum.domain.MeditationRecord;
import com.students.mum.domain.Student;
import com.students.mum.repository.MeditationImportFileRepository;
import com.students.mum.repository.MeditationRecordRepository;
import com.students.mum.repository.StudentRepository;

import lombok.Getter;
import lombok.Setter;

@Service
public class ImportService {
	@Getter
	@Setter
	private static class MeditationTransitionRecord {
		private LocalDate date;
		private String studentId;
	}

	@Autowired
	private MeditationImportFileRepository meditationImportFileRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private MeditationRecordRepository meditationRecordRepository;

	@Transactional
	public boolean importData() {
		List<MeditationImportFile> importFileList = meditationImportFileRepository.findByFinished(false);
		if (importFileList.isEmpty()) {
			return false;
		}
		MeditationImportFile importFile = importFileList.get(0);
		importData(importFile);
		importFileList = meditationImportFileRepository.findByFinished(false);
		return importFileList.size() > 0;
	}

	public Map<String, String> studentMapper() {
		return studentRepository.findAll().stream().collect(Collectors.toMap(s -> s.getBarcode(), s -> s.getNumber()));
	}

	private DateTimeFormatter scanDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

	private DateTimeFormatter manuallyDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	public void importData(MeditationImportFile importFile) {

		Map<String, String> stuMapper = studentMapper();
		Set<String> stuSet = new HashSet<>(stuMapper.values());
		try (Scanner scanner = new Scanner(new File(importFile.getFileName()))) {
			int finishedLine = importFile.getCurrentLine();
			int currentLine = 0;
			Set<LocalDate> dateSet = new HashSet<>();
			Map<String,MeditationTransitionRecord> basket = new HashMap<>();
			// date = LocalDate.parse("27/09/2015", scanDateFormatter);
			boolean finished = true;
			while (scanner.hasNextLine()) {
				currentLine++;
				String row = scanner.nextLine();
				if (StringUtils.isEmpty(row)) {
					continue;
				}
				if (currentLine <= finishedLine) {
					continue;
				}
				if (importFile.getType() == MeditationImportFileType.scan) {
					String[] column = row.split(",");
					if (!"EAM".equals(column[2]) && !"AM".equals(column[2])) {
						continue;
					}

					String stdId = stuMapper.get(column[0]);
					if (stdId == null) {
						continue;
					}
					LocalDate date = LocalDate.parse(column[1], scanDateFormatter);
					MeditationTransitionRecord transitionRecord = new MeditationTransitionRecord();
					transitionRecord.setDate(date);
					transitionRecord.setStudentId(stdId);
					basket.put(combinateKey(transitionRecord.getStudentId(), transitionRecord.getDate()), transitionRecord);
					dateSet.add(date);

					// MeditationRecord record = new MeditationRecord();
					// record.setDate(LocalDate.parse(column[1], scanDateFormatter));
					// Student student = studentRepository.getOne(stdId);
					// record.setStudent(student);
					// student.getMeditationRecordList().add(record);
					// meditationRecordRepository.save(record);
				} else {
					String[] column = row.split(",");
					LocalDate date = LocalDate.parse(column[0], manuallyDateFormatter);
					if (!stuSet.contains(column[1].trim())) {
						continue;
					}
					MeditationTransitionRecord transitionRecord = new MeditationTransitionRecord();
					transitionRecord.setDate(date);
					transitionRecord.setStudentId(column[1].trim());
					basket.put(combinateKey(transitionRecord.getStudentId(), transitionRecord.getDate()), transitionRecord);
					dateSet.add(date);
				}
				if (basket.size() > 1000) {
					finished = false;
					break;
				}
			}
			Set<String> existingSet = getExistingMedidationData(dateSet);
			basket.values().forEach(transitionRecord -> {
				//Check duplication
				if (!existingSet.contains(combinateKey(transitionRecord.getStudentId(), transitionRecord.getDate()))) {
					 MeditationRecord record = new MeditationRecord();
					 record.setDate(transitionRecord.getDate());
					 Student student = studentRepository.getOne(transitionRecord.getStudentId());
					 record.setStudent(student);
					 student.getMeditationRecordList().add(record);
					 meditationRecordRepository.save(record);
				}
			});
			importFile.setFinished(finished);
			importFile.setCurrentLine(currentLine);
		} catch (FileNotFoundException e) {
			importFile.setFinished(true);
			importFile.setFailureReason(e.getMessage());
			e.printStackTrace();
		}

	}

	Set<String> getExistingMedidationData(Set<LocalDate> dateSet) {
		List<MeditationRecord> recordList = meditationRecordRepository.findByDateIn(dateSet);
		return recordList.stream().map(m -> combinateKey(m.getStudent().getNumber(), m.getDate()))
				.collect(Collectors.toSet());
	}

	private String combinateKey(String stuNumber, LocalDate date) {
		return stuNumber + "$" + date.toEpochDay();
	}
}
