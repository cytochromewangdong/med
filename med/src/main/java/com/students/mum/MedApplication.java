package com.students.mum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class MedApplication {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("errorMessages", "messages");
		return messageSource;
	}

	@Bean
	MessageSourceAccessor message() {
		return new MessageSourceAccessor(messageSource());
	}

//	@Bean
//	public JobDetail sampleJobDetail() {
//		return JobBuilder.newJob(ImportJob.class).withIdentity("sampleJob")
//				.usingJobData("name", "World").storeDurably().build();
//	}
//
//	@Bean
//	public Trigger sampleJobTrigger() {
//		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//				.withIntervalInSeconds(2).repeatForever();
//
//		return TriggerBuilder.newTrigger().forJob(sampleJobDetail())
//				.withIdentity("sampleTrigger").withSchedule(scheduleBuilder).build();
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(MedApplication.class, args);
	}

}
