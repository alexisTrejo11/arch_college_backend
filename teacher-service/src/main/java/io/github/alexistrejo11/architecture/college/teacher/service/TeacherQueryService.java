package io.github.alexistrejo11.architecture.college.teacher.service;

import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherDTO;
import io.github.alexistrejo11.architecture.college.common.models.teacher.Title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeacherQueryService {
    Optional<TeacherDTO> getTeacherById(Long studentId);
    Optional<TeacherDTO> getTeacherByAccountNumber(String accountNumber);

    Page<TeacherDTO> getAllTeachersSorted(Pageable pageable, String sortDirection, String sortBy);
    List<TeacherDTO> getTeachersByIds(Set<Long> IdSet);
    Page<TeacherDTO> getTeachersByTitlePageable(Title title, Pageable pageable);
}
