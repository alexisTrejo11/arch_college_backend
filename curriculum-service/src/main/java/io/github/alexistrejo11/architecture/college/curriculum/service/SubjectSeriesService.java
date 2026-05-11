package io.github.alexistrejo11.architecture.college.curriculum.service;

import io.github.alexistrejo11.architecture.college.common.dto.Subject.SubjectSeriesDTO;
import io.github.alexistrejo11.architecture.college.curriculum.service.dto.SubjectSeriesInsertDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectSeriesService {
    Result<Void> validateSubjectSeriesCreation(SubjectSeriesInsertDTO subjectSeriesInsertDTO);
    SubjectSeriesDTO createSubjectSeries(SubjectSeriesInsertDTO subjectSeriesInsertDTO);
    SubjectSeriesDTO getSubjectSeriesByObligatorySubjectId(Long obligatorySubjectId);
    SubjectSeriesDTO getSubjectSeriesByElectiveSubjectId(Long electiveSubjectId);
    Page<SubjectSeriesDTO> getAll(Pageable pageable);
    void deleteSubjectSeriesById(Long subjectSeriesId);
}
