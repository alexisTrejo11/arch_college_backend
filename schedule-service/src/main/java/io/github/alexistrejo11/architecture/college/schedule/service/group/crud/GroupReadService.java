package io.github.alexistrejo11.architecture.college.schedule.service.group.crud;

import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.schedule.service.dto.GroupFinderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GroupReadService {
    Optional<GroupDTO> getGroupById(Long groupId);
    List<GroupDTO> getGroupsByIds(List<Long> groupsId);
    Optional<GroupDTO> getCurrentGroupByKey(String key);
    Page<GroupDTO> findGroupsWithFilters(GroupFinderFilter groupFinderFilter, Pageable pageable);
    List<GroupDTO> getCurrentGroupByTeacherId(Long teacherId);
    Page<GroupDTO> getCurrentGroups(Pageable pageable);
    List<GroupDTO> getCurrentGroupsByClassroomPrefix(String buildingLetter);
}
