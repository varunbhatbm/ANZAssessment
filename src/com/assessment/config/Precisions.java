package com.assessment.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import com.assessment.utils.CurrencyConverterLogger;

public class Precisions {

	private static Precisions instance = null;

	private LinkedHashMap<String, Integer> currencyPrecisionDetails = new LinkedHashMap<String, Integer>();

	private static Object lockObject = new Object();

	private Precisions() {
		readPrecisionDetails();
	}

	public static Precisions getInstance() {
		if (instance == null) {
			synchronized (lockObject) {
				if (instance == null) {
					instance = new Precisions();
				}

			}
		}

		return instance;
	}

	public Set getCurrencySet() {
		return currencyPrecisionDetails.keySet();
	}

	public int getCurrencyCount() {
		return currencyPrecisionDetails.size();
	}

	
	public LinkedHashMap<String, Integer> getCurrencyPrecisionDetails() {
		return currencyPrecisionDetails;
	}

	public void setCurrencyPrecisionDetails(LinkedHashMap<String, Integer> currencyPrecisionDetails) {
		this.currencyPrecisionDetails = currencyPrecisionDetails;
	}

	public int getPrecision(String currency) {
		int precisionValue = -1;
		if (currencyPrecisionDetails.containsKey(currency)) {
			precisionValue = currencyPrecisionDetails.get(currency);
		}

		return precisionValue;
	}

	private void readPrecisionDetails() {
		FileReader fr;
		try {
			URL fileLocation = Precisions.class.getClassLoader().getResource("CurrencyPrecisions.txt");
			
			fr = new FileReader(fileLocation.getPath());
			BufferedReader bfr = new BufferedReader(fr);

			String lineData = null;
			while ((lineData = bfr.readLine()) != null) {
				CurrencyConverterLogger.debug("Line :"+lineData);
				String[] lineParts = lineData.split("[ =]");

				String currencyName = lineParts[0];
				String precision = lineParts[1];
				Integer precisionIntegerValue = null;

				if ((precisionIntegerValue = Integer.parseInt(precision)) == null) {
					CurrencyConverterLogger.error("Invalid Precision data read from file: "+precision);
					continue;
				}

				currencyPrecisionDetails.put(currencyName, precisionIntegerValue);

				CurrencyConverterLogger.debug("Extracted Precision Data: "+currencyName+" = "+precisionIntegerValue);

			}

			System.out.println("Currency Precision Data : " + currencyPrecisionDetails);

			CurrencyConverterLogger.debug("Currency Precision Data : " + currencyPrecisionDetails);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public String getCurrencyInPrecision(String nameOfCurrency,Float currencyValue)
	{
		return String.format("%." + getPrecision(nameOfCurrency) + "f", currencyValue);
	}

}
