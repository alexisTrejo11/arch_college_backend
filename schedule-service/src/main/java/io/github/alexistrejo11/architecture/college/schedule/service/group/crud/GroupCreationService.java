package io.github.alexistrejo11.architecture.college.schedule.service.group.crud;

import io.github.alexistrejo11.architecture.college.schedule.service.group.GroupRelationshipService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Group.ElectiveGroupInsertDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Group.ObligatoryGroupInsertDTO;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.schedule.mappper.GroupMapper;
import io.github.alexistrejo11.architecture.college.schedule.models.Group;
import io.github.alexistrejo11.architecture.college.schedule.service.dto.GroupRelationshipsDTO;
import io.github.alexistrejo11.architecture.college.schedule.repository.GroupRepository;
import io.github.alexistrejo11.architecture.college.schedule.service.KeyGenerationService;
import io.github.alexistrejo11.architecture.college.schedule.service.ScheduleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupCreationService {

    private final GroupMapper groupMapper;
    private final ScheduleService scheduleService;
    private final KeyGenerationService keyGenerationService;
    private final GroupRelationshipService relationshipService;
    private final GroupRepository groupRepository;
    private final String currentSemester = AcademicData.getCurrentSchoolPeriod();

    @Transactional
    public GroupDTO createGroup(ObligatoryGroupInsertDTO ObligatoryGroupInsertDTO, GroupRelationshipsDTO groupRelationshipsDTO) {
        Group group = groupMapper.insertDtoToEntity(ObligatoryGroupInsertDTO);

        group.setSchoolPeriod(currentSemester);
        group.initSpots(ObligatoryGroupInsertDTO.getTotalSpots());
        group.setSchedule(scheduleService.mapScheduleDTOToEntity(ObligatoryGroupInsertDTO.getSchedule()));

        group.setGroupKey(keyGenerationService.generate(group, groupRelationshipsDTO.getObligatorySubjectDTO()));

        relationshipService.setGroupRelationships(group, groupRelationshipsDTO);

        group.setHeadTeacherFromTeachers(ObligatoryGroupInsertDTO.getHeadTeacherId());

        groupRepository.saveAndFlush(group);
        log.info("createGroup -> Group saved successfully with ID: {}", group.getId());

        return groupMapper.entityToDTO(group);
    }

    @Transactional
    public GroupDTO createGroup(ElectiveGroupInsertDTO electiveGroupInsertDTO, GroupRelationshipsDTO groupRelationshipsDTO) {
        Group group = groupMapper.insertDtoToEntity(electiveGroupInsertDTO);
        log.debug("Mapped group entity: {}", group);

        group.setSchedule(scheduleService.mapScheduleDTOToEntity(electiveGroupInsertDTO.getSchedule()));
        group.setSchoolPeriod(currentSemester);

        if (groupRelationshipsDTO.getObligatorySubjectDTO() != null) {
            group.setGroupKey(keyGenerationService.generate(group, groupRelationshipsDTO.getObligatorySubjectDTO()));
        }

        if (groupRelationshipsDTO.getElectiveSubjectDTO() != null) {
            group.setGroupKey(keyGenerationService.generate(group, groupRelationshipsDTO.getElectiveSubjectDTO()));
        }

        relationshipService.setGroupRelationships(group, groupRelationshipsDTO);

        groupRepository.saveAndFlush(group);
        log.info("Group created with ID: {}", group.getId());

        return groupMapper.entityToDTO(group);
    }
}
