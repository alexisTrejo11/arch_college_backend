package io.github.alexistrejo11.architecture.college.teacher.service.Implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Teacher.TeacherDTO;
import io.github.alexistrejo11.architecture.college.common.models.teacher.Title;
import io.github.alexistrejo11.architecture.college.teacher.mappers.TeacherMapper;
import io.github.alexistrejo11.architecture.college.teacher.model.Teacher;
import io.github.alexistrejo11.architecture.college.teacher.repository.TeacherRepository;
import io.github.alexistrejo11.architecture.college.teacher.service.TeacherQueryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherQueryServiceImpl implements TeacherQueryService {

    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;

    @Override
    @Cacheable(value = "teacherById", key = "#teacherId")
    public Optional<TeacherDTO> getTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .map(teacherMapper::entityToDTO);
    }

    @Override
    public List<TeacherDTO> getTeachersByIds(Set<Long> idSet) {
        List<Teacher> teachers = teacherRepository.findByIdIn(idSet);

        Set<Long> idsFounded = teachers.stream()
                .map(Teacher::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = idSet.stream()
                .filter(id -> !idsFounded.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new EntityNotFoundException("Teachers not found for IDs: " + missingIds);
        }

        return teachers.stream().map(teacherMapper::entityToDTO).toList();
    }

    @Override
    @Cacheable(value = "teacherByAccountNumber", key = "#accountNumber")
    public Optional<TeacherDTO> getTeacherByAccountNumber(String accountNumber) {
        return teacherRepository.findByAccountNumber(accountNumber)
                .map(teacherMapper::entityToDTO);
    }

    @Override
    @Cacheable(value = "teachersCache", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #sortDirection + '-' + #sortBy")
    public Page<TeacherDTO> getAllTeachersSorted(Pageable pageable, String sortDirection, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return teacherRepository.findAll(sortedPageable).map(teacherMapper::entityToDTO);
    }

    @Override
    @Cacheable(value = "teacherByTitle", key = "#title")
    public Page<TeacherDTO> getTeachersByTitlePageable(Title title, Pageable pageable) {
        return teacherRepository.findByTitle(title, pageable).map(teacherMapper::entityToDTO);
    }
}
