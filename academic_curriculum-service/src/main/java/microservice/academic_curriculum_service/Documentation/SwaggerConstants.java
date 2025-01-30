package microservice.academic_curriculum_service.Documentation;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public class SwaggerConstants {

    public static final String NOT_FOUND_DESCRIPTION = "Resource not found";

    public static final String CAREER_DTO_EXAMPLE = """
        {
          "id": 1,
          "name": "Architecture",
          "key": "ARQ-2023",
          "titleAwarded": "Bachelor of Architecture"
        }""";

    public static final String CAREER_INSERT_DTO_EXAMPLE = """
        {
          "name": "Landscape Architecture",
          "titleAwarded": "Bachelor of Landscape Architecture",
          "modality": "Hybrid"
        }""";
}