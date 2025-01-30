package microservice.schedule_service.Service.GroupServices.Implementation;

import lombok.RequiredArgsConstructor;
import microservice.common_classes.DTOs.Group.GroupDTO;
import microservice.common_classes.Utils.Schedule.AcademicData;
import microservice.schedule_service.Mapppers.GroupMapper;
import microservice.schedule_service.Models.Group;
import microservice.schedule_service.Repository.GroupRepository;
import microservice.schedule_service.Service.GroupServices.GroupFinderService;
import microservice.schedule_service.Utils.GroupFinderFilter;
import microservice.schedule_service.Utils.GroupSpecification;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupFinderServiceImpl implements GroupFinderService {

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
