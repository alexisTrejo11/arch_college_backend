package io.github.alexistrejo11.architecture.college.curriculum.service.implementation;

import io.github.alexistrejo11.architecture.college.common.dto.Subject.*;
import org.springframework.cache.annotation.Cacheable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.curriculum.service.dto.SubjectSeriesInsertDTO;
import io.github.alexistrejo11.architecture.college.curriculum.mapper.SubjectSeriesMapper;
import io.github.alexistrejo11.architecture.college.curriculum.model.Subject.ElectiveSubject;
import io.github.alexistrejo11.architecture.college.curriculum.model.Subject.ObligatorySubject;
import io.github.alexistrejo11.architecture.college.curriculum.model.Subject.Subject;
import io.github.alexistrejo11.architecture.college.curriculum.model.Subject.SubjectSeries;
import io.github.alexistrejo11.architecture.college.curriculum.repository.ElectiveSubjectRepository;
import io.github.alexistrejo11.architecture.college.curriculum.repository.ObligatorySubjectRepository;
import io.github.alexistrejo11.architecture.college.curriculum.repository.SubjectSeriesRepository;
import io.github.alexistrejo11.architecture.college.curriculum.service.SubjectSeriesService;

import io.github.alexistrejo11.architecture.college.curriculum.service.SubjectService;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubjectSeriesServiceImpl implements SubjectSeriesService {

    private final ObligatorySubjectRepository obligatorySubjectRepository;
    private final SubjectSeriesRepository subjectSeriesRepository;
    private final SubjectService<ObligatorySubjectDTO, ObligatorySubjectInsertDTO> obligatorySubjectService;
    private final SubjectService<ElectiveSubjectDTO, ElectiveSubjectInsertDTO> electiveSubjectService;
    private final ElectiveSubjectRepository electiveSubjectRepository;
    private final SubjectSeriesMapper subjectSeriesMapper;

    @Override
    public Result<Void> validateSubjectSeriesCreation(SubjectSeriesInsertDTO subjectSeriesInsertDTO) {
        SubjectType subjectType = subjectSeriesInsertDTO.getSubjectType();
        Set<Long> providedIds = subjectSeriesInsertDTO.getSubjectsIds();

        if (subjectType == SubjectType.OBLIGATORY) {
            return validateSubjects(providedIds, obligatorySubjectService);
        } else if (subjectType == SubjectType.ELECTIVE) {
            return validateSubjects(providedIds, electiveSubjectService);
        } else {
            throw new IllegalArgumentException("Unsupported SubjectType: " + subjectType);
        }
    }

    private <T extends SubjectDTO> Result<Void> validateSubjects(
            Set<Long> subjectIds,
            SubjectService<T, ?> subjectService
    ) {
        List<T> subjectDTOS = subjectService.getSubjectByIdsIn(subjectIds);

        for (var subject : subjectDTOS) {
            if (subject.getSeriesId() != null) {
                return Result.error("Subject With Key " + subject.getKey() + " already has serialization");
            }
        }

        return Result.success();
    }

    @Override
    @Transactional
    public SubjectSeriesDTO createSubjectSeries(SubjectSeriesInsertDTO subjectSeriesInsertDTO) {
        SubjectSeries subjectSeries = subjectSeriesMapper.insertDtoToEntity(subjectSeriesInsertDTO);

        subjectSeriesRepository.saveAndFlush(subjectSeries);

        addSeriesToSubjects(subjectSeriesInsertDTO, subjectSeries);

        return subjectSeriesMapper.entityToDTO(subjectSeries);
    }

    @Override
    @Cacheable(value = "subjectSeriesByObligatorySubjectId", key = "#obligatorySubjectId")
    public SubjectSeriesDTO getSubjectSeriesByObligatorySubjectId(Long obligatorySubjectId) {
        ObligatorySubject obligatorySubject = obligatorySubjectRepository.findById(obligatorySubjectId)
                .orElseThrow(() -> new EntityNotFoundException("Obligatory Subject With Id " + obligatorySubjectId + " Not Found"));

        if (obligatorySubject.getSeries() == null) {
            return null;
        }

        SubjectSeries subjectSeries = obligatorySubject.getSeries();

        return subjectSeriesMapper.entityToDTO(subjectSeries);
    }

    @Override
    @Cacheable(value = "subjectSeriesByElectiveSubjectId", key = "#electiveSubjectId")
    public SubjectSeriesDTO getSubjectSeriesByElectiveSubjectId(Long electiveSubjectId) {
        ElectiveSubject electiveSubject = electiveSubjectRepository.findById(electiveSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("Elective Subject With Id " + electiveSubjectId + " Not Found"));

        if (electiveSubject.getSeries() == null) {
            return null;
        }

        SubjectSeries subjectSeries = electiveSubject.getSeries();

        return subjectSeriesMapper.entityToDTO(subjectSeries);
    }

    @Override
    @Cacheable(value = "allSubjectSeries", key = "#pageable")
    public Page<SubjectSeriesDTO> getAll(Pageable pageable) {
        Page<SubjectSeries> subjectSeriesPage = subjectSeriesRepository.findAll(pageable);
        return subjectSeriesPage.map(subjectSeriesMapper::entityToDTO);
    }

    @Override
    public void deleteSubjectSeriesById(Long subjectSeriesId) {
        if (!subjectSeriesRepository.existsById(subjectSeriesId)) {
            throw new EntityNotFoundException("Subject Series With Id "+ subjectSeriesId + " Not Found");
        }
        subjectSeriesRepository.deleteById(subjectSeriesId);
    }

    private void addSeriesToSubjects(SubjectSeriesInsertDTO subjectSeriesInsertDTO, SubjectSeries subjectSeries) {
        Set<Long> subjectsIds = subjectSeriesInsertDTO.getSubjectsIds();

        List<? extends Subject> subjectsToAdd = fetchSubjectsByType(subjectSeriesInsertDTO.getSubjectType(), subjectsIds);

        addSubjectsToSeries(subjectSeries, subjectsToAdd);
    }

    private void addSubjectsToSeries(SubjectSeries subjectSeries, List<? extends Subject> subjectsToAdd) {
        for (Subject subject : subjectsToAdd) {
            if (subject instanceof ObligatorySubject obligatorySubject) {
                subjectSeries.addObligatorySubject(obligatorySubject);
            } else if (subject instanceof ElectiveSubject electiveSubject) {
                subjectSeries.addElectiveSubject(electiveSubject);
            }
        }
        subjectSeriesRepository.saveAndFlush(subjectSeries);
    }

    private List<? extends Subject> fetchSubjectsByType(SubjectType subjectType, Set<Long> subjectsIds) {
        if (subjectType == SubjectType.OBLIGATORY) {
            return obligatorySubjectRepository.findByIdIn(subjectsIds);
        } else if (subjectType == SubjectType.ELECTIVE) {
            return electiveSubjectRepository.findByIdIn(subjectsIds);
        }
        throw new IllegalArgumentException("Unsupported SubjectType: " + subjectType);
    }
}
