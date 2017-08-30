package com.assessment.conversionchain;

import com.assessment.config.Currencies;
import com.assessment.config.CurrencyConversionConfiguration;
import com.assessment.core.CurrencyConversionMatrix;
import com.assessment.utils.CurrencyConverterLogger;

public class SingleHopConverter extends AbstractConverter {

	public Float doConversion(String fromCurrency, String toCurrency, Float currencyValue) {
		Float returnValue = null;

		Integer fromCurrencyIndex = Currencies.getCurrencyIndex(fromCurrency);
		Integer toCurrencyIndex = Currencies.getCurrencyIndex(toCurrency);

		//Check using the from and to currency if direct conversion is possible
		// If direct conversion is not possible then use the intermediate currency for conversion
		String conversionVia = CurrencyConversionMatrix
				.getInstance().getCurrencyConversionMatrix()[fromCurrencyIndex][toCurrencyIndex];

		Integer conversionViaIndex = Currencies.getCurrencyIndex(conversionVia);
		String singleHopCurrency = CurrencyConversionMatrix
				.getInstance().getCurrencyConversionMatrix()[conversionViaIndex][toCurrencyIndex];
		
		if ((singleHopCurrency == CurrencyConversionMatrix.DIRECT_CHECK)
				|| (singleHopCurrency == CurrencyConversionMatrix.INVERSE)
				) {
			// Since direct conversion is possible use the input file for conversion rates..		
			Float intermediatConversionRate = CurrencyConversionConfiguration.getInstance().convertValue(fromCurrency, conversionVia);

			Float intermediateValue = intermediatConversionRate * currencyValue;
			CurrencyConverterLogger.debug("Intermediat ConversionRate is "+intermediatConversionRate);
			
			Float finalConversionRate = CurrencyConversionConfiguration.getInstance().convertValue(conversionVia, toCurrency);
			CurrencyConverterLogger.debug("Final ConversionRate is "+finalConversionRate);

			returnValue = finalConversionRate * intermediateValue;
		}
		else if(singleHopCurrency == CurrencyConversionMatrix.SAME_CURRENCY)
		{
			Float finalConversionRate = CurrencyConversionConfiguration.getInstance().convertValue(fromCurrency, toCurrency);
			CurrencyConverterLogger.debug("Final ConversionRate is "+finalConversionRate);

			returnValue = finalConversionRate * currencyValue;

		}
		
		return returnValue;
	}

}
