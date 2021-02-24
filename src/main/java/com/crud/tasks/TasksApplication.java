package com.crud.tasks;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TasksApplication extends SpringBootServletInitializer implements CommandLineRunner {
	@Autowired
	SimpleEmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TasksApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		Mail mail = new Mail(
				"amienkowski@gmail.com",
				"kodilla.course.2020@gmail.com",
				"TESTS",
				"New card: has been created on your Trello account.");
		emailService.send(mail);
	}
}
