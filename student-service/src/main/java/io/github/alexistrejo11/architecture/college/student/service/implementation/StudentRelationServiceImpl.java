package io.github.alexistrejo11.architecture.college.student.service.implementation;

import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Grade.InitAcademicHistory;
import io.github.alexistrejo11.architecture.college.common.dto.ProfessionalLine.ProfessionalLineDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.service.curriculum.AcademicCurriculumFacadeService;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.student.service.StudentRelationService;
import io.github.alexistrejo11.architecture.college.student.messaging.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class StudentRelationServiceImpl implements StudentRelationService {

    private final AcademicCurriculumFacadeService academicCurriculumFacadeService;
    private final RabbitMQSender rabbitMQSender;

    @Autowired
    public StudentRelationServiceImpl(@Qualifier("AcademicCurriculumFacadeServiceImpl") AcademicCurriculumFacadeService academicCurriculumFacadeService,
                                      RabbitMQSender rabbitMQSender) {
        this.academicCurriculumFacadeService = academicCurriculumFacadeService;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Override
    public Result<Void> validateExistingCareerId(Long careerId) {
        CareerDTO careerDTO = academicCurriculumFacadeService.getCareerById(careerId).join();
        if (careerDTO == null) {
            return Result.error("Career with ID" + careerId + " not found" );
        }

        return Result.success();
    }

    @Override
    public Result<Void> validateProfessionalLineId(Long professionalLineId) {
        ProfessionalLineDTO professionalLineDTO = academicCurriculumFacadeService.getProfessionalLineById(professionalLineId).join();
        if (professionalLineDTO == null) {
            return Result.error("Professional Line with ID" + professionalLineId + " not found" );
        }

        return Result.success();
    }


    @Override
    @Async("taskExecutor")
    public void initAcademicHistoryAsync(StudentDTO studentDTO) {
         CompletableFuture.runAsync(() -> {
            CareerDTO careerDTO = academicCurriculumFacadeService.getCareerById(studentDTO.getCareerId()).join();

            InitAcademicHistory initAcademicHistory = new InitAcademicHistory(studentDTO, careerDTO);

            rabbitMQSender.queueAcademicHistoryInit(initAcademicHistory);
        });
    }
}
