package io.github.alexistrejo11.architecture.college.grade.service.implementation.AcademicHistory;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.models.grades.GradeResult;
import io.github.alexistrejo11.architecture.college.common.models.grades.GradeType;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.grade.mapper.GradeMapper;
import io.github.alexistrejo11.architecture.college.grade.model.AcademicHistory;
import io.github.alexistrejo11.architecture.college.grade.model.grade.Grade;
import io.github.alexistrejo11.architecture.college.grade.model.grade.GradeTrack;
import io.github.alexistrejo11.architecture.college.grade.repository.AcademicHistoryRepository;
import io.github.alexistrejo11.architecture.college.grade.service.AcademicHistoryService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcademicHistoryServiceImpl implements AcademicHistoryService {

    private final AcademicHistoryRepository academicHistoryRepository;
    private final GradeMapper gradeMapper;

    public Result<Void> validateAcademicHistoryInit(String accountNumber) {
       Optional<AcademicHistory> optionalAcademicHistory = academicHistoryRepository.findByStudentAccountNumber(accountNumber);
        if (optionalAcademicHistory.isPresent()) {
            return Result.error("student with account number" + accountNumber + "already have an academic history");
        }

        return Result.success();
    }

    @Override
    public void setGradeToAcademicHistory(Grade grade) {
        AcademicHistory academicHistory = academicHistoryRepository.findByStudentAccountNumber(grade.getStudentAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Academic History not found"));

        addGrade(grade, academicHistory.getGrades());

        if (grade.getGradeResult() == GradeResult.APPROVED && grade.getGradeType() == GradeType.CREDITABLE) {
            updateCreditData(academicHistory,  grade.getSubject().getSubjectCredits());
            academicHistory.reCalculatePercentages();
        }

        if (grade.getGradeValue() != null) {
            academicHistory.reCalculateAverage();
        }

        academicHistoryRepository.save(academicHistory);
    }

    public void addSpecialityToAcademicHistory(String speciality, AcademicHistory academicHistory) {
        academicHistory.setSpeciality(speciality);
        academicHistoryRepository.save(academicHistory);
    }


    @Override
    public void initAcademicHistory(StudentDTO studentDTO, CareerDTO careerDTO) {

        AcademicHistory academicHistory = AcademicHistory.builder()
                .careerKey(careerDTO.getKey())
                .careerName(careerDTO.getName())
                .studentAccountNumber(studentDTO.getAccountNumber())
                .incomeGeneration(studentDTO.getIncomeGeneration())
                .studentName(studentDTO.getFirstName() + " " + studentDTO.getLastName())
                .academicAverAge(0.00)
                .build();

        academicHistory.initCreditAdvance(careerDTO.getTotalObligatoryCredits(), careerDTO.getTotalElectiveCredits());

        academicHistoryRepository.save(academicHistory);
    }

    @Override
    public AcademicHistory getAcademicHistoryByAccountNumber(String accountNumber) {
        Optional<AcademicHistory> optionalAcademicHistory = academicHistoryRepository.findByStudentAccountNumber(accountNumber);
        if (optionalAcademicHistory.isEmpty()) {
            throw new EntityNotFoundException("student with account number" + accountNumber + "doesn't have academic history");
        }

        return optionalAcademicHistory.get();
    }

    private void sortGradeNameList(List<GradeTrack> grades) {
        grades.sort(Comparator.comparingLong(GradeTrack::getSubjectId));
    }

    private void updateCreditData(AcademicHistory academicHistory, int credits) {
        academicHistory.addOrdinaryCreditAdvance(credits);
        academicHistory.addTotalCreditAdvance(credits);
    }

    private void addGrade(Grade grade, List<GradeTrack> studentGrades) {
        GradeTrack newGrade = gradeMapper.entityToNamedDTO(grade);
        newGrade.increaseCoursedCount();

        boolean isGradeAlreadyCoursed = false;
        // Update Grade Track
        for (var studentGrade : studentGrades) {
            if (studentGrade.getSubjectId().equals(newGrade.getSubjectId())) {
               studentGrade.setLastSchoolPeriodCoursed(newGrade.getLastSchoolPeriodCoursed());
               studentGrade.setGradeValue(newGrade.getGradeValue());
               studentGrade.increaseCoursedCount();

               isGradeAlreadyCoursed = true;
            }
        }

        // Add New One
        if (!isGradeAlreadyCoursed) {
            studentGrades.add(newGrade);

            sortGradeNameList(studentGrades);
        }
    }

    @Override
    public void validateUniqueAcademicHistoryPerStudent(String accountNumber) {
        Optional<AcademicHistory> optionalAcademicHistory = academicHistoryRepository.findByStudentAccountNumber(accountNumber);
        if (optionalAcademicHistory.isPresent()) {
            throw new RuntimeException("Student with account number (" + accountNumber + ") already has academic history");
        }
    }

    @Override
    public void validateGrade(Grade grade) {
        AcademicHistory academicHistory = academicHistoryRepository.findByStudentAccountNumber(grade.getStudentAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("Student with account number "
                        + grade.getStudentAccountNumber() + " doesn't have an academic history"));

        boolean updated = academicHistory.getGrades().stream()
                .filter(gradeTrack -> gradeTrack.getSubjectId().equals(grade.getSubject().getSubjectId()))
                .peek(gradeTrack -> gradeTrack.setAuthorized(true))
                .findFirst()
                .isPresent();

        if (!updated) {
            throw new EntityNotFoundException("GradeTrack with subject ID "
                    + grade.getSubject().getSubjectId() + " not found in academic history");
        }

        academicHistoryRepository.save(academicHistory);
    }
}
