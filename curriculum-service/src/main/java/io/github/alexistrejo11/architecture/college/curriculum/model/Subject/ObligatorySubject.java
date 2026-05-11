package io.github.alexistrejo11.architecture.college.curriculum.model.Subject;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "obligatory_subject")
public class ObligatorySubject extends Subject {
    @Column(name = "semester")
    private int semester;



}
