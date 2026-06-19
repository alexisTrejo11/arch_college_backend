package io.github.alexistrejo11.architecture.college.curriculum.repository;

import io.github.alexistrejo11.architecture.college.curriculum.model.Career.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Optional<Area> findByName(String name);
    @Query("SELECT a FROM Area a " +
            "LEFT JOIN FETCH a.obligatorySubjects os " +
            "LEFT JOIN FETCH a.electiveSubjects es " +
            "WHERE a.id = :areaId")
    Optional<Area> findByIdWithSubjects(Long areaId);

}
