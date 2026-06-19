package io.github.alexistrejo11.architecture.college.curriculum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaServer
@ComponentScan(basePackages = {"io.github.alexistrejo11.architecture.college.curriculum",
		"io.github.alexistrejo11.architecture.college.common",
})
public class CurriculumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurriculumServiceApplication.class, args);
	}

}
