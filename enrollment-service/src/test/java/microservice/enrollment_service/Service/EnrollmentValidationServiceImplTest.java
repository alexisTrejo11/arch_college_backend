package microservice.enrollment_service.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import microservice.common_classes.DTOs.Enrollment.EnrollmentInsertDTO;
import microservice.common_classes.Utils.ProfessionalLineModality;
import microservice.common_classes.Utils.Response.Result;
import microservice.common_classes.Utils.SubjectType;
import microservice.enrollment_service.DTOs.EnrollmentRelationship;
import microservice.enrollment_service.Model.Enrollment;
import microservice.enrollment_service.Model.Preload.ElectiveSubject;
import microservice.enrollment_service.Model.Preload.Grade;
import microservice.enrollment_service.Model.Preload.ObligatorySubject;
import microservice.enrollment_service.Model.Preload.Student;
import microservice.enrollment_service.Repository.EnrollmentRepository;
import microservice.enrollment_service.Service.Implementation.EnrollmentValidationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class EnrollmentValidationServiceImplTest {

    private EnrollmentValidationServiceImpl enrollmentValidationService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private EnrollmentInsertDTO enrollmentInsertDTO;

    @Mock
    private EnrollmentRelationship enrollmentRelationship;

    @Mock
    private Student student;

    @Mock
    private Enrollment enrollmentMock;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        enrollmentValidationService = new EnrollmentValidationServiceImpl(enrollmentRepository);
    }

        private ObligatorySubject createObligatorySubject(long id) {
        ObligatorySubject subject = new ObligatorySubject();
        subject.setId(id);
        return subject;
    }

    private Enrollment createEnrollment(String groupKey, String subjectKey) {
        Enrollment enrollment = mock(Enrollment.class);
        when(enrollment.getGroupKey()).thenReturn(groupKey);
        when(enrollment.getSubjectKey()).thenReturn(subjectKey);
        return enrollment;
    }

    private Grade createGrade(long subjectId, SubjectType subjectType, int gradeValue) {
        Grade grade = new Grade();
        grade.setSubjectId(subjectId);
        grade.setSubjectType(subjectType);
        grade.setGradeValue(gradeValue);
        return grade;
    }

    private Enrollment createEnrollment(int credits) {
        Enrollment enrollment = mock(Enrollment.class);
        when(enrollment.getSubjectCredits()).thenReturn(credits);
        return enrollment;
    }

    private EnrollmentRelationship createEnrollmentRelationship(ObligatorySubject subject, List<Grade> grades) {
        EnrollmentRelationship relationship = new EnrollmentRelationship();
        relationship.setObligatorySubject(subject);
        relationship.setStudentGrades(grades);
        return relationship;
    }

    private Student createStudent(Long professionalLineId) {
        Student student = new Student();
        student.setProfessionalLineId(professionalLineId);
        return student;
    }

    private ElectiveSubject createElectiveSubject(Long professionalLineId) {
        ElectiveSubject electiveSubject = new ElectiveSubject();
        electiveSubject.setProfessionalLineId(professionalLineId);
        return electiveSubject;
    }

    private Grade createGrade(Long subjectId, SubjectType subjectType, Long professionalLineId) {
        Grade grade = new Grade();
        grade.setSubjectId(subjectId);
        grade.setSubjectType(subjectType);
        grade.setProfessionalLineId(professionalLineId);
        return grade;
    }


    /*
        NOT GRADE CONFLICT
    */

    @Test
    void testValidateSubject_ShouldReturnError_WhenSubjectTooFarAhead() {
        // Given
        when(student.getSemestersCompleted()).thenReturn(2);
        ObligatorySubject obligatorySubject = createObligatorySubject(5);

        // When
        Result<Void> result = enrollmentValidationService.validateOrdinarySubject(obligatorySubject, student);

        // Then
        assertFalse(result.isSuccess());
        assertEquals("You cannot enroll in subjects that are more than two semesters ahead of the semester you are currently taking.", result.getErrorMessage());
    }

    @Test
    void testValidateNotGradeConflict_NoGrades_Success() {
        ObligatorySubject subject = createObligatorySubject(123L);
        when(enrollmentRelationship.getObligatorySubject()).thenReturn(subject);
        when(enrollmentRelationship.getStudentGrades()).thenReturn(Collections.emptyList());

        Result<Void> result = enrollmentValidationService.validateNotGradeConflict(enrollmentRelationship);

        assertTrue(result.isSuccess());
        verify(enrollmentRelationship).getStudentGrades();
    }

    @Test
    void testValidateNotGradeConflict_SubjectApproved_Error() {
        ObligatorySubject subject = createObligatorySubject(123L);

        Grade approvedGrade = createGrade(123L, SubjectType.OBLIGATORY, 7);

        EnrollmentRelationship relationship = createEnrollmentRelationship(subject, List.of(approvedGrade));

        Result<Void> result = enrollmentValidationService.validateNotGradeConflict(relationship);

        assertFalse(result.isSuccess());
        assertEquals("Can't make enrollment, subject already approved", result.getErrorMessage());
    }

    @Test
    void testValidateNotGradeConflict_TwoFailedAttempts_Error() {
        ObligatorySubject subject = createObligatorySubject(123L);

        Grade failedGrade1 = createGrade(123L, SubjectType.OBLIGATORY, 5);
        Grade failedGrade2 = createGrade(123L, SubjectType.OBLIGATORY, 5);

        EnrollmentRelationship relationship = createEnrollmentRelationship(subject, List.of(failedGrade1, failedGrade2));

        Result<Void> result = enrollmentValidationService.validateNotGradeConflict(relationship);

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessage().contains("extraordinary exam"));
    }

    @Test
    void testValidateNotGradeConflict_OneFailedAttempt_Success() {
        ObligatorySubject subject = createObligatorySubject(123L);

        Grade failedGrade = createGrade(123L, SubjectType.OBLIGATORY, 5);

        EnrollmentRelationship relationship = createEnrollmentRelationship(subject, List.of(failedGrade));

        Result<Void> result = enrollmentValidationService.validateNotGradeConflict(relationship);

        assertTrue(result.isSuccess());
    }

    @Test
    void testValidateNotGradeConflict_OtherSubjectsIgnored_Success() {
        ObligatorySubject subject = createObligatorySubject(123L);

        Grade otherSubjectGrade = createGrade(122L, SubjectType.OBLIGATORY, 5);
        Grade electiveGrade = createGrade(124L, SubjectType.ELECTIVE, 5);

        EnrollmentRelationship relationship = createEnrollmentRelationship(subject, List.of(otherSubjectGrade, electiveGrade));

        Result<Void> result = enrollmentValidationService.validateNotGradeConflict(relationship);

        assertTrue(result.isSuccess());
    }


    @Test
    void testValidateNotDuplicatedEnrollment_EnrollmentAlreadyCreated() {
        // Given
        String groupKey = "group1";
        String subjectKey = "subject1";

        Enrollment existingEnrollment = createEnrollment(groupKey, subjectKey);
        List<Enrollment> groupEnrollments = List.of(existingEnrollment);

        // When
        Result<Void> result = enrollmentValidationService.validateNotDuplicatedEnrollment(groupKey, subjectKey, groupEnrollments);

        // Then
        assertFalse(result.isSuccess());
        assertEquals("Enrollment Already Created", result.getErrorMessage());
    }

    @Test
    void testValidateNotDuplicatedEnrollment_SubjectAlreadyEnrolled() {
        // Given
        String groupKey = "group2";
        String subjectKey = "subject1";

        Enrollment existingEnrollment = createEnrollment("group1", "subject1"); // Different group but same subject
        List<Enrollment> groupEnrollments = List.of(existingEnrollment);

        // When
        Result<Void> result = enrollmentValidationService.validateNotDuplicatedEnrollment(groupKey, subjectKey, groupEnrollments);

        // Then
        assertFalse(result.isSuccess());
        assertEquals("Subject Already Enrolled", result.getErrorMessage());
    }

    @Test
    void testValidateNotDuplicatedEnrollment_NoDuplicates_Success() {
        // Given
        String groupKey = "group1";
        String subjectKey = "subject2";

        Enrollment existingEnrollment1 = createEnrollment("group1", "subject1");
        Enrollment existingEnrollment2 = createEnrollment("group1", "subject3");
        List<Enrollment> groupEnrollments = List.of(existingEnrollment1, existingEnrollment2);

        // When
        Result<Void> result = enrollmentValidationService.validateNotDuplicatedEnrollment(groupKey, subjectKey, groupEnrollments);

        // Then
        assertTrue(result.isSuccess());
    }

    /*
        validateMaximumCreditsPerStudent
     */



    @Test
    void testValidateMaximumCreditsPerStudent_ExceededCredits() {
        String accountNumber = "student123";
        List<Enrollment> currentStudentEnrollments = List.of(
                createEnrollment(23),
                createEnrollment(32),
                createEnrollment(32),
                createEnrollment(32),
                createEnrollment(32),
                createEnrollment(32)
        );

        when(enrollmentRepository.findByStudentAccountNumberAndSchoolPeriod(accountNumber, "2025-1"))
                .thenReturn(currentStudentEnrollments);

        // When
        Result<Void> result = enrollmentValidationService.validateMaximumCreditsPerStudent(accountNumber, enrollmentRelationship);

        // Then
        assertFalse(result.isSuccess());
        assertEquals("Can't enroll this group, limit of credits per semester will be exceeded. ", result.getErrorMessage());
    }

    @Test
    void testValidateMaximumCreditsPerStudent_UnderMaxCredits_Success() {
        // Given
        String accountNumber = "student123";
        List<Enrollment> currentStudentEnrollments = List.of(
                createEnrollment(24), // 10 credits
                createEnrollment(12)  // 15 credits
        );

        when(enrollmentRepository.findByStudentAccountNumberAndSchoolPeriod(accountNumber, "2025-1"))
                .thenReturn(currentStudentEnrollments);

        // When
        Result<Void> result = enrollmentValidationService.validateMaximumCreditsPerStudent(accountNumber, enrollmentRelationship);

        // Then
        assertTrue(result.isSuccess());
    }

    @Test
    void testValidateMaximumCreditsPerStudent_ExactCredits_Success() {
        // Given
        String accountNumber = "student123";
        List<Enrollment> currentStudentEnrollments = List.of(
                createEnrollment(70),
                createEnrollment(50)
                );

        when(enrollmentRepository.findByStudentAccountNumberAndSchoolPeriod(accountNumber, "2025-1"))
                .thenReturn(currentStudentEnrollments);

        // When
        Result<Void> result = enrollmentValidationService.validateMaximumCreditsPerStudent(accountNumber, enrollmentRelationship);

        // Then
        assertTrue(result.isSuccess());
    }

    /*
       Validate Subject
     */
    @Test
    void testValidateObligatorySubject_ValidSubjectOfCurrentSemester_Success() {
        Student  student = new Student();
        student.setSemestersCompleted(6);

        ObligatorySubject subject = new ObligatorySubject();
        subject.setSemester(6);

        Result<Void> result = enrollmentValidationService.validateOrdinarySubject(subject, student);

        assertTrue(result.isSuccess());
    }

    @Test
    void testValidateObligatorySubject_ValidSubjectOfBelowSemester_Success() {
        Student  student = new Student();
        student.setSemestersCompleted(4);

        ObligatorySubject subject = new ObligatorySubject();
        subject.setSemester(1);

        Result<Void> result = enrollmentValidationService.validateOrdinarySubject(subject, student);

        assertTrue(result.isSuccess());
    }

    @Test
    void testValidateObligatorySubject_ValidSubjectOfAboveSemester_Error() {
        Student  student = new Student();
        student.setSemestersCompleted(1);

        ObligatorySubject subject = new ObligatorySubject();
        subject.setSemester(8);

        Result<Void> result = enrollmentValidationService.validateOrdinarySubject(subject, student);

        assertFalse(result.isSuccess());
        assertEquals("You cannot enroll in subjects that are more than two semesters " +
                "ahead of the semester you are currently taking.", result.getErrorMessage());

    }


    @Test
    void testValidateElectiveSubject_ValidStudentNotSemesterAllowed_Error() {
        Student  student = new Student();
        student.setSemestersCompleted(4);

        ElectiveSubject subject = new ElectiveSubject();

        Result<Void> result = enrollmentValidationService.validateElectiveSubject(subject, student, Collections.emptyList());

        assertFalse(result.isSuccess());
        assertEquals("Only students in 6th semester or above can enroll in elective subjects.", result.getErrorMessage());

    }

    @Test
    void testValidateElectiveSubject_ValidStudentNotLineSelected_Error() {
        Student  student = new Student();
        student.setSemestersCompleted(6);
        student.setProfessionalLineId(null);

        ElectiveSubject subject = new ElectiveSubject();

        Result<Void> result = enrollmentValidationService.validateElectiveSubject(subject, student, Collections.emptyList());

        assertFalse(result.isSuccess());
        assertEquals("You must select a professional line before enrolling in elective subjects.", result.getErrorMessage());

    }

    @Test
    void testValidateElectiveSubject_ValidStudentNotModalitySelected_Error() {
        Student  student = new Student();
        student.setSemestersCompleted(6);
        student.setProfessionalLineId(1L);
        student.setProfessionalLineModality(null);

        ElectiveSubject subject = new ElectiveSubject();

        Result<Void> result = enrollmentValidationService.validateElectiveSubject(subject, student, Collections.emptyList());

        assertFalse(result.isSuccess());
        assertEquals("You must choose a professional line modality before enrolling in elective subjects.", result.getErrorMessage());

    }


    @Test
    void testValidateElectivesModality_ElectivesCompleted_Error() {
        // Given
        Student student = createStudent(1L);
        ElectiveSubject electiveSubject = createElectiveSubject(1L);
        List<Grade> studentGrades = List.of(
                createGrade(101L, SubjectType.ELECTIVE, 1L),
                createGrade(102L, SubjectType.ELECTIVE, 1L),
                createGrade(103L, SubjectType.ELECTIVE, 2L), // Free elective
                createGrade(104L, SubjectType.ELECTIVE, 2L), // Free elective
                createGrade(105L, SubjectType.ELECTIVE, 1L),
                createGrade(106L, SubjectType.ELECTIVE, 1L),
                createGrade(107L, SubjectType.ELECTIVE, 2L), // Free elective
                createGrade(108L, SubjectType.ELECTIVE, 2L)  // Free elective
        );

        // When
        Result<Void> result = enrollmentValidationService.validateElectivesModality(student, electiveSubject, studentGrades);

        // Then
        assertFalse(result.isSuccess());
        assertEquals( "You have already completed all your elective subjects. " +
                "Enrollment in additional electives is not allowed.", result.getErrorMessage());
    }

    @Test
    void testValidateElectivesModality_FreeElectivesLimit_Error() {
        // Given
        Student student = createStudent(1L);
        student.setProfessionalLineModality(ProfessionalLineModality.ELECTIVES);
        ElectiveSubject electiveSubject = createElectiveSubject(2L);
        List<Grade> studentGrades = List.of(
                createGrade(101L, SubjectType.ELECTIVE, 1L),
                createGrade(102L, SubjectType.ELECTIVE, 2L),
                createGrade(103L, SubjectType.ELECTIVE, 2L),
                createGrade(104L, SubjectType.ELECTIVE, 2L),
                createGrade(104L, SubjectType.ELECTIVE, 2L)
        );

        // When
        Result<Void> result = enrollmentValidationService.validateElectivesModality(student, electiveSubject, studentGrades);

        // Then
        assertFalse(result.isSuccess());
        assertEquals("You have reached the limit for free electives from other professional lines. " +
                        "You can only enroll in electives from your professional line."
                ,result.getErrorMessage());
    }

    @Test
    void testValidateElectivesModality_Success() {
        // Given
        Student student = createStudent(1L);
        ElectiveSubject electiveSubject = createElectiveSubject(1L);
        List<Grade> studentGrades = List.of(
                createGrade(101L, SubjectType.ELECTIVE, 1L),
                createGrade(102L, SubjectType.ELECTIVE, 2L)
        );

        // When
        Result<Void> result = enrollmentValidationService.validateElectivesModality(student, electiveSubject, studentGrades);

        // Then
        assertTrue(result.isSuccess());
    }

    @Test
    void testValidateProfessionalPracticesModality_WrongProfessionalLine_Error() {
        // Given
        Student student = createStudent(1L);
        ElectiveSubject electiveSubject = createElectiveSubject(2L); // Different professional line
        List<Grade> studentGrades = List.of(
                createGrade(101L, SubjectType.ELECTIVE, 1L),
                createGrade(102L, SubjectType.ELECTIVE, 1L)
        );

        // When
        Result<Void> result = enrollmentValidationService.validateProfessionalPracticesModality(student, electiveSubject, studentGrades);

        // Then
        assertFalse(result.isSuccess());
        assertEquals("You can only enroll in elective subjects from your professional line.", result.getErrorMessage());
    }

    @Test
    void testValidateProfessionalPracticesModality_MaxElectivesReached_Error() {
        // Given
        Student student = createStudent(1L);
        ElectiveSubject electiveSubject = createElectiveSubject(1L);
        List<Grade> studentGrades = List.of(
                createGrade(101L, SubjectType.ELECTIVE, 1L),
                createGrade(102L, SubjectType.ELECTIVE, 1L),
                createGrade(103L, SubjectType.ELECTIVE, 1L),
                createGrade(104L, SubjectType.ELECTIVE, 1L)
        );

        // When
        Result<Void> result = enrollmentValidationService.validateProfessionalPracticesModality(student, electiveSubject, studentGrades);

        // Then
        assertFalse(result.isSuccess());
        assertEquals("You have already completed all your elective subjects for professional practices.", result.getErrorMessage());
    }

    @Test
    void testValidateProfessionalPracticesModality_Success() {
        // Given
        Student student = createStudent(1L);
        ElectiveSubject electiveSubject = createElectiveSubject(1L);
        List<Grade> studentGrades = List.of(
                createGrade(101L, SubjectType.ELECTIVE, 1L),
                createGrade(102L, SubjectType.ELECTIVE, 1L)
        );

        // When
        Result<Void> result = enrollmentValidationService.validateProfessionalPracticesModality(student, electiveSubject, studentGrades);

        // Then
        assertTrue(result.isSuccess());
    }
}
