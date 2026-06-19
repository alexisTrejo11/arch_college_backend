package io.github.alexistrejo11.architecture.college.enrollment.repository;

import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.Grade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GradeRepository extends MongoRepository<Grade, String> {
    List<Grade> findByStudentAccountNumber(String studentAccountNumber);
}