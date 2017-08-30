package com.assessment.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.assessment.config.CurrencyConversionConfiguration;
import com.assessment.config.Precisions;
import com.assessment.core.CurrencyConversionMatrix;
import com.assessment.core.CurrencyConverter;
import com.assessment.utils.CurrencyConverterLogger;
import com.assessment.utils.ValidationUtil;

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
		
		CurrencyConverterLogger.debug("Currency converter application started..");

		BufferedReader bufferedReader = new BufferedReader(ioReader);
		System.out.println("==================================================");
		System.out.println("========== Welcome to Currency Converter =========");
		System.out.println("==================================================");

		String inputString = null;
		String fromCurrency = null;
		String toCurrency = null;
		String valueToConvert = null;
		Float currencyValue = null;

		CurrencyConverter currencyConverter = new CurrencyConverter();
		
		while (true) {
			System.out.println();
			System.out.println("Example Format: AUD 100.0 in USD, JPY 100.0 in USD ");
			System.out.println("--------------------------------------------------");

			//Request input
			System.out.format("%17s", "Coversion Input: ");

			try {
				inputString = bufferedReader.readLine();
				CurrencyConverterLogger.debug("Entered value is [" + inputString + "]");

				String[] parsedInput = inputString.split(" ");

				//Validate input.. throw error if invalid data is passed
				ValidationUtil.validateInput(parsedInput);

				fromCurrency = parsedInput[0];
				toCurrency = parsedInput[3];
				valueToConvert = parsedInput[1];

				currencyValue = Float.parseFloat(valueToConvert);

				Float finalValue = currencyConverter.doConversion(fromCurrency, toCurrency, currencyValue);

				//Convert the output according to the desired precision settings in "CurrencyPrecisions.txt" file
				String finalValueWithPrecision = Precisions.getInstance().getCurrencyInPrecision(toCurrency, finalValue);

				CurrencyConverterLogger.debug("Conversion Result is ["+finalValueWithPrecision+"]");

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

	
	
	
}
