package io.github.alexistrejo11.architecture.college.curriculum.controller;

import jakarta.validation.Valid;
import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerDTO;
import io.github.alexistrejo11.architecture.college.common.dto.Carrer.CareerInsertDTO;
import io.github.alexistrejo11.architecture.college.common.utils.response.ResponseWrapper;
import io.github.alexistrejo11.architecture.college.curriculum.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/api/careers")
public class CareerController {

    private final CareerService careerService;

    @Autowired
    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @Operation(summary = "Get Career by ID", description = "Fetches the career with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Career found", content = @Content(schema = @Schema(implementation = CareerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Career not found", content = @Content)
    })
    @GetMapping("/{careerId}")
    public ResponseEntity<ResponseWrapper<CareerDTO>> getCareerById(@PathVariable Long careerId) {
        Optional<CareerDTO> careerOptional = careerService.getCareerById(careerId);
        return careerOptional
                .map(career -> ResponseEntity.ok(ResponseWrapper.found(career, "Career", "ID", careerId)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Career", "ID", careerId)));
    }

    @Operation(summary = "Get Career by Name", description = "Fetches the career with the specified name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Career found", content = @Content(schema = @Schema(implementation = CareerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Career not found", content = @Content)
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseWrapper<CareerDTO>> getCareerByName(@PathVariable String name) {
        Optional<CareerDTO> careerOptional = careerService.getCareerByName(name);
        return careerOptional
                .map(career -> ResponseEntity.ok(ResponseWrapper.found(career, "Career", "name", name)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseWrapper.notFound("Career", "name", name)));
    }

    @Operation(summary = "Get All Careers", description = "Fetches all available careers")
    @ApiResponse(responseCode = "200", description = "Careers found", content = @Content(schema = @Schema(implementation = CareerDTO.class)))
    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<List<CareerDTO>>> getAllCareers() {
        List<CareerDTO> careers = careerService.getAllCareers();
        return ResponseEntity.ok(ResponseWrapper.found(careers, "Careers"));
    }

    @Operation(summary = "Create Career", description = "Creates a new career entry")
    @ApiResponse(responseCode = "201", description = "Career created", content = @Content)
    @PostMapping
    public ResponseEntity<ResponseWrapper<Void>> createCareer(@Valid @RequestBody CareerInsertDTO careerInsertDTO) {
        careerService.createCareer(careerInsertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(null, "Career"));
    }

    @Operation(summary = "Update Career", description = "Updates an existing career by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Career updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Career not found", content = @Content)
    })
    @PutMapping("/{careerId}")
    public ResponseEntity<ResponseWrapper<Void>> updateCareer(
            @Valid @RequestBody CareerInsertDTO careerInsertDTO,
            @Parameter(description = "ID of the career to be updated") @PathVariable Long careerId) {
        careerService.updateCareer(careerInsertDTO, careerId);
        return ResponseEntity.ok(ResponseWrapper.updated(null, "Career"));
    }
}