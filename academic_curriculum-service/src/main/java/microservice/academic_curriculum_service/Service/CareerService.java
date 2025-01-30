package microservice.academic_curriculum_service.Service;

import microservice.common_classes.DTOs.Carrer.CareerDTO;
import microservice.common_classes.DTOs.Carrer.CareerInsertDTO;
import microservice.common_classes.Utils.Response.Result;


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
