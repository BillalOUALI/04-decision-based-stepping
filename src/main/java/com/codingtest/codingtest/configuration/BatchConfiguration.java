package com.codingtest.codingtest.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Slf4j
@Configuration
public class BatchConfiguration {
	
	

	@Bean
	Step startStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("startStep", jobRepository).tasklet((contribution, chunkContext) -> {
			log.info("This is the start tasklet");
			return RepeatStatus.FINISHED;
		}, transactionManager).build();
	}

	@Bean
	Step evenStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("evenStep", jobRepository).tasklet((contribution, chunkContext) -> {
			log.info("This is the even tasklet");
			return RepeatStatus.FINISHED;
		}, transactionManager).build();
	}

	@Bean
	Step oddStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("oddStep", jobRepository).tasklet((contribution, chunkContext) -> {
			log.info("This is the odd tasklet");
			return RepeatStatus.FINISHED;
		}, transactionManager).build();
	}

	@Bean
	JobExecutionDecider decider() {
		return new OddDecider();
	}

	@Bean
	Job job(JobRepository jobRepository, Step startStep, Step evenStep, Step oddStep, JobExecutionDecider decider) {
		return new JobBuilder("job", jobRepository).start(startStep).next(decider).from(decider).on("ODD").to(oddStep)
				.from(decider).on("EVEN").to(evenStep).from(oddStep).on("*").to(decider).from(decider).on("ODD")
				.to(oddStep).from(decider).on("EVEN").to(evenStep).end().
				listener(new JobExecutionListener() {
	                @Override
	                public void beforeJob(JobExecution jobExecution) {
	                	log.info(">> Job started");
	                }

	                @Override
	                public void afterJob(JobExecution jobExecution) {
	                	log.info(">> Job ended with status: " + jobExecution.getStatus());
	                }
	            }).build();
	}
}
