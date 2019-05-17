package com.students.mum.domain;

import javax.persistence.Entity;
import javax.persistence.Version;

import com.students.mum.domain.base.DefaultPrimaryKeyEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MeditationImportFile extends DefaultPrimaryKeyEntity {

	public enum MeditationImportFileType {
		scan, mannually
	}

	private String fileName;

	private int currentLine;

	@Version
	private int version;

	private boolean finished;
	
	private MeditationImportFileType type;
	
	private String failureReason;

}
