package microservice.academic_curriculum_service.Service.Implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import microservice.common_classes.DTOs.ProfessionalLine.ProfessionalLineDTO;
import microservice.common_classes.DTOs.ProfessionalLine.ProfessionalLineInsertDTO;
import microservice.academic_curriculum_service.Mappers.ProfessionalLineMapper;
import microservice.academic_curriculum_service.Model.Career.Area;
import microservice.academic_curriculum_service.Model.Career.ProfessionalLine;
import microservice.academic_curriculum_service.Repository.AreaRepository;
import microservice.academic_curriculum_service.Repository.ProfessionalLineRepository;
import microservice.academic_curriculum_service.Service.ProfessionalLineService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessionalLineServiceImpl implements ProfessionalLineService {
    private final ProfessionalLineMapper professionalLineMapper;
    private final AreaRepository areaRepository;
    private final ProfessionalLineRepository professionalLineRepository;

    @Override
    @Cacheable(value = "professionalLineByIdCache", key = "#professionalLineId")
    public Optional<ProfessionalLineDTO> getProfessionalLineById(Long professionalLineId) {
        return professionalLineRepository.findById(professionalLineId)
                .map(professionalLineMapper::entityToDTO);
    }

    @Override
    @Cacheable(value = "professionalLineWithSubjectsCache", key = "#professionalLineId")
    public Optional<ProfessionalLineDTO> getProfessionalLineByIdWithSubjects(Long professionalLineId) {
        return getProfessionalLineById(professionalLineId);
    }

    @Override
    @Cacheable(value = "professionalLineByNameCache", key = "#name")
    public Optional<ProfessionalLineDTO> getProfessionalLineByName(String name) {
        return professionalLineRepository.findByName(name)
                .map(professionalLineMapper::entityToDTO);
    }

    @Override
    @Cacheable(value = "allProfessionalLinesCache")
    public List<ProfessionalLineDTO> getAllProfessionalLines() {
        return professionalLineRepository.findAll()
                .stream()
                .map(professionalLineMapper::entityToDTO)
                .toList();
    }

    @Override
    @Transactional
    public void createProfessionalLine(ProfessionalLineInsertDTO professionalLineInsertDTO) {
        ProfessionalLine professionalLine = professionalLineMapper.insertDtoToEntity(professionalLineInsertDTO);
        professionalLine.setArea(getArea(professionalLineInsertDTO.getAreaId()));
        professionalLineRepository.save(professionalLine);
    }

    @Override
    @Transactional
    public void updateProfessionalLineName(ProfessionalLineInsertDTO professionalLineInsertDTO, Long professionalLineId) {
        ProfessionalLine professionalLine = professionalLineRepository.findById(professionalLineId)
                .orElseThrow(() -> new EntityNotFoundException("Professional Line with ID " + professionalLineId + " not found"));
        professionalLine.updateName(professionalLineInsertDTO.getName());
        professionalLineRepository.save(professionalLine);
    }

    @Override
    @Transactional
    public void deleteProfessionalLine(Long professionalLineId) {
        if (!professionalLineRepository.existsById(professionalLineId)) {
            throw new EntityNotFoundException("Professional Line with ID " + professionalLineId + " not found");
        }
        professionalLineRepository.deleteById(professionalLineId);
    }

    private Area getArea(Long areaId) {
        return areaRepository.findById(areaId)
                .orElseThrow(() -> new EntityNotFoundException("Area with ID " + areaId + " not found"));
    }
}
