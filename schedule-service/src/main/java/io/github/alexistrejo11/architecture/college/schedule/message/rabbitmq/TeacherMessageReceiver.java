package io.github.alexistrejo11.architecture.college.schedule.message.rabbitmq;


import io.github.alexistrejo11.architecture.college.schedule.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherDTO;
import io.github.alexistrejo11.architecture.college.schedule.service.TeacherService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherMessageReceiver {

    private final TeacherService teacherService;

    @RabbitListener(queues = RabbitMQConfig.TEACHER_QUEUE)
    public void receiveTeacher(Object message) {
        if (message instanceof TeacherDTO teacherDTO) {
            teacherService.createTeacher(teacherDTO);
        } else if (message instanceof String teacherAccountNumber) {
            teacherService.deleteTeacher(teacherAccountNumber);
        }
    }
}

