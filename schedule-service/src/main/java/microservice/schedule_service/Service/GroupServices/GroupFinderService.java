package microservice.schedule_service.Service.GroupServices;

import microservice.common_classes.DTOs.Group.GroupDTO;
import microservice.common_classes.Utils.Response.Result;
import microservice.schedule_service.Utils.GroupFinderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GroupFinderService {
    Optional<GroupDTO> getGroupById(Long groupId);
    List<GroupDTO> getGroupsByIds(List<Long> groupsId);
    Optional<GroupDTO> getCurrentGroupByKey(String key);
    Page<GroupDTO> findGroupsWithFilters(GroupFinderFilter groupFinderFilter, Pageable pageable);
    List<GroupDTO> getCurrentGroupByTeacherId(Long teacherId);
    Page<GroupDTO> getCurrentGroups(Pageable pageable);
    List<GroupDTO> getCurrentGroupsByClassroomPrefix(String buildingLetter);
}
