package io.github.alexistrejo11.architecture.college.curriculum.repository;

import io.github.alexistrejo11.architecture.college.curriculum.model.Career.ProfessionalLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionalLineRepository extends JpaRepository<ProfessionalLine, Long> {
    Optional<ProfessionalLine> findByName(String name);
}
