package microservice.teacher_service.Service;

import microservice.common_classes.DTOs.Teacher.TeacherDTO;
import microservice.common_classes.Utils.Response.Result;
import microservice.common_classes.Utils.Teacher.Title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeacherFinderService {
    Optional<TeacherDTO> getTeacherById(Long studentId);
    Optional<TeacherDTO> getTeacherByAccountNumber(String accountNumber);

    Page<TeacherDTO> getAllTeachersSorted(Pageable pageable, String sortDirection, String sortBy);
    List<TeacherDTO> getTeachersByIds(Set<Long> IdSet);
    Page<TeacherDTO> getTeachersByTitlePageable(Title title, Pageable pageable);
}
