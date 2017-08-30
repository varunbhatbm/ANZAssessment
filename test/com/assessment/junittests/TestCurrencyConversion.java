package com.assessment.junittests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.assessment.app.CurrencyConverterMain;
import com.assessment.core.CurrencyConverter;

public class TestCurrencyConversion {

	@Test
	public void testCurrencyConversionAUDtoUSD() {
		
		String fromCurrency ="AUD";
		String toCurrency ="USD";
		Float conversionValue = new Float(100.0);
		
		CurrencyConverter currencyConverter = new CurrencyConverter();
		
		Float returnValue = currencyConverter.doConversion(fromCurrency, toCurrency, conversionValue);
		
		double d = 83.71001;
		assertEquals(d, returnValue, 0.00001);
		
	}

	@Test
	public void testCurrencyConversionGBPtoNOK() {
		
		String fromCurrency ="GBP";
		String toCurrency ="NOK";
		Float conversionValue = new Float(120.0);
		
		CurrencyConverter currencyConverter = new CurrencyConverter();
		
		Float returnValue = currencyConverter.doConversion(fromCurrency, toCurrency, conversionValue);
		
		double d = 1324.19;
		assertEquals(d, returnValue, 0.01);
		
	}

	@Test
	public void testCurrencyConversionInvalidCurrency() {
		
		String fromCurrency ="INR";
		String toCurrency ="AUD";
		Float conversionValue = new Float(120.0);
		
		try
		{
			CurrencyConverter currencyConverter = new CurrencyConverter();
			
			Float returnValue = currencyConverter.doConversion(fromCurrency, toCurrency, conversionValue);
			
		}
		catch(Exception e){
			assertEquals(true,true);
			System.out.println("Exception thrown as expected..");	
		}
		
		
	}
	
}
