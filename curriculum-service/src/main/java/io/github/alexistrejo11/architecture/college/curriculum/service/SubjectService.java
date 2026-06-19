package io.github.alexistrejo11.architecture.college.curriculum.service;

import io.github.alexistrejo11.architecture.college.common.dto.Subject.SubjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubjectService<T extends SubjectDTO, I extends SubjectDTO> {
    Optional<T> getSubjectById(Long id);
    Optional<T> getSubjectByName(String name);
    Page<T> getSubjectsByFilterPageable(Long filterId, String filterType, Pageable pageable);
    Page<T> getAllSubjectsPageable(Pageable pageable);
    List<T> getSubjectsByFilter(Long filterId, String filterType);
    List<T> getSubjectByIdsIn(Set<Long> providedIds);

    void createSubject(I subjectInsertDTO);
    void updateSubject(I subjectInsertDTO, Long subjectId);
    void deleteSubject(Long subjectId);
}