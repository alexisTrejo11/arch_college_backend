package io.github.alexistrejo11.architecture.college.schedule.repository;

import io.github.alexistrejo11.architecture.college.schedule.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByAccountNumber(String accountNumber);
}
