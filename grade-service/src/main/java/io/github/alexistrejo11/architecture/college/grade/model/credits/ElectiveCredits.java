package io.github.alexistrejo11.architecture.college.grade.model.credits;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ElectiveCredits extends CreditCategory {
    public ElectiveCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

        @Override
    public void validateCredits() {
        if (currentCredits > totalCredits) {
            throw new IllegalArgumentException("Elective current credits cannot exceed total credits.");
        }
    }

    @Override
    public void increaseCurrentCredits(int creditsIncome) {
        this.currentCredits += creditsIncome;
    }


}
