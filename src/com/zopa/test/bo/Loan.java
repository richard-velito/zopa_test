package com.zopa.test.bo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Loan {

    private List<Lender> lenders;
    private static int months = 36;
    private static String requestAmountLine = "Requested amount: £%s";
    private static String rateLine = "Rate: %f ";
    private static String monthlyRepaymentLine = "Monthly repayment: £%s";
    private static String totalRepaymentLine = "Total repayment: £%s";
    private static String errorMessage = "error";

    public Loan(String filename) {

        loadLenders(filename);
    }

    public void displayLoanInformation(int loan) {

        double rate = getRate(loan);
        double m = getMonthly(loan, rate);
        double y = m * months;

        System.out.println( String.format(requestAmountLine, loan) );
        System.out.println( String.format(rateLine, rate) );
        System.out.println( String.format(monthlyRepaymentLine, m) );
        System.out.println(String.format(totalRepaymentLine, y));
    }

    private void loadLenders(String filename) {

        try {

            lenders = new ArrayList<Lender>();

            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {

                String[] e = line.split(Pattern.quote(","));
                if (e.length>2) {

                    if (i>0)
                        lenders.add( new Lender(e[0], Double.parseDouble(e[1]), Integer.parseInt(e[2])) );

                    i++;
                }
            }

            lenders.sort((Lender o1, Lender o2) -> Double.compare(o1.getRate(), o2.getRate()));
        } catch (IOException e) {

            System.out.println(e);
        }
    }

    private double getRate(int loan) {

        int a = 0;
        int i = 0;
        double r = 0;

        for(Lender lender : lenders) {

            a += lender.getAvailable();
            r += lender.getRate();
            i++;

            if (a>=loan)
                break;
        }
        return r/i;
    }

    private double getMonthly(int loan, double rate) {

        double j = rate/12;
        return ( j / ( 1 - ( Math.pow( (1+j) , -months) ) ) ) * loan;
    }

    private class Lender {

        private String key;
        private Double rate;
        private Integer available;

        public Lender(String key, Double rate, Integer available) {

            this.key = key;
            this.rate = rate;
            this.available = available;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Double getRate() {
            return rate;
        }

        public void setRate(Double rate) {
            this.rate = rate;
        }

        public Integer getAvailable() {
            return available;
        }

        public void setAvailable(Integer available) {
            this.available = available;
        }

        public String toString() {

            return key;
        }

    }
}
