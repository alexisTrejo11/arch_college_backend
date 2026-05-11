package io.github.alexistrejo11.architecture.college.teacher.config;

public class SwaggerExamples {

    public static final String TEACHER_RESPONSE = """
            {
                "success": true,
                "data": {
                    "id": 1,
                    "first_name": "John",
                    "last_name": "Doe"
                },
                "message": "Teacher data successfully fetched",
                "code": 200,
                "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String TEACHERS_RESPONSE = """
        [
            {
                "success": true,
                "data": {
                    "id": 1,
                    "first_name": "John",
                    "last_name": "Doe"
                },
                "message": "Teacher data successfully fetched",
                "code": 200,
                "time_stamp": "2024-09-15T10:30:00"
            },
            {
                "success": true,
                "data": {
                    "id": 2,
                    "first_name": "Jane",
                    "last_name": "Smith"
                },
                "message": "Teacher data successfully fetched",
                "code": 200,
                "time_stamp": "2024-09-15T11:00:00"
            }
        ]""";


    public static final String TEACHER_CREATED_RESPONSE = """
        {
            "success": true,
            "data": null,
            "message": "Teacher successfully created",
            "code": 200,
            "time_stamp": "2024-09-15T10:30:00"
        }""";

    public static final String INVALID_DATA_RESPONSE = """
        {
            "success": false,
            "data": null,
            "message": "Data validation errors: inva....",
            "code": 400,
            "time_stamp": "2024-09-15T10:30:00"
        }""";



    public static final String TEACHER_DELETED_RESPONSE = """
        {
            "success": true,
            "data": null,
            "message": "Teacher successfully deleted",
            "code": 200,
            "time_stamp": "2024-09-15T10:30:00"
        }""";

    public static final String TEACHER_NOT_FOUND_RESPONSE = """
        {
            "success": false,
            "data": null,
            "message": "Teacher not found",
            "code": 404,
            "time_stamp": "2024-09-15T10:30:00"
        }
        """;

    public static final String FORBIDDEN = """
        {
            "success": false,
            "data": null,
            "message": "User does not have sufficient permissions",
            "code": 404,
            "time_stamp": "2024-09-15T10:30:00"
        }
        """;

    public static final String UNAUTHROZED = """
        {
            "success": false,
            "data": null,
            "message": "User does not have sufficient permissions",
            "code": 404,
            "time_stamp": "2024-09-15T10:30:00"
        }
        """;
}
