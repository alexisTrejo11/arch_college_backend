package io.github.alexistrejo11.architecture.college.curriculum.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.curriculum.model.Subject.ObligatorySubject;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectInsertDTO;
import io.github.alexistrejo11.architecture.college.curriculum.mapper.ObligatorySubjectMapper;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.Area;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.Career;
import io.github.alexistrejo11.architecture.college.curriculum.repository.AreaRepository;
import io.github.alexistrejo11.architecture.college.curriculum.repository.CareerRepository;
import io.github.alexistrejo11.architecture.college.curriculum.repository.ObligatorySubjectRepository;
import io.github.alexistrejo11.architecture.college.curriculum.service.SubjectService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObligatoryServiceImpl implements SubjectService<ObligatorySubjectDTO, ObligatorySubjectInsertDTO> {

    private final ObligatorySubjectRepository obligatorySubjectRepository;
    private final ObligatorySubjectMapper obligatorySubjectMapper;
    private final CareerRepository careerRepository;
    private final AreaRepository areaRepository;
    private final KeyGenerationService keyGenerationService;

    @Override
    @Cacheable(value = "obligatorySubjectByIdCache", key = "#subjectId")
    public Optional<ObligatorySubjectDTO> getSubjectById(Long subjectId) {
        return obligatorySubjectRepository.findById(subjectId)
                .map(obligatorySubjectMapper::entityToDTO);
    }

    @Override
    @Cacheable(value = "obligatorySubjectByNameCache", key = "#name")
    public Optional<ObligatorySubjectDTO> getSubjectByName(String name) {
        return obligatorySubjectRepository.findByName(name)
                .map(obligatorySubjectMapper::entityToDTO);
    }

    @Override
    public List<ObligatorySubjectDTO> getSubjectByIdsIn(Set<Long> providedIds) {
        List<ObligatorySubject> foundSubjects = obligatorySubjectRepository.findByIdIn(providedIds);

        Set<Long> foundIds = foundSubjects.stream()
                .map(ObligatorySubject::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = new HashSet<>(providedIds);
        missingIds.removeAll(foundIds);

        if (!missingIds.isEmpty()) {
            throw new EntityNotFoundException("Subjects not found for IDs: " + missingIds);
        }

        return foundSubjects.stream()
                .map(obligatorySubjectMapper::entityToDTO)
                .toList();
    }

    @Override
    @Cacheable(value = "obligatorySubjectsByFilterCache", key = "#filterId + '_' + #filterType")
    public Page<ObligatorySubjectDTO> getSubjectsByFilterPageable(Long filterId, String filterType, Pageable pageable) {
        return switch (filterType.toLowerCase()) {
            case "semester" -> obligatorySubjectRepository.findBySemester(filterId.intValue(), pageable)
                    .map(obligatorySubjectMapper::entityToDTO);
            case "area" -> obligatorySubjectRepository.findByAreaId(filterId, pageable)
                    .map(obligatorySubjectMapper::entityToDTO);
            case "career" -> obligatorySubjectRepository.findByCareerId(filterId, pageable)
                    .map(obligatorySubjectMapper::entityToDTO);
            default -> throw new IllegalArgumentException("Invalid filter type: " + filterType);
        };
    }

    @Override
    @Cacheable(value = "allObligatorySubjectsCache")
    public Page<ObligatorySubjectDTO> getAllSubjectsPageable(Pageable pageable) {
        return obligatorySubjectRepository.findAll(pageable)
                .map(obligatorySubjectMapper::entityToDTO);
    }

    @Override
    public List<ObligatorySubjectDTO> getSubjectsByFilter(Long filterId, String filterType) {
        if (!"career".equalsIgnoreCase(filterType)) {
            throw new IllegalArgumentException("Invalid filter type: " + filterType);
        }
        return obligatorySubjectRepository.findByCareerId(filterId).stream()
                .map(obligatorySubjectMapper::entityToDTO)
                .toList();
    }

    @Override
    @Transactional
    public void createSubject(ObligatorySubjectInsertDTO obligatorySubjectInsertDTO) {
        ObligatorySubject obligatorySubject = obligatorySubjectMapper.insertDtoToEntity(obligatorySubjectInsertDTO);
        handleObligatorySubjectRelationships(obligatorySubject, obligatorySubjectInsertDTO);

        // Save first to generate ID
        ObligatorySubject savedSubject = obligatorySubjectRepository.saveAndFlush(obligatorySubject);

        // Generate and set key
        String key = keyGenerationService.generateSubjectKey(savedSubject);
        savedSubject.setKey(key);

        obligatorySubjectRepository.save(savedSubject);
    }

    @Override
    @Transactional
    public void updateSubject(ObligatorySubjectInsertDTO obligatorySubjectInsertDTO, Long subjectId) {
        if (!obligatorySubjectRepository.existsById(subjectId)) {
            throw new EntityNotFoundException("Subject not found with ID: " + subjectId);
        }
        ObligatorySubject obligatorySubject = obligatorySubjectMapper.updateDtoToEntity(obligatorySubjectInsertDTO, subjectId);
        handleObligatorySubjectRelationships(obligatorySubject, obligatorySubjectInsertDTO);
        obligatorySubjectRepository.save(obligatorySubject);
    }

    @Override
    @Transactional
    public void deleteSubject(Long subjectId) {
        if (!obligatorySubjectRepository.existsById(subjectId)) {
            throw new EntityNotFoundException("Subject not found with ID: " + subjectId);
        }
        obligatorySubjectRepository.deleteById(subjectId);
    }

    private void handleObligatorySubjectRelationships(ObligatorySubject subject, ObligatorySubjectInsertDTO insertDTO) {
        Career career = careerRepository.findById(insertDTO.getCareerId())
                .orElseThrow(() -> new EntityNotFoundException("Career not found with ID: " + insertDTO.getCareerId()));
        subject.setCareer(career);

        Area area = areaRepository.findById(insertDTO.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Area not found with ID: " + insertDTO.getAreaId()));
        subject.setArea(area);
    }
}