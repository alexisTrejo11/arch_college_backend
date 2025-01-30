package microservice.academic_curriculum_service.Service.Implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import microservice.common_classes.DTOs.Carrer.CareerDTO;
import microservice.common_classes.DTOs.Carrer.CareerInsertDTO;
import microservice.academic_curriculum_service.Mappers.CareerMapper;
import microservice.academic_curriculum_service.Model.Career.Career;
import microservice.academic_curriculum_service.Repository.CareerRepository;
import microservice.academic_curriculum_service.Service.CareerService;
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