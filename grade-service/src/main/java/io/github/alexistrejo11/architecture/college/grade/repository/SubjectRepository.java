package io.github.alexistrejo11.architecture.college.grade.repository;

import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;
import io.github.alexistrejo11.architecture.college.grade.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findBySubjectIdAndSubjectType(Long subjectId, SubjectType subjectType);
}
