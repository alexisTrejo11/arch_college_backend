package io.github.alexistrejo11.architecture.college.curriculum.model.Subject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import io.github.alexistrejo11.architecture.college.curriculum.model.Career.ProfessionalLine;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "elective_subject")
public class ElectiveSubject extends Subject {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_line_id")
    private ProfessionalLine professionalLine;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
