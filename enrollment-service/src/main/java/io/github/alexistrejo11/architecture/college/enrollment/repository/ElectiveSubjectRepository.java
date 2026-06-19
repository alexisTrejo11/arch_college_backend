package io.github.alexistrejo11.architecture.college.enrollment.repository;

import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.ElectiveSubject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ElectiveSubjectRepository extends MongoRepository<ElectiveSubject, String> {
    Optional<ElectiveSubject> findByKey(String key);
}