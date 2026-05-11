package io.github.alexistrejo11.architecture.college.enrollment.repository;

import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.SubjectSeries;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectSeriesRepository extends MongoRepository<SubjectSeries, String> {
}