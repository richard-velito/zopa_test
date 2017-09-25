package com.zopa.test;

import com.zopa.test.bo.Loan;

public class Main {

    public static void main(String[] args) {

    	if ( args.length > 1 ) {
    		
	    	String fileName = args[0];
	    	int amount = Integer.parseInt(args[1]);
	    	
			if (fileName.isEmpty() && amount>0) {
	    	
		        Loan loan = new Loan();
		        loan.loadLenders(fileName);
		        
		        loan.displayLoanInformation(1000);
		        loan.displayLoanInformation(99);
		        loan.displayLoanInformation(399);
		        loan.displayLoanInformation(15000);
			}
    	}
    }
}
