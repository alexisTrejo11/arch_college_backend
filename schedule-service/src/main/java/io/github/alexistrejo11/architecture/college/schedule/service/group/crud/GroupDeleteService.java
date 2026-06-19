package io.github.alexistrejo11.architecture.college.schedule.service.group.crud;

import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.schedule.mappper.GroupMapper;
import io.github.alexistrejo11.architecture.college.schedule.models.Teacher;
import io.github.alexistrejo11.architecture.college.schedule.repository.GroupRepository;
import io.github.alexistrejo11.architecture.college.schedule.models.Group;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupDeleteService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final String currentSemester = AcademicData.getCurrentSchoolPeriod();

    public GroupDTO deleteTeacher(String groupKey, Long teacherId) {
        Group group = groupRepository.findByGroupKeyAndSchoolPeriod(groupKey, currentSemester)
                .orElseThrow(() -> new EntityNotFoundException("Group with Key " + groupKey + " not found"));

        Optional<Teacher> optionalTeacher = group.getTeachers().stream().filter(teacher -> teacher.getTeacherId().equals(teacherId)).findAny();
        if (optionalTeacher.isEmpty()) {
            throw new EntityNotFoundException("Teacher Not Found in group");
        }

        group.removeTeacher(optionalTeacher.get());

        groupRepository.saveAndFlush(group);
        log.info("Teacher with ID {} removed from group with key {}", teacherId, groupKey);
        log.info("Group with key {} updated ", groupKey);

        return groupMapper.entityToDTO(group);
    }

    public void deleteCurrentGroupByKey(String key) {
        Optional<Group> optionalGroup = groupRepository.findByGroupKeyAndSchoolPeriod(key, currentSemester);
        if (optionalGroup.isEmpty()) {
            throw new EntityNotFoundException("Group with Key " + key + " not found");
        }

        groupRepository.delete(optionalGroup.get());
        log.info("Group with key {} deleted from the database", key);
    }
}
