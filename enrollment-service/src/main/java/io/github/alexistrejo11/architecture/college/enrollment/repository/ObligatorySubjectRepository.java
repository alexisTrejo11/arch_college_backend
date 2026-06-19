package io.github.alexistrejo11.architecture.college.enrollment.repository;

import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.ObligatorySubject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObligatorySubjectRepository extends MongoRepository<ObligatorySubject, Long> {

    @Query("{ 'key': ?0 }")
    Optional<ObligatorySubject> findByKey(String key);
}