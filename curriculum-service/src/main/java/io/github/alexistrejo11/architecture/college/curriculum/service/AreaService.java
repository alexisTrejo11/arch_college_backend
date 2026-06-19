package io.github.alexistrejo11.architecture.college.curriculum.service;

import io.github.alexistrejo11.architecture.college.common.dto.Area.AreaDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Area.AreaInsertDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Area.AreaWithRelationsDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AreaService {
    Result<AreaDTO> getAreaById(Long areaId);
    AreaWithRelationsDTO getAreaByIdWithSubjects(Long areaId, Pageable pageable);
    Result<AreaDTO> getAreaByName(String name);
    List<AreaDTO> getAllAreas();
    void createArea(AreaInsertDTO areaInsertDTO);
    void updateAreaName(AreaInsertDTO areaInsertDTO, Long areaId);
    void deleteArea(Long areaId);
}
