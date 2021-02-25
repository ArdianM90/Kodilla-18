package com.crud.tasks;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Date;

@SpringBootApplication
public class TasksApplication extends SpringBootServletInitializer implements CommandLineRunner {
	@Autowired
	SimpleEmailService emailService;
	@Autowired
	AdminConfig adminConfig;

	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TasksApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		int quantity = 14;
		emailService.send(new Mail(
				"kodilla.course.2020@gmail.com",
				null,
				new Date(),
				"Daily information about tasks quantity",
				"Currently you have "+quantity+" tasks."
		));
	}
}
