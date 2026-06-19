package io.github.alexistrejo11.architecture.college.curriculum.service;

import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerInsertDTO;


import java.util.List;
import java.util.Optional;

public interface CareerService {
    Optional<CareerDTO> getCareerById(Long careerId);
    Optional<CareerDTO> getCareerByIdWithSubjects(Long careerId);
    Optional<CareerDTO> getCareerByName(String name);
    List<CareerDTO> getAllCareers();
    void createCareer(CareerInsertDTO careerInsertDTO);
    void updateCareer(CareerInsertDTO careerInsertDTO, Long careerId);
}
