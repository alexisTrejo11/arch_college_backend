package io.github.alexistrejo11.architecture.college.common.service.teacher;

import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface TeacherFacadeService {
    CompletableFuture<Boolean> validateExisitingTeacher(String accountNumber);
    CompletableFuture<TeacherDTO> getTeacherById(Long teacherId);
    CompletableFuture<Result<List<TeacherDTO>>> getTeachersById(Set<Long> teacherIdSet);
    CompletableFuture<TeacherDTO> getTeacherByAccountNumber(String accountNumber);
}
