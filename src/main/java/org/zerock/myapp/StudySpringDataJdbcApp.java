package org.zerock.myapp;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.Arrays;


//@Log4j2
@Slf4j

@NoArgsConstructor

@ServletComponentScan
@SpringBootApplication
public class StudySpringDataJdbcApp extends ServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(StudySpringDataJdbcApp.class, args);
		log.trace("main({}) invoked.", Arrays.toString(args));
	} // main

} // end class
