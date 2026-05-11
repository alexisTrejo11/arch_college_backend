package io.github.alexistrejo11.architecture.college.grade.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Enrollment.GroupEnrollmentDTO;
import io.github.alexistrejo11.architecture.college.grade.mapper.SubjectMapper;
import io.github.alexistrejo11.architecture.college.grade.model.Subject;
import io.github.alexistrejo11.architecture.college.grade.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {
    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;

    public Subject ensureSubjectsExist(GroupEnrollmentDTO groupEnrollmentDTO) {
        Optional<Subject> optionalSubject = subjectRepository.findBySubjectIdAndSubjectType(groupEnrollmentDTO.getSubjectId(), groupEnrollmentDTO.getSubjectType());
        if (optionalSubject.isEmpty()) {
            Subject subject = new Subject();
            subject.setSubjectId(groupEnrollmentDTO.getSubjectId());
            subject.setSubjectId(groupEnrollmentDTO.getSubjectId());
            subject.setSubjectName(groupEnrollmentDTO.getSubjectName());
            subject.setSubjectType(groupEnrollmentDTO.getSubjectType());
            subject.setSubjectCredits(groupEnrollmentDTO.getSubjectCredits());

            subject = subjectRepository.saveAndFlush(subject);

            return subject;
        }

        return optionalSubject.get();
    }

}
