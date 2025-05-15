package com.pranshi.loan.loan.mapper;

import com.pranshi.loan.loan.dto.LoanDto;
import com.pranshi.loan.loan.entity.Loans;

public class LoanMapper {
    public static LoanDto mapToLoansDto(Loans loans, LoanDto loansDto) {
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setOutStandingAmount(loans.getOutStandingAmount());
        return loansDto;
    }

    public static Loans mapToLoans(LoanDto loansDto, Loans loans) {
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setOutStandingAmount(loansDto.getOutStandingAmount());
        return loans;
    }
}
