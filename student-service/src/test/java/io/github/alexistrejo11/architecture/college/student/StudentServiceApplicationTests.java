package io.github.alexistrejo11.architecture.college.student;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
@ActiveProfiles("test")
class StudentServiceApplicationTests {

	@Test
	void contextLoads() {
	}
}

