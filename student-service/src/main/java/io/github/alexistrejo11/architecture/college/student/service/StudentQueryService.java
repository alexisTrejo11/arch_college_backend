package io.github.alexistrejo11.architecture.college.student.service;

import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.common.utils.student.StudentFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentQueryService {
    Result<StudentDTO> getStudentById(Long studentId);
    Result<StudentDTO> getStudentByAccountNumber(String accountNumber);

    Page<StudentDTO> getAllStudentsSortedByFilterPageable(Pageable pageable, StudentFilter filter);
    Page<StudentDTO> getStudentsSortedByFilterPageable(Pageable pageable, StudentFilter filter, String param);
}
