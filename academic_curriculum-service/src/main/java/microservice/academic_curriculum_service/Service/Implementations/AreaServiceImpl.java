package microservice.academic_curriculum_service.Service.Implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import microservice.common_classes.DTOs.Area.AreaDTO;
import microservice.common_classes.DTOs.Area.AreaInsertDTO;
import microservice.common_classes.DTOs.Area.AreaWithRelationsDTO;
import microservice.common_classes.DTOs.Subject.ElectiveSubjectDTO;
import microservice.common_classes.DTOs.Subject.ElectiveSubjectInsertDTO;
import microservice.common_classes.DTOs.Subject.ObligatorySubjectDTO;
import microservice.common_classes.DTOs.Subject.ObligatorySubjectInsertDTO;
import microservice.common_classes.Utils.Response.Result;
import microservice.academic_curriculum_service.Mappers.AreaMapper;
import microservice.academic_curriculum_service.Model.Career.Area;

import microservice.academic_curriculum_service.Repository.AreaRepository;
import microservice.academic_curriculum_service.Service.AreaService;
import microservice.academic_curriculum_service.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "areaWithSubjectsCache", key = "#areaId")
    public AreaWithRelationsDTO getAreaByIdWithSubjects(Long areaId, Pageable pageable) {
        Area area = areaRepository.findByIdWithSubjects(areaId)
                .orElseThrow(() -> new EntityNotFoundException("Area with ID " + areaId + " not found"));

        return areaMapper.entityToDTOWithRelations(area);
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
