package com.example.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

import jakarta.annotation.PostConstruct;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "vaadin-demo")
@EntityScan("com.example.application.data.entity")
@EnableJpaRepositories(basePackages = "com.example.application.repository")
@EnableTransactionManagement
public class Application implements AppShellConfigurator {

	@Autowired
	DBTools dbTools;

	@PostConstruct
	protected void onInit() {
		dbTools.clear();
		dbTools.create();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
