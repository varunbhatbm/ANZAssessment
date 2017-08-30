package com.assessment.utils;

import com.assessment.config.Precisions;

public class ValidationUtil {
	public static void validateInput(String [] inputArray) throws Exception
	{
		// If number of values passed in input are less then 4 then throw error
		if ((inputArray.length != 4) || (inputArray[0].length() != 3) || (inputArray[3].length() != 3) ) {
			CurrencyConverterLogger.error("Input not in proper format!! Please try again..");
			throw new Exception("Input not in proper format!! Please try again..");
		}

		// If the currency names passed in the input are not configured then throw error.. 
		if ((Precisions.getInstance().getPrecision(inputArray[0]) == -1)
				|| (Precisions.getInstance().getPrecision(inputArray[3]) == -1)) {
			CurrencyConverterLogger.error("Unable to find conversion rate for "+inputArray[0]+"/"+inputArray[3]+"");
			throw new Exception("Unable to find conversion rate for "+inputArray[0]+"/"+inputArray[3]+"");
		}		
		

		// Check if the value passed is numeric 
		try {
			String valueToConvert = inputArray[1];

			Float currencyValue = Float.parseFloat(valueToConvert);

		} catch (Exception e) {
			CurrencyConverterLogger.error("Invalid value ["+inputArray[1]+"] for conversion");
			throw new Exception("Invalid value ["+inputArray[1]+"] for conversion");
		}
		
	}
}
