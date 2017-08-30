package com.assessment.mockitotests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.assessment.config.Currencies;
import com.assessment.config.CurrencyConversionConfiguration;
import com.assessment.config.Precisions;
import com.assessment.core.CurrencyConversionMatrix;
import com.assessment.core.CurrencyConverter;


@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterMockitoTest {

	private Currencies currencies = new Currencies();
	
	private Precisions precisions = Precisions.getInstance();
	
	private CurrencyConversionConfiguration currencyConversionConfigurations = CurrencyConversionConfiguration.getInstance();
	
	@Mock
	private CurrencyConversionMatrix currencyConversionMatrixObj = CurrencyConversionMatrix.getInstance();
	
	@InjectMocks
	private CurrencyConverter currencyConverter= new CurrencyConverter();
	
	@Test // Conversion from JPY to USD.. when USD to JPY conversion rate is configured
	public void testConversion1()
	{
		LinkedHashMap<String, Integer> currencyPrecisionsMap = new LinkedHashMap<>();
		currencyPrecisionsMap.put("JPY", 2);
		currencyPrecisionsMap.put("USD", 3);

		precisions.setCurrencyPrecisionDetails(currencyPrecisionsMap);

		Currencies.setPrecisions(precisions);
		
		
		LinkedHashMap<String, Integer> currencyIndexMap = new LinkedHashMap<>();
		currencyIndexMap.put("JPY", 0);
		currencyIndexMap.put("USD", 1);

		currencies.setCurrencyIndexMap(currencyIndexMap);
		
		HashMap<String, HashMap<String, Float>> conversionRateMap = new HashMap<>();
		HashMap<String,Float> currencyConversionRate = new HashMap<>();
		currencyConversionRate.put("JPY", (float) 120.0);
		conversionRateMap.put("USD", currencyConversionRate);

		currencyConversionConfigurations.setConversionRateMap(conversionRateMap);
//		Mockito.when(currencyConversionConfigurations.getConversionRateMap()).thenReturn(conversionRateMap);
		
		String[][] currencyConversionMatrix = new String[currencyIndexMap.size()][currencyIndexMap.size()];		
		currencyConversionMatrix[0][0]=CurrencyConversionMatrix.SAME_CURRENCY;
		currencyConversionMatrix[0][1]=CurrencyConversionMatrix.DIRECT_CHECK;
		currencyConversionMatrix[1][0]=CurrencyConversionMatrix.INVERSE;
		currencyConversionMatrix[1][1]=CurrencyConversionMatrix.SAME_CURRENCY;
		
		CurrencyConversionMatrix.getInstance().setCurrencyConversionMatrix(currencyConversionMatrix);
		
//		Mockito.when(currencyConversionMatrixObj.getCurrencyConversionMatrix()).thenReturn(currencyConversionMatrix);
		
		currencyConverter.setCurrencies(currencies);
		
//		Mockito.when(currencyConverter.getCurrencies()).thenReturn(currencies);
		
		Float currencyValue = (float) 1.0;
		Float expectedValue = (float) 120.0;
		assertEquals(expectedValue,currencyConverter.doConversion("USD", "JPY", currencyValue));
		
		
	}
	
	@Test // Conversion from INR to GBP using USD as intermediat currency
	public void testConversion2()
	{
		LinkedHashMap<String, Integer> currencyPrecisionsMap = new LinkedHashMap<>();
		currencyPrecisionsMap.put("INR", 2);
		currencyPrecisionsMap.put("GBP", 2);
		currencyPrecisionsMap.put("USD", 2);

		precisions.setCurrencyPrecisionDetails(currencyPrecisionsMap);

		Currencies.setPrecisions(precisions);
		
		LinkedHashMap<String, Integer> currencyIndexMap = new LinkedHashMap<>();
		currencyIndexMap.put("INR", 0);
		currencyIndexMap.put("GBP", 1);
		currencyIndexMap.put("USD", 2);

		currencies.setCurrencyIndexMap(currencyIndexMap);
		
		HashMap<String, HashMap<String, Float>> conversionRateMap = new HashMap<>();
		HashMap<String,Float> currencyConversionRate = new HashMap<>();
		currencyConversionRate.put("INR", (float) 69.5);
		currencyConversionRate.put("GBP", (float) 0.8);
		conversionRateMap.put("USD", currencyConversionRate);
		
		
		currencyConversionConfigurations.setConversionRateMap(conversionRateMap);
		
		String[][] currencyConversionMatrix = new String[currencyIndexMap.size()][currencyIndexMap.size()];		
		currencyConversionMatrix[0][0]=CurrencyConversionMatrix.SAME_CURRENCY;
		currencyConversionMatrix[0][1]="USD";
		currencyConversionMatrix[0][2]=CurrencyConversionMatrix.INVERSE;
		currencyConversionMatrix[1][0]="USD";
		currencyConversionMatrix[1][1]=CurrencyConversionMatrix.SAME_CURRENCY;
		currencyConversionMatrix[1][2]=CurrencyConversionMatrix.INVERSE;
		currencyConversionMatrix[2][0]=CurrencyConversionMatrix.DIRECT_CHECK;
		currencyConversionMatrix[2][1]=CurrencyConversionMatrix.DIRECT_CHECK;
		currencyConversionMatrix[2][2]=CurrencyConversionMatrix.SAME_CURRENCY;
		
		CurrencyConversionMatrix.getInstance().setCurrencyConversionMatrix(currencyConversionMatrix);
				
		currencyConverter.setCurrencies(currencies);
				
		Float currencyValue = (float) 2000;
		Float expectedValue = (float) 23.02;
		assertEquals(expectedValue,currencyConverter.doConversion("INR", "GBP", currencyValue),(float)0.02);
		
		
	}
}
