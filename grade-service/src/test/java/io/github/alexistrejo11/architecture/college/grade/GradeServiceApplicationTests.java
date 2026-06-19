package io.github.alexistrejo11.architecture.college.grade;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
		properties = {
				"spring.cloud.config.enabled=false",
				"spring.data.mongodb.repositories.enabled=false",
				"spring.autoconfigure.exclude=de.flapdoodle.embed.mongo.spring.autoconfigure.EmbeddedMongoAutoConfiguration,org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,org.springframework.cloud.netflix.eureka.server.EurekaServerAutoConfiguration",
		},
		classes = GradeServiceApplicationTests.TestGradeServiceApplication.class
)
@ActiveProfiles("test")
class GradeServiceApplicationTests {

	@MockBean
	private io.github.alexistrejo11.architecture.college.grade.repository.AcademicHistoryRepository academicHistoryRepository;

	@MockBean
	private io.github.alexistrejo11.architecture.college.grade.repository.GradeRepository gradeRepository;

	@MockBean
	private io.github.alexistrejo11.architecture.college.grade.repository.GroupRepository groupRepository;

	@MockBean
	private io.github.alexistrejo11.architecture.college.grade.repository.SubjectRepository subjectRepository;

	@Test
	void contextLoads() {
	}

	@SpringBootApplication
	@ComponentScan(basePackages = {
			"io.github.alexistrejo11.architecture.college.grade",
			"io.github.alexistrejo11.architecture.college.common"
		})
	static class TestGradeServiceApplication {
	}
}
