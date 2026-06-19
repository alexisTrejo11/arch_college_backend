package io.github.alexistrejo11.architecture.college.grade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.grade.model.credits.CreditAdvance;
import io.github.alexistrejo11.architecture.college.grade.model.credits.ElectiveCredits;
import io.github.alexistrejo11.architecture.college.grade.model.credits.ObligatoryCredits;
import io.github.alexistrejo11.architecture.college.grade.model.credits.TotalCredits;
import io.github.alexistrejo11.architecture.college.grade.model.grade.GradeTrack;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "academic_histories")
public class AcademicHistory {
    @Id
    @JsonProperty("student_account_number")
    private String studentAccountNumber;

    @JsonProperty("student_name")
    private String studentName;

    @JsonProperty("career_key")
    private String careerKey;

    @JsonProperty("career_name")
    private String careerName;

    @JsonProperty("speciality")
    private String speciality;

    @JsonProperty("academic_average")
    private double academicAverAge;

    @JsonProperty("credit_advance")
    private CreditAdvance creditAdvance;

    @JsonProperty("income_generation")
    private String incomeGeneration;

    @JsonProperty("grades")
    private List<GradeTrack> grades;

    public void initCreditAdvance(int totalObligatoryCredits, int totalElectiveCredits) {
        int totalCareerCredits = totalElectiveCredits + totalObligatoryCredits;

        TotalCredits totalCredits = new TotalCredits(totalCareerCredits);
        ElectiveCredits electiveCredits = new ElectiveCredits(totalElectiveCredits);
        ObligatoryCredits obligatoryCredits = new ObligatoryCredits(totalObligatoryCredits);

        this.creditAdvance = new CreditAdvance(totalCredits, obligatoryCredits, electiveCredits);
    }

    public void reCalculateAverage() {
        double newAcademicAverage = this.grades.stream()
                .mapToDouble(GradeTrack::getGradeValue)
                .average()
                .orElse(0.0);

        this.setAcademicAverAge(newAcademicAverage);
    }

    public void reCalculatePercentages() {
       this.creditAdvance.calculateAllPercentages();
    }

    public void addOrdinaryCreditAdvance(int creditAdvance) {
        this.creditAdvance.getObligatoryCredits().increaseCurrentCredits(creditAdvance);
    }

    public void addTotalCreditAdvance(int creditAdvance) {
        this.creditAdvance.getTotalCredits().increaseCurrentCredits(creditAdvance);
    }
}
