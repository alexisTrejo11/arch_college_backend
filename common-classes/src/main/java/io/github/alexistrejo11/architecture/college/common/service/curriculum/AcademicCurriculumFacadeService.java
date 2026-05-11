package io.github.alexistrejo11.architecture.college.common.service.curriculum;

import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerDTO;
import io.github.alexistrejo11.architecture.college.common.dto.ProfessionalLine.ProfessionalLineDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.SubjectSeriesDTO;
import io.github.alexistrejo11.architecture.college.common.utils.CustomPage;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface AcademicCurriculumFacadeService {
    CompletableFuture<ObligatorySubjectDTO> getOrdinarySubjectById(Long subjectId);
    CompletableFuture<CareerDTO> getCareerById(Long careerId);
    CompletableFuture<ProfessionalLineDTO> getProfessionalLineById(Long professionalLineId);
    CompletableFuture<ElectiveSubjectDTO> getElectiveSubjectById(Long subjectId);

    CompletableFuture<List<ObligatorySubjectDTO>> getObligatorySubjectsByCareer(String careerKey);
    CompletableFuture<List<ElectiveSubjectDTO>> getElectiveSubjectsByCareer(Long careerId);

    CustomPage<ObligatorySubjectDTO> getObligatorySubjectsPageable(int page, int size);
    CustomPage<ElectiveSubjectDTO> getElectiveSubjectsPageable(int page, int size);
    CustomPage<SubjectSeriesDTO> getSubjectSeriesPageable(int page, int pageSize);

}
