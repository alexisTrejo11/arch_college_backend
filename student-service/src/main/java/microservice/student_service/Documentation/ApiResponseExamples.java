package microservice.student_service.Documentation;

public class ApiResponseExamples {

    public static final String STUDENT_CREATED = """
            {
              "success": true,
              "message": "Student successfully created",
              "code": 201,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String INVALID_INPUT_DATA = """
            {
              "success": false,
              "message": "Invalid data",
              "code": 400,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String STUDENT_UPDATED = """
            {
              "success": true,
              "message": "Student successfully updated",
              "code": 200,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String STUDENT_NOT_FOUND = """
            {
              "success": false,
              "message": "Student not found",
              "code": 404,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String STUDENT_DELETED = """
            {
              "success": true,
              "message": "Student successfully deleted",
              "code": 200,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String PROFESSIONAL_LINE_SET = """
            {
              "success": true,
              "message": "Professional line data successfully set",
              "code": 200,
              "time_stamp": "2024-09-15T10:30:00"
            }""";

    public static final String SEMESTER_INCREASED = """
            {
              "success": true,
              "message": "Semester count successfully increased",
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

    public static final String STUDENT_FETCHED = """
            {
              "success": true,
              "message": "Student data successfully fetched",
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

    public static final String STUDENTS_FETCHED = """
            {
              "success": true,
              "message": "Student data successfully fetched with applied filters",
              "code": 200,
              "time_stamp": "2024-09-15T10:30:00"
            }""";
}
