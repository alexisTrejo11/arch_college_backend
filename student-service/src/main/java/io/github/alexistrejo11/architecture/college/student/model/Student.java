package io.github.alexistrejo11.architecture.college.student.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.common.models.Person;
import io.github.alexistrejo11.architecture.college.common.models.subject.ProfessionalLineModality;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
public class Student extends Person {
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "curp", nullable = false)
    private String curp;

    @Column(name = "semesters_completed")
    private int semestersCompleted;

    @Column(name = "income_generation",  nullable = false)
    private String incomeGeneration;

    @Column(name = "career_id", nullable = false)
    private Long careerId;

    @Column(name = "professional_line_id")
    private Long professionalLineId;

    @Column(name = "professional_line_modality")
    @Enumerated(EnumType.STRING)
    private ProfessionalLineModality professionalLineModality;

    public void initializeAcademicValues(String accountNumber, String incomeGeneration) {
        this.accountNumber = accountNumber;
        this.incomeGeneration = incomeGeneration;
        this.semestersCompleted = 0;
    }

    public void increaseSemesterCompleted() {
        this.semestersCompleted++;
    }
}
