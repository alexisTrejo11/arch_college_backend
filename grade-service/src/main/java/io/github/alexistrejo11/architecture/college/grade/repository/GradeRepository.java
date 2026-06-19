package io.github.alexistrejo11.architecture.college.grade.repository;

import io.github.alexistrejo11.architecture.college.common.models.grades.GradeStatus;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;
import io.github.alexistrejo11.architecture.college.grade.model.grade.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface GradeRepository extends JpaRepository<Grade, Long>, JpaSpecificationExecutor<Grade> {
    List<Grade> findByStudentAccountNumberAndSchoolPeriod(String accountNumber, String schoolPeriod);
    Page<Grade> findByGradeStatus(GradeStatus status, Pageable pageable);
    List<Grade> findByStudentAccountNumberAndSubjectIdAndSubject_SubjectType(String accountNumber, Long subjectId, SubjectType subjectType);
}
