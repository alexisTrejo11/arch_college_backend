package microservice.schedule_service.Documentation;

public class SwaggerExamples {

    public static final String OBLIGATORY_GROUP_EXAMPLE = """
            {
            "subjectId": 1,
            "subjectKey": "MATH101",
            "subjectName": "Mathematics",
            "subjectType": "LECTURE",
            "availableSpots": 30,
            "groupStatus": "ACTIVE",
            "headTeacherAccountNumber": "T12345",
            "groupType": "ORDINAL",
            "classroom": "Room 101",
            "schedule": [{"day": "Monday", "startTime": "10:00", "endTime": "12:00"}],
            "teacherIds": ["T12345", "T67890"]
            }""";

    public static final String ELECTIVE_GROUP_EXAMPLE = """
            {
            "subjectId": 2,
            "subjectKey": "SCI101",
            "subjectName": "Science",
            "subjectType": "PRACTICAL",
            "availableSpots": 20,
            "groupStatus": "ACTIVE",
            "headTeacherAccountNumber": "T67890",
            "groupType": "SPECIAL",
            "classroom": "Room 102",
            "schedule": [{"day": "Wednesday", "startTime": "14:00", "endTime": "16:00"}],
            "teacherId": "T67890"
            }""";

    public static final String OBLIGATORY_GROUP_CREATED_RESPONSE = """
            {
            "status": "success",
            "data": {
            "id": 1,
            "groupKey": "MATH101",
            "subjectName": "Mathematics"
            }
            }""";

    public static final String ELECTIVE_GROUP_CREATED_RESPONSE = """
            {
            "status": "success",
            "data": {
            "id": 2,
            "groupKey": "SCI101",
            "subjectName": "Science"
            }
            }""";

    public static final String CONFLICT_RESPONSE = """
            {
            "status": "error",
            "message": "Conflict in group schedule"
            }""";

    public static final String FORBIDDEN = """
        {
            "success": false,
            "data": null,
            "message": "User does not have sufficient permissions",
            "code": 404,
            "time_stamp": "2024-09-15T10:30:00"
        }
        """;

    public static final String UNAUTHORIZED = """
        {
            "success": false,
            "data": null,
            "message": "User does not have sufficient permissions",
            "code": 404,
            "time_stamp": "2024-09-15T10:30:00"
        }
        """;
}
