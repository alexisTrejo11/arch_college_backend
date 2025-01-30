package microservice.academic_curriculum_service.Service;

import microservice.common_classes.DTOs.ProfessionalLine.ProfessionalLineDTO;
import microservice.common_classes.DTOs.ProfessionalLine.ProfessionalLineInsertDTO;
import microservice.common_classes.Utils.Response.Result;

import java.util.List;
import java.util.Optional;

public interface ProfessionalLineService {
    Optional<ProfessionalLineDTO> getProfessionalLineById(Long professionalLineId);
    Optional<ProfessionalLineDTO> getProfessionalLineByIdWithSubjects(Long professionalLineId);
    Optional<ProfessionalLineDTO> getProfessionalLineByName(String name);
    List<ProfessionalLineDTO> getAllProfessionalLines();
    void createProfessionalLine(ProfessionalLineInsertDTO professionalLineInsertDTO);
    void updateProfessionalLineName(ProfessionalLineInsertDTO professionalLineInsertDTO, Long professionalLineId);
    void deleteProfessionalLine(Long professionalLineId);
}
