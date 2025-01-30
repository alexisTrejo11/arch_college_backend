package microservice.schedule_service.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupFilterRequestDTO {
    private String schoolPeriod;
    private String subjectType;
    private String subjectKey;
    private String classroom;
    private int page = 0;
    private int size = 10;
    private String sortBy = "id";
    private String sortOrder = "asc";

    public Sort getSort() {
        return Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
    }
}

