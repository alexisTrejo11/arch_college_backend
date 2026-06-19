package io.github.alexistrejo11.architecture.college.grade.repository;

import io.github.alexistrejo11.architecture.college.grade.model.AcademicHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AcademicHistoryRepository extends MongoRepository<AcademicHistory, String> {
    Optional<AcademicHistory> findByStudentAccountNumber(String accountNumber);
}