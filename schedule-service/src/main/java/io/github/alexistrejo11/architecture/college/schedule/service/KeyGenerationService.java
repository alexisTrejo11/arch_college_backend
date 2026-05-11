package io.github.alexistrejo11.architecture.college.schedule.service;

import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectDTO;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;
import io.github.alexistrejo11.architecture.college.schedule.models.Group;
import io.github.alexistrejo11.architecture.college.schedule.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KeyGenerationService {

    private static final String STATIC_NUMBER_ORDINARY_START_KEY = "5";
    private static final String STATIC_NUMBER_ELECTIVE_START_KEY = "6";

    private final GroupRepository groupRepository;

    @Autowired
    public KeyGenerationService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    // Obligatory Key example --> 5210
    // (5) means a generic number
    // (2) means the number of the semester that the semester that subject belongs
    // (10) means the number of the group from some subject
    public String generate(Group group, ObligatorySubjectDTO ordinarySubjectDTO) {
        Long subjectId = ordinarySubjectDTO.getId();

        List<Group> ordinaryGroups = groupRepository.findBySubjectIdAndSubjectTypeAndSchoolPeriod(
                subjectId,
                SubjectType.OBLIGATORY,
                AcademicData.getCurrentSchoolPeriod());

        int semesterNumber = ordinarySubjectDTO.getSemester();
        int subjectGroupNumber = ordinaryGroups.size() + 1;

        return String.format("%s%d%02d", STATIC_NUMBER_ORDINARY_START_KEY, semesterNumber, subjectGroupNumber);
    }

    // Obligatory Key example --> 6102
    // (6) means a generic number
    // (10) means the ID of the elective subject
    // (9) means the number of the group from the elective subject
    public String generate(Group group, ElectiveSubjectDTO electiveSubjectDTO) {
        Long subjectId = electiveSubjectDTO.getId();

        List<Group> electiveGroups = groupRepository.findBySubjectIdAndSubjectTypeAndSchoolPeriod(
                subjectId,
                SubjectType.ELECTIVE,
                AcademicData.getCurrentSchoolPeriod());

        int subjectGroupNumber = electiveGroups.size() + 1;

        return String.format("%s%02d%d", STATIC_NUMBER_ELECTIVE_START_KEY, electiveSubjectDTO.getId(), subjectGroupNumber);
    }
}
