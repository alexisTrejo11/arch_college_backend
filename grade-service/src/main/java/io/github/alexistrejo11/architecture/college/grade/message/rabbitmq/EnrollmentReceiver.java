package io.github.alexistrejo11.architecture.college.grade.message.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.alexistrejo11.architecture.college.grade.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.GroupEnrollmentDTO;
import io.github.alexistrejo11.architecture.college.grade.model.Group;
import io.github.alexistrejo11.architecture.college.grade.model.Subject;
import io.github.alexistrejo11.architecture.college.grade.service.GradeCommandService;
import io.github.alexistrejo11.architecture.college.grade.service.GroupService;
import io.github.alexistrejo11.architecture.college.grade.service.implementation.SubjectService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnrollmentReceiver {

    private final ObjectMapper objectMapper;
    private final SubjectService subjectService;
    private final GroupService groupService;
    private final GradeCommandService gradeCommandService;

    @RabbitListener(queues = RabbitMQConfig.EG_QUEUE)
    public void receiveGroupEnrollment(GroupEnrollmentDTO groupEnrollmentDTO) {
        try {
            log.info("receiveEnrollment -> receiving {} from group ID {}", groupEnrollmentDTO.getEnrollments().size(), groupEnrollmentDTO.getGroupId());
            Subject subject = subjectService.ensureSubjectsExist(groupEnrollmentDTO);
            Group group = groupService.createGroupFromEnrollment(groupEnrollmentDTO, subject);
            gradeCommandService.initGradesFromEnrollments(group, groupEnrollmentDTO.getEnrollments());
            log.info("receiveEnrollment -> grades successfully processed from group ID {}", groupEnrollmentDTO.getGroupId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

