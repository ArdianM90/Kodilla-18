package com.crud.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//public class TasksApplication extends SpringBootServletInitializer implements CommandLineRunner {
public class TasksApplication extends SpringBootServletInitializer {
//	@Autowired
//	SimpleEmailService emailService;
//	@Autowired
//	AdminConfig adminConfig;

	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TasksApplication.class);
	}

//	@Override
//	public void run(String... args) {
//		int quantity = 14;
//		emailService.send(new Mail(
//				"kodilla.course.2020@gmail.com",
//				null,
//				new Date(),
//				"Daily information about tasks quantity",
//				"Currently you have "+quantity+" tasks."
//		));
//	}
}
