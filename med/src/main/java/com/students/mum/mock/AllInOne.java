package com.students.mum.mock;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllInOne {

	@Autowired
	private HandlerOne handlerOne;

	@PostConstruct
	public void createData() {
		handlerOne.handleAll();
	}

}
