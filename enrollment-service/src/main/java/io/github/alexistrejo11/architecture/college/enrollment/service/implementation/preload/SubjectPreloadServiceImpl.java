package io.github.alexistrejo11.architecture.college.enrollment.service.implementation.preload;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ElectiveSubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.ObligatorySubjectDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Subject.SubjectSeriesDTO;
import io.github.alexistrejo11.architecture.college.common.service.curriculum.AcademicCurriculumFacadeService;
import io.github.alexistrejo11.architecture.college.common.models.subject.Subject;
import io.github.alexistrejo11.architecture.college.common.utils.CustomPage;
import io.github.alexistrejo11.architecture.college.enrollment.mappers.ElectiveSubjectMapper;
import io.github.alexistrejo11.architecture.college.enrollment.mappers.ObligatorySubjectMapper;
import io.github.alexistrejo11.architecture.college.enrollment.mappers.SubjectSeriesMapper;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.ElectiveSubject;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.ObligatorySubject;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.SubjectSeries;
import io.github.alexistrejo11.architecture.college.enrollment.repository.ElectiveSubjectRepository;
import io.github.alexistrejo11.architecture.college.enrollment.repository.ObligatorySubjectRepository;
import io.github.alexistrejo11.architecture.college.enrollment.repository.SubjectSeriesRepository;
import io.github.alexistrejo11.architecture.college.enrollment.service.PreloadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SubjectPreloadServiceImpl implements PreloadDataService<Subject> {

    private final AcademicCurriculumFacadeService obligatoryAcademicCurriculumFacadeService;
    private final ObligatorySubjectRepository obligatorySubjectRepository;
    private final ElectiveSubjectRepository electiveSubjectRepository;
    private final ObligatorySubjectMapper obligatorySubjectMapper;
    private final ElectiveSubjectMapper electiveSubjectMapper;
    private final SubjectSeriesMapper subjectSeriesMapper;
    private final SubjectSeriesRepository subjectSeriesRepository;
    private final Map<String, String> processStatus = new ConcurrentHashMap<>();

    @Autowired
    public SubjectPreloadServiceImpl(@Qualifier("AcademicCurriculumFacadeServiceImpl") AcademicCurriculumFacadeService obligatoryAcademicCurriculumFacadeService,
                                     ObligatorySubjectRepository obligatorySubjectRepository,
                                     ElectiveSubjectRepository electiveSubjectRepository,
                                     ObligatorySubjectMapper obligatorySubjectMapper,
                                     ElectiveSubjectMapper electiveSubjectMapper, SubjectSeriesMapper subjectSeriesMapper, SubjectSeriesRepository subjectSeriesRepository) {
        this.obligatoryAcademicCurriculumFacadeService = obligatoryAcademicCurriculumFacadeService;
        this.obligatorySubjectRepository = obligatorySubjectRepository;
        this.electiveSubjectRepository = electiveSubjectRepository;
        this.obligatorySubjectMapper = obligatorySubjectMapper;
        this.electiveSubjectMapper = electiveSubjectMapper;
        this.subjectSeriesMapper = subjectSeriesMapper;
        this.subjectSeriesRepository = subjectSeriesRepository;
    }

    @Override
    public void startPreload(String processId) {
        processStatus.put(processId, "Started");

        new Thread(() -> preload(processId)).start();
    }

    @Override
    public String getPreloadStatus(String processId) {
        return processStatus.get(processId);
    }

    @Override
    @Transactional
    public void preload(String processId) {
        int pageSize = 10;
        processStatus.put(processId, "Processing");

        try {
            List<ObligatorySubject> obligatorySubjects = getAllObligatorySubjects(pageSize);
            List<ElectiveSubject> electiveSubjects = getAllElectiveSubjects(pageSize);
            List<SubjectSeries> subjectSeries = getAllSubjectsSerialization(pageSize);

            saveSubjects(obligatorySubjects, electiveSubjects, subjectSeries);
            processStatus.put(processId, "Completed");

            log.info("Preloaded {} subjects into enrollment-service", obligatorySubjects.size() + electiveSubjects.size());
        } catch (Exception e) {
            processStatus.put(processId, "Failed");

            log.error("Failed to preload subjects: {}", e.getMessage());
        }
    }

    @Override
    public void clear() {
        obligatorySubjectRepository.deleteAll();
    }

    private List<ObligatorySubject> getAllObligatorySubjects(int pageSize)  {
        int page = 0;
        boolean hasMorePages = true;
        List<ObligatorySubject> allSubjects = new ArrayList<>();

        while (hasMorePages) {
            CustomPage<ObligatorySubjectDTO> obligatorySubjectPage = obligatoryAcademicCurriculumFacadeService.getObligatorySubjectsPageable(page, pageSize);

            List<ObligatorySubject> obligatorySubjects = obligatorySubjectPage.getContent()
                    .stream()
                    .map(obligatorySubjectMapper::dtoToEntity)
                    .toList();
            allSubjects.addAll(obligatorySubjects);

            hasMorePages = obligatorySubjectPage.hasNext();
            page++;
        }

        return allSubjects;
    }

    private List<SubjectSeries> getAllSubjectsSerialization(int pageSize)  {
        int page = 0;
        boolean hasMorePages = true;
        List<SubjectSeries> allSubjects = new ArrayList<>();

        while (hasMorePages) {
            CustomPage<SubjectSeriesDTO> obligatorySubjectPage = obligatoryAcademicCurriculumFacadeService.getSubjectSeriesPageable(page, pageSize);

            List<SubjectSeries> obligatorySubjects = obligatorySubjectPage.getContent()
                    .stream()
                    .map(subjectSeriesMapper::dtoToEntity)
                    .toList();
            allSubjects.addAll(obligatorySubjects);

            hasMorePages = obligatorySubjectPage.hasNext();
            page++;
        }

        return allSubjects;
    }

    private List<ElectiveSubject> getAllElectiveSubjects(int pageSize)  {
        int page = 0;
        boolean hasMorePages = true;
        List<ElectiveSubject> allSubjects = new ArrayList<>();

        while (hasMorePages) {
            CustomPage<ElectiveSubjectDTO> obligatorySubjectPage = obligatoryAcademicCurriculumFacadeService.getElectiveSubjectsPageable(page, pageSize);

            List<ElectiveSubject> obligatorySubjects = obligatorySubjectPage.getContent()
                    .stream()
                    .map(electiveSubjectMapper::dtoToEntity)
                    .toList();
            allSubjects.addAll(obligatorySubjects);

            hasMorePages = obligatorySubjectPage.hasNext();
            page++;
        }

        return allSubjects;
    }

    private void saveSubjects(List<ObligatorySubject> obligatorySubjects, List<ElectiveSubject> electiveSubjects, List<SubjectSeries> subjectSeries) {
        obligatorySubjectRepository.deleteAll();
        electiveSubjectRepository.deleteAll();
        subjectSeriesRepository.deleteAll();

        obligatorySubjectRepository.saveAll(obligatorySubjects);
        electiveSubjectRepository.saveAll(electiveSubjects);
        subjectSeriesRepository.saveAll(subjectSeries);
    }
}
