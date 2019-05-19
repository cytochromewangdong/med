package com.students.mum.service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.mum.domain.MeditationImportFile;
import com.students.mum.domain.Student;
import com.students.mum.domain.TmCheckRetreat;
import com.students.mum.dto.TmCheckRetreatDto;
import com.students.mum.dto.UploadMed;
import com.students.mum.repository.MeditationImportFileRepository;
import com.students.mum.repository.StudentRepository;
import com.students.mum.repository.TmCheckRetreatRepository;

@Service
@Transactional
public class TmCheckRetreatService {

	@Autowired
	private TmCheckRetreatRepository tmCheckRetreatRepository;

	@Autowired
	private StudentRepository studentRepository;

	private final File UPLOAD_DIR = new File(System.getProperty("user.dir"),"data");

	@PostConstruct
	void init() {
		if (!UPLOAD_DIR.exists()) {
			UPLOAD_DIR.mkdirs();
		}
	}

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

	@Autowired
	private MeditationImportFileRepository meditationImportFileRepository;

	public void uploadMedDataFileService(UploadMed upload) {
		File dataFile = new File(UPLOAD_DIR, UUID.randomUUID().toString());
		try {
			upload.getDataFile().transferTo(dataFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		MeditationImportFile file = new MeditationImportFile();
		file.setFileName(dataFile.getAbsolutePath());
		file.setType(upload.getType());
		file.setCreateDate(LocalDateTime.now());
		meditationImportFileRepository.save(file);
	}
	public List<MeditationImportFile> getMeditationImportFileList(){
		return meditationImportFileRepository.findAll();
	}
}
