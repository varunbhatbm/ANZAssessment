package com.assessment.conversionchain;

import com.assessment.config.Currencies;
import com.assessment.config.CurrencyConversionConfiguration;
import com.assessment.core.CurrencyConversionMatrix;
import com.assessment.utils.CurrencyConverterLogger;

public class DirectConverter extends AbstractConverter {

	public Float doConversion(String fromCurrency, String toCurrency, Float currencyValue) {
		
		Float returnValue = null;
		
		Integer fromCurrencyIndex = Currencies.getCurrencyIndex(fromCurrency);
		Integer toCurrencyIndex = Currencies.getCurrencyIndex(toCurrency);

		//Check using the from and to currency if direct conversion is possible
		// If direct conversion is not possible then use the intermediate currency for conversion
		String conversionVia = CurrencyConversionMatrix
				.getInstance().getCurrencyConversionMatrix()[fromCurrencyIndex][toCurrencyIndex];

		if ((conversionVia == CurrencyConversionMatrix.DIRECT_CHECK)
				|| (conversionVia == CurrencyConversionMatrix.INVERSE)
				) {
			// Since direct conversion is possible use the input file for conversion rates..		
			Float conversionRate = CurrencyConversionConfiguration.getInstance().convertValue(fromCurrency, toCurrency);

			CurrencyConverterLogger.debug("ConversionRate received is "+conversionRate);
			returnValue = currencyValue * conversionRate;
		}
		else if(conversionVia == CurrencyConversionMatrix.SAME_CURRENCY)
		{
			returnValue = currencyValue ;
		}
		
		return returnValue;
		
	}

}
