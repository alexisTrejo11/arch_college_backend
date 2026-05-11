package io.github.alexistrejo11.architecture.college.grade.service;


import io.github.alexistrejo11.architecture.college.common.utils.response.Result;
import io.github.alexistrejo11.architecture.college.grade.service.dto.GradeInsertDTO;
import io.github.alexistrejo11.architecture.college.grade.service.dto.GradeRelationshipsDTO;

public interface GradeRelationshipService {
    Result<GradeRelationshipsDTO> validateGradeRelationship(GradeInsertDTO gradeInsertDTO);
}
