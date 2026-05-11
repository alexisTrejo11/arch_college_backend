package io.github.alexistrejo11.architecture.college.grade.message.rabbitmq;

import io.github.alexistrejo11.architecture.college.grade.config.RabbitMQConfig;
import io.github.alexistrejo11.architecture.college.grade.service.AcademicHistoryService;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Grade.InitAcademicHistory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AcademicHistoryReceiver {

    private final AcademicHistoryService academicHistoryService;

    @Autowired
    public AcademicHistoryReceiver(AcademicHistoryService academicHistoryService) {
        this.academicHistoryService = academicHistoryService;
    }

    @RabbitListener(queues = RabbitMQConfig.AH_QUEUE)
    public void processAcademicHistory(InitAcademicHistory initAcademicHistory) {
        log.info("Processing message to create academic history for user with account number: {}", initAcademicHistory.getStudent().getAccountNumber());
        academicHistoryService.validateUniqueAcademicHistoryPerStudent(initAcademicHistory.getStudent().getAccountNumber());

        academicHistoryService.initAcademicHistory(initAcademicHistory.getStudent(), initAcademicHistory.getCareer());
        log.info("Academic History created for student: {}", initAcademicHistory.getStudent().getAccountNumber());
    }
}