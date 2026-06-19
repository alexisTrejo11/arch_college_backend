package io.github.alexistrejo11.architecture.college.enrollment.service.implementation;

import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.enrollment.message.rabbitmq.EnrollmentGradeProducer;
import io.github.alexistrejo11.architecture.college.enrollment.model.Enrollment;
import io.github.alexistrejo11.architecture.college.enrollment.repository.EnrollmentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentLockService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentGradeProducer enrollmentGradeProducer;
    private static final String schoolPeriod = AcademicData.getCurrentSchoolPeriod();

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleLockEnrollments() {
        LocalDateTime targetDate = getLockDate();

        if (LocalDateTime.now().isAfter(targetDate)) {
            lockEnrollments();
        }
    }

    public LocalDateTime getLockDate() {
        return AcademicData.getCurrentSchoolPeriodStartDate().plusMonths(2);
    }

    private void lockEnrollments() {
        List<Enrollment> currentEnrollments = enrollmentRepository.findBySchoolPeriod(schoolPeriod);
        List<Enrollment> currentEnrollmentsLocked = currentEnrollments.stream()
                .peek(Enrollment::lock)
                .toList();

        enrollmentRepository.saveAll(currentEnrollmentsLocked);

        enrollmentGradeProducer.sendEnrollmentsToGradeService(currentEnrollments);
    }
}
