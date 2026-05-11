package io.github.alexistrejo11.architecture.college.curriculum.repository;

import io.github.alexistrejo11.architecture.college.curriculum.model.Career.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareerRepository extends JpaRepository<Career, Long> {
    Optional<Career> findByName(String name);
}
