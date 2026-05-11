package io.github.alexistrejo11.architecture.college.curriculum;

import org.junit.jupiter.api.Test;
import io.github.alexistrejo11.architecture.college.schedule.ScheduleServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
		properties = "spring.cloud.config.enabled=false",
		classes = ScheduleServiceApplication.class
)
@ActiveProfiles("test")
class SubjectServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
