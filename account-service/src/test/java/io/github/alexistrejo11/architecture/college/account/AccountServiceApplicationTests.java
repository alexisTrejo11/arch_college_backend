package io.github.alexistrejo11.architecture.college.account;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
@ActiveProfiles("test")
class AccountServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
