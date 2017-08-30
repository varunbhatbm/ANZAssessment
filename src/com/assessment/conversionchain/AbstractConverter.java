package com.assessment.conversionchain;

import com.assessment.config.Currencies;
import com.assessment.core.CurrencyConversionMatrix;

public abstract class AbstractConverter {

	protected AbstractConverter nextConverter;
	
	public void setNextConverter(AbstractConverter nextConveter)
	{
		this.nextConverter = nextConveter;
	}

	public Float checkAndPerformConversion(String fromCurrency, String toCurrency, Float currencyValue)
	{
		Float returnValue = null;

		if ((returnValue = doConversion(fromCurrency, toCurrency, currencyValue)) == null) {
			if (nextConverter != null) {
				returnValue = nextConverter.checkAndPerformConversion(fromCurrency, toCurrency, currencyValue);
			}			
		} 	
		return returnValue;
	}
	
	abstract protected Float doConversion(String fromCurrency, String toCurrency, Float currencyValue);

}
