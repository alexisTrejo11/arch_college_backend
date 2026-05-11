package io.github.alexistrejo11.architecture.college.enrollment.repository;

import io.github.alexistrejo11.architecture.college.enrollment.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentAccountNumberAndSchoolPeriod(String accountNumber, String schoolPeriod);
    List<Enrollment> findBySchoolPeriod(String schoolPeriod);
    Optional<Enrollment> findByGroupKeyAndSubjectKeyAndStudentAccountNumber(String groupKey, String subjectKey, String accountNumber);
}
