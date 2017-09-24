package com.zopa.test;

import com.zopa.test.bo.Loan;

public class Main {

    public static void main(String[] args) {

        String fileName = "/Users/richard.velito/Developer/java/zopa/resources/data.csv";
        int loan = 1000;

        Loan l = new Loan(fileName, loan);
    }
}
