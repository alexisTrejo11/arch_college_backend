package io.github.alexistrejo11.architecture.college.curriculum.service.implementation;

import io.github.alexistrejo11.architecture.college.curriculum.repository.AreaRepository;
import io.github.alexistrejo11.architecture.college.curriculum.repository.CareerRepository;
import io.github.alexistrejo11.architecture.college.curriculum.repository.ElectiveSubjectRepository;
import io.github.alexistrejo11.architecture.college.curriculum.repository.ProfessionalLineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.Area;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.Career;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.ProfessionalLine;
import io.github.alexistrejo11.architecture.college.curriculum.model.Subject.ElectiveSubject;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectInsertDTO;
import io.github.alexistrejo11.architecture.college.curriculum.mapper.ElectiveSubjectMapper;
import io.github.alexistrejo11.architecture.college.curriculum.service.SubjectService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectiveSubjectServiceImpl implements SubjectService<ElectiveSubjectDTO, ElectiveSubjectInsertDTO> {

    private final ElectiveSubjectRepository electiveSubjectRepository;
    private final ElectiveSubjectMapper electiveSubjectMapper;
    private final AreaRepository areaRepository;
    private final CareerRepository careerRepository;
    private final ProfessionalLineRepository professionalLineRepository;
    private final KeyGenerationService keyGenerationService;

    @Override
    @Cacheable(value = "electiveSubjectByIdCache", key = "#subjectId")
    public Optional<ElectiveSubjectDTO> getSubjectById(Long subjectId) {
        return electiveSubjectRepository.findById(subjectId)
                .map(electiveSubjectMapper::entityToDTO);
    }

    @Override
    @Cacheable(value = "electiveSubjectByNameCache", key = "#name")
    public Optional<ElectiveSubjectDTO> getSubjectByName(String name) {
        return electiveSubjectRepository.findByName(name)
                .map(electiveSubjectMapper::entityToDTO);
    }

    @Override
    @Cacheable(value = "electiveSubjectsByFilterCache", key = "#filterId + '_' + #filterType")
    public Page<ElectiveSubjectDTO> getSubjectsByFilterPageable(Long filterId, String filterType, Pageable pageable) {
        return switch (filterType.toLowerCase()) {
            case "professionalline" -> electiveSubjectRepository.findByProfessionalLine(filterId, pageable)
                    .map(electiveSubjectMapper::entityToDTO);
            case "area" -> electiveSubjectRepository.findByAreaId(filterId, pageable)
                    .map(electiveSubjectMapper::entityToDTO);
            case "career" -> electiveSubjectRepository.findByCareerId(filterId, pageable)
                    .map(electiveSubjectMapper::entityToDTO);
            default -> throw new IllegalArgumentException("Invalid filter type: " + filterType);
        };
    }

    @Override
    @Cacheable(value = "allElectiveSubjectsCache")
    public Page<ElectiveSubjectDTO> getAllSubjectsPageable(Pageable pageable) {
        return electiveSubjectRepository.findAll(pageable)
                .map(electiveSubjectMapper::entityToDTO);
    }

    @Override
    public List<ElectiveSubjectDTO> getSubjectsByFilter(Long filterId, String filterType) {
        if (!"career".equalsIgnoreCase(filterType)) {
            throw new IllegalArgumentException("Invalid filter type: " + filterType);
        }
        return electiveSubjectRepository.findByCareerId(filterId).stream()
                .map(electiveSubjectMapper::entityToDTO)
                .toList();
    }

    @Override
    public List<ElectiveSubjectDTO> getSubjectByIdsIn(Set<Long> providedIds) {
        List<ElectiveSubject> foundSubjects = electiveSubjectRepository.findByIdIn(providedIds);

        Set<Long> foundIds = foundSubjects.stream()
                .map(ElectiveSubject::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = new HashSet<>(providedIds);
        missingIds.removeAll(foundIds);

        if (!missingIds.isEmpty()) {
            throw new EntityNotFoundException("Subjects not found for IDs: " + missingIds);
        }

        return foundSubjects.stream()
                .map(electiveSubjectMapper::entityToDTO)
                .toList();
    }

    @Override
    public void createSubject(ElectiveSubjectInsertDTO electiveSubjectInsertDTO) {
        ElectiveSubject electiveSubject = electiveSubjectMapper.insertDtoToEntity(electiveSubjectInsertDTO);
        handleElectiveSubjectRelationships(electiveSubject, electiveSubjectInsertDTO);

        // Save first to generate ID
        ElectiveSubject savedSubject = electiveSubjectRepository.saveAndFlush(electiveSubject);

        // Generate and set key
        String key = keyGenerationService.generateSubjectKey(savedSubject);
        savedSubject.setKey(key);

        electiveSubjectRepository.save(savedSubject);
    }

    @Override
    public void updateSubject(ElectiveSubjectInsertDTO electiveSubjectInsertDTO, Long subjectId) {
        if (!electiveSubjectRepository.existsById(subjectId)) {
            throw new EntityNotFoundException("Subject not found with ID: " + subjectId);
        }
        ElectiveSubject electiveSubject = electiveSubjectMapper.updateDtoToEntity(electiveSubjectInsertDTO, subjectId);
        handleElectiveSubjectRelationships(electiveSubject, electiveSubjectInsertDTO);
        electiveSubjectRepository.save(electiveSubject);
    }

    @Override
    public void deleteSubject(Long subjectId) {
        if (!electiveSubjectRepository.existsById(subjectId)) {
            throw new EntityNotFoundException("Subject not found with ID: " + subjectId);
        }
        electiveSubjectRepository.deleteById(subjectId);
    }

    private void handleElectiveSubjectRelationships(ElectiveSubject subject, ElectiveSubjectInsertDTO insertDTO) {
        Career career = careerRepository.findById(insertDTO.getCareerId())
                .orElseThrow(() -> new EntityNotFoundException("Career not found with ID: " + insertDTO.getCareerId()));
        subject.setCareer(career);

        if ("Architecture".equals(career.getName())) {
            setArchitectureSpecificRelationships(subject, insertDTO);
        }
    }

    private void setArchitectureSpecificRelationships(ElectiveSubject subject, ElectiveSubjectInsertDTO insertDTO) {
        Area area = areaRepository.findById(insertDTO.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Area not found with ID: " + insertDTO.getAreaId()));
        subject.setArea(area);

        ProfessionalLine professionalLine = professionalLineRepository.findById(insertDTO.getProfessionalLineId())
                .orElseThrow(() -> new EntityNotFoundException("Professional Line not found with ID: " + insertDTO.getProfessionalLineId()));
        subject.setProfessionalLine(professionalLine);
    }
}