package io.github.alexistrejo11.architecture.college.teacher.queue.rabbitmq;

import io.github.alexistrejo11.architecture.college.teacher.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherDTO;
import io.github.alexistrejo11.architecture.college.teacher.mappers.TeacherMapper;
import io.github.alexistrejo11.architecture.college.teacher.model.Teacher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherMessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final TeacherMapper teacherMapper;

    public void sendTeacherCreation(Teacher teacher) {
        TeacherDTO teacherDTO = teacherMapper.entityToDTO(teacher);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TEACHER_EXCHANGE,
                RabbitMQConfig.TEACHER_CREATE_ROUTING_KEY,
                teacherDTO
        );
    }

    public void sendTeacherDeletion(String teacherAccountNumber) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TEACHER_EXCHANGE,
                RabbitMQConfig.TEACHER_DELETE_ROUTING_KEY,
                teacherAccountNumber
        );
    }

}
