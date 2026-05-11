package io.github.alexistrejo11.architecture.college.curriculum.repository;

import io.github.alexistrejo11.architecture.college.curriculum.model.Subject.SubjectSeries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectSeriesRepository extends JpaRepository<SubjectSeries, Long> {
        Page<SubjectSeries> findAll(Pageable pageable);
}
