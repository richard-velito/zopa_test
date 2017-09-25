package com.zopa.test.bo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class LoanTest {

	@BeforeClass
	public static void before() 
			throws IOException {		
	}
	
	@Test
	public void test_zopa_load_null() {	
		
		Loan loan = new Loan();
		assertFalse( loan.loadLenders(null) );		
	}
	
	@Test
	public void test_zopa_load_empty_file() {	
		
		Loan loan = new Loan();
		assertTrue( loan.loadLenders("src/test/resources/empty.csv") );	
		assertEquals( loan.getLenders().size(), 0 );
	}

	
	@Test
	public void test_zopa_load_badformat_file() {	
		
		Loan loan = new Loan();
		assertFalse( loan.loadLenders("src/test/resources/badformat.csv") );	
		assertEquals( loan.getLenders().size(), 0 );
	}
	
	@Test
	public void test_zopa_load() {	
		
		Loan loan = new Loan();
		assertTrue( loan.loadLenders("src/test/resources/data.csv") );	
		assertEquals( loan.getLenders().size(), 7 );
	}
	
	@Test
	public void test_zopa_rate() {	
		
		Loan loan = new Loan();
		
		assertTrue( loan.loadLenders("src/test/resources/data.csv") );	
		assertEquals( loan.getLenders().size(), 7 );
		assertEquals( loan.getRate(1000000000), 0, 1e-17 );		
		assertEquals( loan.getRate(0), 0, 1e-17 );			
		assertEquals( loan.getRate(-1000), 0, 1e-17 );		
		assertEquals( loan.getRate(200), 0.069, 1e-17 );	
		assertEquals( loan.getRate(900), 0.07, 1e-17 );	
		assertEquals( loan.getRate(1300), 0.072, 1e-10 );				
	}
	
	@Test
	public void test_zopa_monthly() {	
		
		Loan loan = new Loan();
		
		assertTrue( loan.loadLenders("src/test/resources/data.csv") );	
		assertEquals( loan.getLenders().size(), 7 );
		assertTrue( Double.isNaN(loan.getMonthly(0, 0)) );	
		assertTrue( Double.isNaN(loan.getMonthly(100, 0)) );
		assertEquals( loan.getMonthly(0,-20), 0.0, 1e-17 );		
		assertEquals( loan.getMonthly(0,-20), 0.0, 1e-17 );		
		assertEquals( loan.getMonthly(100,0.07), 3.087709686537184, 1e-17 );	
		assertEquals( loan.getMonthly(1300,0.10), 41.94734335198886, 1e-17 );		
	}
	
	@AfterClass
	public static void after() {
		
	}	
}
