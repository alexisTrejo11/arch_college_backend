package io.github.alexistrejo11.architecture.college.curriculum.service;

import io.github.alexistrejo11.architecture.college.common.dto.ProfessionalLine.ProfessionalLineDTO;
import io.github.alexistrejo11.architecture.college.common.dto.ProfessionalLine.ProfessionalLineInsertDTO;

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
