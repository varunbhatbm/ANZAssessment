package com.assessment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

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
			fr = new FileReader("CurrencyPrecisions.txt");
			BufferedReader bfr = new BufferedReader(fr);

			String lineData = null;
			while ((lineData = bfr.readLine()) != null) {
				// System.out.println("Line :"+lineData);
				String[] lineParts = lineData.split("[ =]");

				String currencyName = lineParts[0];
				String precision = lineParts[1];
				Integer precisionIntegerValue = null;

				if ((precisionIntegerValue = Integer.parseInt(precision)) == null) {
					// System.err.println("Invalid Precision data read from
					// file: "+precision);
					continue;
				}

				currencyPrecisionDetails.put(currencyName, precisionIntegerValue);

				// System.out.println("Extracted Precision Data:
				// "+currencyName+" = "+precisionIntegerValue);

			}

			System.out.println("Currency Precision Data : " + currencyPrecisionDetails);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
