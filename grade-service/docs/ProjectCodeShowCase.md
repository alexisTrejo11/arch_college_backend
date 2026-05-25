# Code showcase

Structured counterpart: [obsidian/ProjectCodeShowCase.md](obsidian/ProjectCodeShowCase.md) (`ProjectCodeShowCase` in [docs-schema.ts](../../docs-schema.ts)).

## Example: grades, teacher workflow, student reads

| **id** | `grade-service-example-1` |

### GradeCommandController.java

`grade-service/src/main/java/io/github/alexistrejo11/architecture/college/grade/controller/grade/GradeCommandController.java`  

Authorize and soft-delete grades under `/v1/api/grades`.

### TeacherGradeController.java

`grade-service/src/main/java/io/github/alexistrejo11/architecture/college/grade/controller/grade/TeacherGradeController.java`  

Pending groups, grading history, and `PUT .../set-grades`.

### StudentAcademicHistoryController.java

`grade-service/src/main/java/io/github/alexistrejo11/architecture/college/grade/controller/academicHistory/StudentAcademicHistoryController.java`  

Student-facing GETs under `/v1/api/students/...`.

Replace stub `content` in Obsidian with curated excerpts for portfolio embeds.
