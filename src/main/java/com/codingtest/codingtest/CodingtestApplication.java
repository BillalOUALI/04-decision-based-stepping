package com.codingtest.codingtest;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CodingtestApplication {

	public static void main(String[] args) {
		System.out.println("App starts ... ");
		SpringApplication.run(CodingtestApplication.class, args);
		System.out.println("App started .... ");
	}

//	@Bean
//	CommandLineRunner runJob(JobLauncher jobLauncher, Job job) {
//		return args -> {
//			System.out.println("Déclenchement du batch ...");
//			jobLauncher.run(job, new JobParametersBuilder().addLong("time", System.currentTimeMillis()) 
//					.toJobParameters());
//		};
//	}

}
