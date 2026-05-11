package io.github.alexistrejo11.architecture.college.enrollment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
@ActiveProfiles("test")
class EnrollmentServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
