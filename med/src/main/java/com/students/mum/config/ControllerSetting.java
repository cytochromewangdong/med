package com.students.mum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.students.mum.dto.DomainError;
import com.students.mum.dto.DomainErrors;

@ControllerAdvice
public class ControllerSetting {

//	@Autowired
//	private StudentRepository studentRepository;
//
//	@ModelAttribute("studentList")
//	List<Student> getStudentList() {
//		
//	}
	@Autowired
	MessageSourceAccessor messageAccessor;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public DomainErrors handleParameterException(MethodArgumentNotValidException  exception) {
		DomainErrors errors = new DomainErrors();
		errors.setErrorType("invalidException");
		exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
			errors.addError(new DomainError(messageAccessor.getMessage(fieldError)));
		});
		exception.getBindingResult().getGlobalErrors().forEach(err->{
			errors.addError(new DomainError(messageAccessor.getMessage(err)));
		});
		return errors;
	}

}
