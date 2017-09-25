package com.zopa.test.bo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Loan {

    private List<Lender> lenders;

	private static final int months = 36;
    private static final int min = 1000;
    private static final int max = 15000;
    
    private static final String requestAmountLine = "Requested amount: EUR %s";
    private static final String rateLine = "Rate: %f ";
    private static final String monthlyRepaymentLine = "Monthly repayment: EUR %s";
    private static final String totalRepaymentLine = "Total repayment: EUR %s";
    private static final String invalidAmountMessage = "Invalid amount.";
    private static final String notEnoughAmountMessage = "Is not possible provide a quote.";

    public void displayLoanInformation(int loan) {

    	if (loan<min || loan>max || (loan%100)!=0) {
        
    		System.out.println( invalidAmountMessage );  
        } else {
    		
	        double rate = getRate(loan);
	        if ( rate>0 ) {
	        	
		        double m = getMonthly(loan, rate);
		        double y = m * months;
		
		        System.out.println( String.format(requestAmountLine, loan) );
		        System.out.println( String.format(rateLine, rate) );
		        System.out.println( String.format(monthlyRepaymentLine, m) );
		        System.out.println( String.format(totalRepaymentLine, y) );
	        } else {
	            System.out.println( notEnoughAmountMessage ); 
	        }
    	}
    }

    public boolean loadLenders(String filename) {

    	if (filename==null)
    		return false;
    	
    	boolean response=true;
    	
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
        } catch (Exception e) {
            response = false;
        }
        return response;
    }

    public double getRate(int loan) {

    	if (loan<1)
    		return 0;
    	
        int a = 0;
        int i = 0;
        double r = 0;

        for(Lender lender : lenders) {

            a += lender.getAvailable();
            r += lender.getRate();
            i++;

            if (a>=loan)
                return r/i;
        }
        return 0;
    }

    public double getMonthly(int loan, double rate) {

        double j = rate/12;
        return ( j / ( 1 - ( Math.pow( (1+j) , -months) ) ) ) * loan;
    }

    public List<Lender> getLenders() {
		return lenders;
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
            return String.format("%s has available : %s at %s", key, available, rate);
        }

    }
}
