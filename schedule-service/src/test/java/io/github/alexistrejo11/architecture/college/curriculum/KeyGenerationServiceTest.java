package io.github.alexistrejo11.architecture.college.curriculum;

import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectDTO;
import io.github.alexistrejo11.architecture.college.common.models.schedule.AcademicData;
import io.github.alexistrejo11.architecture.college.common.models.subject.SubjectType;
import io.github.alexistrejo11.architecture.college.schedule.models.Group;
import io.github.alexistrejo11.architecture.college.schedule.repository.GroupRepository;
import io.github.alexistrejo11.architecture.college.schedule.service.KeyGenerationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class KeyGenerationServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private KeyGenerationService keyGenerationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateObligatoryKey() {
        Group group1 = new Group();
        Group group2 = new Group();
        Group group3 = new Group();
        List<Group> groups = Arrays.asList(group1, group2, group3); // First 3 Groups

        ObligatorySubjectDTO obligatorySubjectDTO = new ObligatorySubjectDTO();
        obligatorySubjectDTO.setId(1L);
        obligatorySubjectDTO.setSemester(2);

        when(groupRepository.findBySubjectIdAndSubjectTypeAndSchoolPeriod(
                1L, SubjectType.OBLIGATORY, AcademicData.getCurrentSchoolPeriod())).thenReturn(groups);

        String key = keyGenerationService.generate(new Group(), obligatorySubjectDTO);

        assertEquals("5204", key); // Fourth Group
    }


    @Test
    public void testGenerateElectiveKey() {
        Group group1 = new Group();
        List<Group> groups = List.of(group1);

        ElectiveSubjectDTO electiveSubjectDTO = new ElectiveSubjectDTO();
        electiveSubjectDTO.setId(10L);

        when(groupRepository.findBySubjectIdAndSubjectTypeAndSchoolPeriod(
                10L, SubjectType.ELECTIVE, AcademicData.getCurrentSchoolPeriod())).thenReturn(groups);

        String key = keyGenerationService.generate(new Group(), electiveSubjectDTO);

        assertEquals("6102", key);
    }
}

