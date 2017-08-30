package com.assessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CurrencyConverterMain {
	
	static
	{
		//Initialize all configurations
		Precisions.getInstance();
		CurrencyConversionConfiguration.getInstance();
		CurrencyConversionMatrix.getInstance().printCurrencyMatrix();
	}

	public static void main(String[] args) {
		InputStreamReader ioReader = new InputStreamReader(System.in);

		BufferedReader bufferedReader = new BufferedReader(ioReader);
		System.out.println("==================================================");
		System.out.println("========== Welcome to Currency Converter =========");
		System.out.println("==================================================");

		String inputString = null;
		String fromCurrency = null;
		String toCurrency = null;
		String valueToConvert = null;
		Float currencyValue = null;

		while (true) {
			System.out.println();
			System.out.println("Example Format: AUD 100.0 in USD, JPY 100.0 in USD ");
			System.out.println("--------------------------------------------------");

			//Request input
			System.out.format("%17s", "Coversion Input: ");

			try {
				inputString = bufferedReader.readLine();
				// System.out.println("Entered value is [" + inputString + "]");

				String[] parsedInput = inputString.split(" ");

				//Validate input.. throw error if invalid data is passed
				validateInput(parsedInput);

				fromCurrency = parsedInput[0];
				toCurrency = parsedInput[3];
				valueToConvert = parsedInput[1];

				currencyValue = Float.parseFloat(valueToConvert);

				Float finalValue = doConversion(fromCurrency, toCurrency, currencyValue);

				//Convert the output according to the desired precision settings in "CurrencyPrecisions.txt" file
				String finalValueWithPrecision = String
						.format("%." + Precisions.getInstance().getPrecision(toCurrency) + "f", finalValue);

				// Display output
				System.out.println("Result         : " + toCurrency + " " + finalValueWithPrecision);
				System.out.println("==================================================");
				System.out.println(" ");

			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.out.println();
				System.out.println("-----------------------------------------------------------");
			}

		}

	}

	
	public static Float doConversion(String fromCurrency, String toCurrency, Float currencyValue) {
		Float returnValue = null;

		Integer fromCurrencyIndex = Currencies.getCurrencyIndex(fromCurrency);
		Integer toCurrencyIndex = Currencies.getCurrencyIndex(toCurrency);

		//Check using the from and to currency if direct conversion is possible
		// If direct conversion is not possible then use the intermediate currency for conversion
		String conversionVia = CurrencyConversionMatrix
				.getInstance().currencyConversionMatrix[fromCurrencyIndex][toCurrencyIndex];

		if ((conversionVia == CurrencyConversionMatrix.DIRECT_CHECK)
				|| (conversionVia == CurrencyConversionMatrix.INVERSE)) {
			// Since direct conversion is possible use the input file for conversion rates..		
			Float conversionRate = CurrencyConversionConfiguration.getInstance().convertValue(fromCurrency, toCurrency);

			// System.out.println("ConversionRate received is "+conversionRate);
			returnValue = currencyValue * conversionRate;
		} else if (conversionVia == CurrencyConversionMatrix.SAME_CURRENCY) {
			// Check if from and to currency are same
			returnValue = currencyValue;
		} else {
			// System.out.println("Converting Via "+conversionVia);

			// Perform intermediate conversion
			Float intermediateValue = doConversion(fromCurrency, conversionVia, currencyValue);

			String furtherConversionVia = null;
			// In case of GBP to NOK conversion.. its GBP -> USD -> EUR -> NOK.. further conversion will take care of it..
			if((furtherConversionVia = checkForFurtherIntermediateConversion(conversionVia,toCurrency)) != null)
			{				
				intermediateValue = doConversion(conversionVia, furtherConversionVia, intermediateValue);
				//Set the conversionVia to the new intermediate currency
				conversionVia = furtherConversionVia;
			}
			
			// Invoke convertValue for final conversion with intermediate current as fromCurrency
			Float finalConversionRate = CurrencyConversionConfiguration.getInstance().convertValue(conversionVia,
					toCurrency);

			// System.out.println("final ConversionRate received is
			// "+finalConversionRate);

			returnValue = intermediateValue * finalConversionRate;
		}

		return returnValue;
	}
	
	public static String checkForFurtherIntermediateConversion(String fromCurrency,String conversionVia)
	{
		Integer fromCurrencyIndex = Currencies.getCurrencyIndex(fromCurrency);
		Integer toCurrencyIndex = Currencies.getCurrencyIndex(conversionVia);
		
		
		String returnString = null;
		
		String furtherConversionString = CurrencyConversionMatrix
		.getInstance().currencyConversionMatrix[fromCurrencyIndex][toCurrencyIndex];
		
		
		if((furtherConversionString == conversionVia) || (furtherConversionString == CurrencyConversionMatrix.SAME_CURRENCY) || 
				(furtherConversionString == CurrencyConversionMatrix.INVERSE) || (furtherConversionString == CurrencyConversionMatrix.DIRECT_CHECK))
		{
			returnString = null;
		}
		else
			returnString = furtherConversionString;
		
		return returnString;
	}
	
	public static void validateInput(String [] inputArray) throws Exception
	{
		// If number of values passed in input are less then 4 then throw error
		if ((inputArray.length != 4) || (inputArray[0].length() != 3) || (inputArray[3].length() != 3) ) {
			throw new Exception("Input not in proper format!! Please try again..");
		}

		// If the currency names passed in the input are not configured then throw error.. 
		if ((Precisions.getInstance().getPrecision(inputArray[0]) == -1)
				|| (Precisions.getInstance().getPrecision(inputArray[3]) == -1)) {
			throw new Exception("Unable to find rate for "+inputArray[0]+"/"+inputArray[3]+"");
		}		
		

		// Check if the value passed is numeric 
		try {
			String valueToConvert = inputArray[1];

			Float currencyValue = Float.parseFloat(valueToConvert);

		} catch (Exception e) {
			throw new Exception("Invalid value ["+inputArray[1]+"] for conversion");
		}
		
	}
}
