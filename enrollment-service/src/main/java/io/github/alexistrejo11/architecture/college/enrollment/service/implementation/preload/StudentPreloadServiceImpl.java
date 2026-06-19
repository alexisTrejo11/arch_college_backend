package io.github.alexistrejo11.architecture.college.enrollment.service.implementation.preload;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Student.StudentDTO;
import io.github.alexistrejo11.architecture.college.common.service.student.StudentFacadeService;
import io.github.alexistrejo11.architecture.college.common.utils.CustomPage;
import io.github.alexistrejo11.architecture.college.enrollment.mappers.StudentMapper;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.Student;
import io.github.alexistrejo11.architecture.college.enrollment.repository.StudentRepository;
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
public class StudentPreloadServiceImpl implements PreloadDataService<Student> {

    private final StudentFacadeService studentFacadeService;
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final Map<String, String> processStatus = new ConcurrentHashMap<>();

    @Autowired
    public StudentPreloadServiceImpl(@Qualifier("StudentFacadeServiceImpl") StudentFacadeService studentFacadeService,
                                     StudentMapper studentMapper, StudentRepository studentRepository) {
        this.studentFacadeService = studentFacadeService;
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
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
        int pageSize = 20;
        processStatus.put(processId, "Processing");

        try {
            List<Student> allStudents = getAllStudents(pageSize);

            saveStudents(allStudents);
            processStatus.put(processId, "Completed");

            log.info("Preloaded {} student into enrollment-service", allStudents.size());
        } catch (Exception e) {
            processStatus.put(processId, "Failed");

            log.error("Failed to preload student: {}", e.getMessage());
        }
    }

    @Override
    public void clear() {
        studentRepository.deleteAll();
    }

    private List<Student> getAllStudents(int pageSize)  {
        int page = 0;
        boolean hasMorePages = true;
        List<Student> allStudents = new ArrayList<>();

        while (hasMorePages) {
            CustomPage<StudentDTO> studentPage = studentFacadeService.getStudentsPageable(page, pageSize);
            List<Student> students = studentPage.getContent().stream()
                    .map(studentMapper::dtoToEntity)
                    .toList();
            allStudents.addAll(students);

            hasMorePages = studentPage.hasNext();
            page++;
        }

        return allStudents;
    }

    private void saveStudents(List<Student> students) {
        studentRepository.deleteAll();
        studentRepository.saveAll(students);
    }
}
