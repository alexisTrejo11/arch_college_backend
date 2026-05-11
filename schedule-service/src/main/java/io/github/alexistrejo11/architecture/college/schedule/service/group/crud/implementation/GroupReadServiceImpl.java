package io.github.alexistrejo11.architecture.college.schedule.service.group.crud.implementation;

import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.schedule.mappper.GroupMapper;
import io.github.alexistrejo11.architecture.college.schedule.models.Group;
import io.github.alexistrejo11.architecture.college.schedule.repository.GroupRepository;
import io.github.alexistrejo11.architecture.college.schedule.service.group.crud.GroupReadService;
import io.github.alexistrejo11.architecture.college.schedule.service.dto.GroupFinderFilter;
import io.github.alexistrejo11.architecture.college.schedule.repository.specification.GroupSpecification;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupReadServiceImpl implements GroupReadService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Cacheable(value = "groupById", key = "#groupId")
    public Optional<GroupDTO> getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .map(groupMapper::entityToDTO);
    }

    @Cacheable(value = "groupsByIds", key = "#groupsId")
    public List<GroupDTO> getGroupsByIds(List<Long> groupsId) {
        List<Group> groups = groupRepository.findByIdIn(groupsId);
        return groups.stream()
                .map(groupMapper::entityToDTO)
                .toList();
    }

    @Cacheable(value = "groupCurrentByKey", key = "#key")
    public Optional<GroupDTO> getCurrentGroupByKey(String key) {
        return groupRepository.findByGroupKeyAndSchoolPeriod(key, AcademicData.getCurrentSchoolPeriod())
                .map(groupMapper::entityToDTO);
    }

    @Cacheable(value = "groupsWithFilters", key = "#groupFinderFilter")
    public Page<GroupDTO> findGroupsWithFilters(GroupFinderFilter groupFinderFilter, Pageable pageable) {
        Specification<Group> specification = GroupSpecification.withFilters(groupFinderFilter);
        return groupRepository.findAll(specification, pageable)
                .map(groupMapper::entityToDTO);
    }

    @Cacheable(value = "groupByTeacherId", key = "#teacherId")
    public List<GroupDTO> getCurrentGroupByTeacherId(Long teacherId) {
        return groupRepository.findByTeacherIdAndSchoolPeriod(teacherId, AcademicData.getCurrentSchoolPeriod())
                .stream()
                .map(groupMapper::entityToDTO)
                .toList();
    }

    @Cacheable(value = "currentGroups", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<GroupDTO> getCurrentGroups(Pageable pageable) {
        return groupRepository.findBySchoolPeriod(AcademicData.getCurrentSchoolPeriod(), pageable)
                .map(groupMapper::entityToDTO);
    }

    @Cacheable(value = "currentGroupsByClassroomPrefix", key = "#buildingLetter")
    public List<GroupDTO> getCurrentGroupsByClassroomPrefix(String buildingLetter) {
        return groupRepository.findByClassroomPrefix(buildingLetter, AcademicData.getCurrentSchoolPeriod())
                .stream()
                .map(groupMapper::entityToDTO)
                .toList();
    }
}
