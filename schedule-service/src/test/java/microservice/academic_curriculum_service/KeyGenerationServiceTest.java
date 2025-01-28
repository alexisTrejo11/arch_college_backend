package microservice.academic_curriculum_service;

import microservice.common_classes.DTOs.Subject.ElectiveSubjectDTO;
import microservice.common_classes.DTOs.Subject.ObligatorySubjectDTO;
import microservice.common_classes.Utils.Schedule.AcademicData;
import microservice.common_classes.Utils.SubjectType;
import microservice.schedule_service.Models.Group;
import microservice.schedule_service.Repository.GroupRepository;
import microservice.schedule_service.Service.KeyGenerationService;
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

