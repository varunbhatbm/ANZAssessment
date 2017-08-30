package com.assessment.core;

import com.assessment.config.Currencies;
import com.assessment.config.CurrencyConversionConfiguration;
import com.assessment.conversionchain.*;
import com.assessment.utils.CurrencyConverterLogger;

public class CurrencyConverter {
	
	private Currencies currencies = new Currencies();
	
	public Currencies getCurrencies() {
		return currencies;
	}

	public void setCurrencies(Currencies currencies) {
		this.currencies = currencies;
	}
		
	private static AbstractConverter abstractConverter = null;
	
	static
	{
		abstractConverter = new DirectConverter();
		AbstractConverter singleHopConverter = new SingleHopConverter();
		AbstractConverter multiHopConverter = new MultiHopConverter();
		
		abstractConverter.setNextConverter(singleHopConverter);
		singleHopConverter.setNextConverter(multiHopConverter);		
	}
	
	public Float doConversion(String fromCurrency, String toCurrency, Float currencyValue) {
		Float returnValue = null;
		
		returnValue = abstractConverter.checkAndPerformConversion(fromCurrency, toCurrency, currencyValue);

		return returnValue;
		
	}
	
}
