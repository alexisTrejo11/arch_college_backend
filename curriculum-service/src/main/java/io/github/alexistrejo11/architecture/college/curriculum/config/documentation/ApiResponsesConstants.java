package io.github.alexistrejo11.architecture.college.curriculum.config.documentation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface ApiResponsesConstants {
    @ApiResponse(
            responseCode = "404",
            description = "Career not found",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            value = """
                    {
                      "success": false,
                      "message": "Career not found with ID: 999",
                      "data": null
                    }"""
                    )
            )
    )
    public static interface NotFoundResponse {}
}
