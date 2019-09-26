package com.infosys.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class StudentDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentDemoApplication.class, args);
	}

}

/*
 * @SpringBootApplication public class StudentDemoApplication extends
 * SpringBootServletInitializer {
 * 
 * public static void main(String[] args) {
 * SpringApplication.run(StudentDemoApplication.class, args); }
 * 
 * @Override protected SpringApplicationBuilder
 * configure(SpringApplicationBuilder builder) { return
 * builder.sources(StudentDemoApplication.class); } }
 */
