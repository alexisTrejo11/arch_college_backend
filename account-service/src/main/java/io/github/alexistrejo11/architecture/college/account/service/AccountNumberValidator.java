package io.github.alexistrejo11.architecture.college.account.service;

import java.util.regex.Pattern;

public class AccountNumberValidator {

    public static boolean isStudentAccountNumber(String accountNumber) {
        Pattern pattern = Pattern.compile("^\\d{8}-\\d$");

        return pattern.matcher(accountNumber).matches();
    }
}
