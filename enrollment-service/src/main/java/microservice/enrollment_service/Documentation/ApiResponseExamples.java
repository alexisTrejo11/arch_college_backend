package microservice.enrollment_service.Documentation;


public class ApiResponseExamples {

    public static final String ENROLLMENT_CREATED = """
            {
              "success": true,
              "data" : null,
              "message": "Enrollment successfully created",
              "code": 201,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String INVALID_INPUT_DATA = """
            {
              "success": false,
              "data" : null,
              "message": "Invalid data",
              "code": 400,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String ENROLLMENT_UPDATED = """
            {
              "success": true,
              "data" : null,
              "message": "Enrollment successfully updated",
              "code": 200,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String ENROLLMENT_NOT_FOUND = """
            {
              "success": false,
              "data" : null,
              "message": "Enrollment not found",
              "code": 404,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String ENROLLMENT_DELETED = """
            {
              "success": true,
              "data" : null,
              "message": "Enrollment successfully deleted",
              "code": 200,
              "time_stamp": "2024-09-15T10:30:00"
            }""";


    public static final String UNAUTHORIZED = """
        {
          "success": false,
          "message": "Unauthorized - User not authenticated",
          "code": 401,
          "time_stamp": "2024-09-15T10:30:00"
        }""";

    public static final String FORBIDDEN = """
        {
          "success": false,
          "message": "Forbidden - User lacks required permissions",
          "code": 403,
          "time_stamp": "2024-09-15T10:30:00"
        }""";

    public static final String ENROLLMENT_FETCHED = """
            {
              "success": true,
              "message": "Enrollment data successfully fetched",
              "code": 200,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String INVALID_FILTER = """
            {
              "success": false,
              "message": "Invalid filter. Valid filters: [SEMESTERS_COMPLETED, ACADEMIC_YEAR, etc.]",
              "code": 400,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String ENROLLMENTS_FETCHED = """
            {
              "success": true,
              "message": "Enrollment data successfully fetched with applied filters",
              "code": 200,
              "time_stamp": "2024-09-15T10:30:00"
            }""";
}

