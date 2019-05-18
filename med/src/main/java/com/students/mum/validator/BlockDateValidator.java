package com.students.mum.validator;

import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.students.mum.domain.Block;

@Component
public class BlockDateValidator implements ConstraintValidator<BlockDate, Block> {

	int maxValue = 50;
	int minValue = 5;

	public void initialize(BlockDate constraintAnnotation) {

		// get annotation parameter
		this.maxValue = constraintAnnotation.maximum();
		this.minValue = constraintAnnotation.min();

	}

	@Override
	public boolean isValid(Block value, ConstraintValidatorContext context) {
		if (value.getStartDate() == null) {
			return true;
		}
		Period preriod = Period.between(value.getStartDate(), value.getEndDate());
		int gap = preriod.getDays() + 1;
		if (gap < minValue || gap > maxValue) {
			return false;
		}
//		if (value.getSessionDays() > gap) {
//			return false;
//		}
		return true;
	}

}
