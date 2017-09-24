package com.zopa.test;

import com.zopa.test.bo.Loan;

public class Main {

    public static void main(String[] args) {

        String fileName = "/Users/richard.velito/Developer/java/zopa/resources/data.csv";
        int amount = 1000;

        Loan loan = new Loan(fileName);
        loan.displayLoanInformation(amount);
    }
}
