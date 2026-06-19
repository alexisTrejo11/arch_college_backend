package io.github.alexistrejo11.architecture.college.curriculum.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerInsertDTO;
import io.github.alexistrejo11.architecture.college.curriculum.mapper.CareerMapper;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.Career;
import io.github.alexistrejo11.architecture.college.curriculum.repository.CareerRepository;
import io.github.alexistrejo11.architecture.college.curriculum.service.CareerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CareerServiceImpl implements CareerService {
    private final CareerRepository careerRepository;
    private final CareerMapper careerMapper;
    private final KeyGenerationService keyGenerationService;

    @Override
    @Cacheable(value = "careerByIdCache", key = "#careerId")
    public Optional<CareerDTO> getCareerById(Long careerId) {
        return careerRepository.findById(careerId)
                .map(careerMapper::entityToDTO);
    }

    @Override
    @Cacheable(value = "careerWithSubjectsCache", key = "#careerId")
    public Optional<CareerDTO> getCareerByIdWithSubjects(Long careerId) {
        return careerRepository.findById(careerId)
                .map(careerMapper::entityToDTO);
    }

    @Override
    @Cacheable(value = "careerByNameCache", key = "#name")
    public Optional<CareerDTO> getCareerByName(String name) {
        return careerRepository.findByName(name)
                .map(careerMapper::entityToDTO);
    }

    @Override
    @Cacheable(value = "allCareersCache")
    public List<CareerDTO> getAllCareers() {
        return careerRepository.findAll().stream()
                .map(careerMapper::entityToDTO)
                .toList();
    }

    @Override
    public void createCareer(CareerInsertDTO careerInsertDTO) {
        Career career = careerMapper.insertDtoToEntity(careerInsertDTO);
        career.setKey(keyGenerationService.generateCareerKey(career));
        careerRepository.save(career);
    }

    @Override
    public void updateCareer(CareerInsertDTO careerInsertDTO, Long careerId) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new EntityNotFoundException("Career with ID " + careerId + " not found"));

        careerMapper.updateEntity(career, careerInsertDTO);
        careerRepository.save(career);
    }
}