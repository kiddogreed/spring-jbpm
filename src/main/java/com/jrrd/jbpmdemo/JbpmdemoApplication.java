package com.jrrd.jbpmdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JbpmdemoApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(JbpmdemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JbpmdemoApplication.class, args);
		logger.info("jBPM to Kogito Migration Demo Application Started!");
		logger.info("Access the REST API at http://localhost:8080/api/leave");
	}

}
