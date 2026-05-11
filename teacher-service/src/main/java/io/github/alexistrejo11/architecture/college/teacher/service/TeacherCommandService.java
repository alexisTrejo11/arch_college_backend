package io.github.alexistrejo11.architecture.college.teacher.service;

import io.github.alexistrejo11.architecture.college.teacher.service.dto.TeacherInsertDTO;

public interface TeacherCommandService {
    void createTeacher(TeacherInsertDTO teacherInsertDTO);
    void deleteTeacher(Long studentId);

    boolean validateExistingTeacher(String teacherAccountNumber);
}
