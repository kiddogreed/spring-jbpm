package com.jrrd.jbpmdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("jBPM Demo Application Tests")
class JbpmdemoApplicationTests {

	@Test
	@DisplayName("Application context should load successfully")
	void contextLoads() {
		// This test verifies that the Spring application context loads without errors
	}

}
