package io.github.alexistrejo11.architecture.college.curriculum.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Area.AreaDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Area.AreaInsertDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Area.AreaWithRelationsDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectInsertDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectInsertDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.curriculum.mapper.AreaMapper;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.Area;

import io.github.alexistrejo11.architecture.college.curriculum.repository.AreaRepository;
import io.github.alexistrejo11.architecture.college.curriculum.service.AreaService;
import io.github.alexistrejo11.architecture.college.curriculum.service.SubjectService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;
    private final SubjectService<ObligatorySubjectDTO, ObligatorySubjectInsertDTO> ordinarySubjectService;
    private final SubjectService<ElectiveSubjectDTO, ElectiveSubjectInsertDTO> electiveSubjectService;

    
    @Override
    @Cacheable(value = "areaByIdCache", key = "#areaId")
    public Result<AreaDTO> getAreaById(Long areaId) {
        Optional<Area> optionalArea = areaRepository.findById(areaId);
        return optionalArea.map(area -> Result.success(areaMapper.entityToDTO(area)))
                .orElseGet(() -> Result.error("Area with ID " + areaId + " not found"));
    }

    @Override
    @Cacheable(value = "areaWithSubjectsCache", key = "#areaId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public AreaWithRelationsDTO getAreaByIdWithSubjects(Long areaId, Pageable pageable) {
        Area area = areaRepository.findByIdWithSubjects(areaId)
                .orElseThrow(() -> new EntityNotFoundException("Area with ID " + areaId + " not found"));

        AreaWithRelationsDTO dto = areaMapper.entityToDTOWithRelations(area);
        Page<ObligatorySubjectDTO> ordinaryPage =
                ordinarySubjectService.getSubjectsByFilterPageable(areaId, "area", pageable);
        Page<ElectiveSubjectDTO> electivePage =
                electiveSubjectService.getSubjectsByFilterPageable(areaId, "area", pageable);
        dto.setRelationships(ordinaryPage, electivePage);
        return dto;
    }

    @Override
    @Cacheable(value = "areaByNameCache", key = "#name")
    public Result<AreaDTO> getAreaByName(String name) {
        Optional<Area> optionalArea = areaRepository.findByName(name);
        return optionalArea.map(area -> Result.success(areaMapper.entityToDTO(area)))
                .orElseGet(() -> Result.error("Area with name " + name + " not found"));
    }

    @Override
    @Cacheable(value = "allAreasCache")
    public List<AreaDTO> getAllAreas() {
        List<Area> areas = areaRepository.findAll();
        return areas.stream().map(areaMapper::entityToDTO).toList();
    }

    @Override
    @Transactional
    public void createArea(AreaInsertDTO areaInsertDTO) {
        Area area = areaMapper.insertDtoToEntity(areaInsertDTO);
        areaRepository.save(area);
    }

    @Override
    @Transactional
    public void updateAreaName(AreaInsertDTO areaInsertDTO, Long areaId) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new EntityNotFoundException("Area with ID " + areaId + " not found"));

        area.updateName(areaInsertDTO.getName());
        areaRepository.save(area);
    }

    @Override
    @Transactional
    public void deleteArea(Long areaId) {
       if (!areaRepository.existsById(areaId)) {
           throw new EntityNotFoundException("Area with ID " + areaId + " not found");
       }
       areaRepository.deleteById(areaId);
    }
}
