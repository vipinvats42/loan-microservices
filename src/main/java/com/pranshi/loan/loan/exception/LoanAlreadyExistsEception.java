package com.pranshi.loan.loan.exception;

public class LoanAlreadyExistsEception extends RuntimeException {
    public LoanAlreadyExistsEception(String message) {
        super(message);
    }
}