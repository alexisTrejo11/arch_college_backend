package io.github.alexistrejo11.architecture.college.grade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaServer
@ComponentScan(basePackages = {"io.github.alexistrejo11.architecture.college.grade",
		"io.github.alexistrejo11.architecture.college.common",
})
public class GradeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradeServiceApplication.class, args);
	}

}
