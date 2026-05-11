package io.github.alexistrejo11.architecture.college.enrollment.repository;

import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GroupRepository extends MongoRepository<Group, Long> {

    Optional<Group> findByGroupKeyAndSubjectKey(String groupKey, String subjectKey);
}