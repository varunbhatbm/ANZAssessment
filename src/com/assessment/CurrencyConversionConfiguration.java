package com.assessment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//This class contains the currency conversion configurations
public class CurrencyConversionConfiguration {

	private static CurrencyConversionConfiguration instance = null;

	private HashMap<String, HashMap<String, String>> defaultConversionMap = new HashMap<String, HashMap<String, String>>();

	private HashMap<String, HashMap<String, Float>> conversionRateMap = new HashMap<String, HashMap<String, Float>>();

	private static Object lockObject = new Object();

	private int currencyCount = Precisions.getInstance().getCurrencyCount();

	private CurrencyConversionConfiguration() {
		readConversionData();
	}

	public static CurrencyConversionConfiguration getInstance() {
		if (instance == null) {
			synchronized (lockObject) {
				if (instance == null) {
					instance = new CurrencyConversionConfiguration();
				}

			}
		}

		return instance;
	}

	private String checkIfInversionIsPossible(String fromCurrency, String toCurrency) {
		String retVal = null;

		if (conversionRateMap.containsKey(toCurrency)) {
			HashMap<String, Float> toCurrencyConversionRatesMap = conversionRateMap.get(toCurrency);

			if (toCurrencyConversionRatesMap.containsKey(fromCurrency)) {
				retVal = CurrencyConversionMatrix.INVERSE;
			}
		}

		return retVal;

	}

	public String findTermCurrency(String fromCurrency, String toCurrency) {
		String returnString = null;

		if (conversionRateMap.containsKey(fromCurrency)) {
			HashMap<String, Float> toCurrencyConversionRatesMap = conversionRateMap.get(fromCurrency);

			if (toCurrencyConversionRatesMap.containsKey(toCurrency)) {
				returnString = CurrencyConversionMatrix.DIRECT_CHECK;
			} else if ((returnString = checkIfInversionIsPossible(fromCurrency, toCurrency)) != null) {
				// System.out.println("Found Inv Match..");
			}

		}

		// If the currency is not found in first attempt check if its present as part of other curreny data
		if ((returnString == null) && (returnString = checkIfInversionIsPossible(fromCurrency, toCurrency)) != null) {
			// System.out.println("Found Inv Match..");
		}

		if (returnString == null) {
			// No configuration is found.. use defaults
			
			// System.out.println("Checking defaults..");
			if (defaultConversionMap.containsKey(fromCurrency)) {
				// System.out.println("Found ["+fromCurrency+"]");
				HashMap<String, String> toCurrencyMap = defaultConversionMap.get(fromCurrency);

				// System.out.println("Result Map ["+toCurrencyMap+"]");

				if (toCurrencyMap.containsKey(toCurrency)) {
					returnString = toCurrencyMap.get(toCurrency);
				} else {
					returnString = "USD";
				}
			} else {
				returnString = "USD";
			}

		}

		return returnString;

	}

	private void addToConversionMap(String fromCurrency, String toCurrency, Float conversionRate) {
		if (conversionRateMap.containsKey(fromCurrency)) {
			HashMap<String, Float> toCurrencyConversionRateMap = conversionRateMap.get(fromCurrency);

			toCurrencyConversionRateMap.put(toCurrency, conversionRate);

			conversionRateMap.put(fromCurrency, toCurrencyConversionRateMap);
		} else {
			HashMap<String, Float> toCurrencyConversionRateMap = new HashMap<String, Float>();

			toCurrencyConversionRateMap.put(toCurrency, conversionRate);

			conversionRateMap.put(fromCurrency, toCurrencyConversionRateMap);
		}
	}

	public Float convertValue(String fromCurrency, String toCurrency) {
		Float conversionRate = null;
		if (conversionRateMap.containsKey(fromCurrency)) {
			HashMap<String, Float> toCurrencyConversionRateMap = conversionRateMap.get(fromCurrency);

			if (toCurrencyConversionRateMap.containsKey(toCurrency)) {
				conversionRate = toCurrencyConversionRateMap.get(toCurrency);
			}
		}

		if ((conversionRate == null) && (conversionRateMap.containsKey(toCurrency))) {
			HashMap<String, Float> toCurrencyConversionRateMap = conversionRateMap.get(toCurrency);

			if (toCurrencyConversionRateMap.containsKey(fromCurrency)) {
				conversionRate = 1 / toCurrencyConversionRateMap.get(fromCurrency);
			}
		}
		
		
		return conversionRate;
	}

	private void readConversionData() {
		FileReader fr;
		try {
			fr = new FileReader("CurrencyConversionRates.txt");
			BufferedReader bfr = new BufferedReader(fr);

			String lineData = null;
			while ((lineData = bfr.readLine()) != null) {
				// System.out.println("Line :"+lineData);
				String[] lineParts = lineData.split("[ =]");

				String currencies = null;
				String conversionRate = null;
				Float conversionRateValue = null;

				try {
					currencies = lineParts[0];
					conversionRate = lineParts[1];
				} catch (Exception e) {
					System.err.println("Exception while reading line [" + lineData + "] Proceeding to next line..");
					continue;
				}

				if (currencies.length() != 6) {
					System.err.println("Invalid currency data read from file: " + currencies);
					continue;
				}

				if ((conversionRateValue = Float.parseFloat(conversionRate)) == null) {
					System.err.println("Invalid conversion data read from file: " + conversionRate);
					continue;
				}

				String sourceCurrency = currencies.substring(0, 3);
				String targetCurrency = currencies.substring(3, 6);

				// System.out.println("Extracted CurrencyData is SourceCurrency
				// ["+sourceCurrency+
				// "] TargetCurrency ["+targetCurrency+"] ");

				addToConversionMap(sourceCurrency, targetCurrency, conversionRateValue);

			}

			System.out.println("Currency Rate Date : " + conversionRateMap);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		//Following currencies don't have direct conversion data, hence build the currency links manually..
		HashMap<String, String> internalMapMap = new HashMap<String, String>();
		internalMapMap.put("DKK", "EUR");
		internalMapMap.put("NOK", "EUR");
		internalMapMap.put("USD", "EUR");
		defaultConversionMap.put("CZK", internalMapMap);

		internalMapMap = null;
		internalMapMap = new HashMap<String, String>();
		internalMapMap.put("CZK", "EUR");
		internalMapMap.put("NOK", "EUR");
		internalMapMap.put("USD", "EUR");
		defaultConversionMap.put("DKK", internalMapMap);

		internalMapMap = null;
		internalMapMap = new HashMap<String, String>();
		internalMapMap.put("CZK", "EUR");
		internalMapMap.put("DKK", "EUR");
		internalMapMap.put("USD", "EUR");
		defaultConversionMap.put("NOK", internalMapMap);

		internalMapMap = null;
		internalMapMap = new HashMap<String, String>();
		internalMapMap.put("CZK", "EUR");
		internalMapMap.put("DKK", "EUR");
		internalMapMap.put("NOK", "EUR");
		defaultConversionMap.put("USD", internalMapMap);

	}

}
