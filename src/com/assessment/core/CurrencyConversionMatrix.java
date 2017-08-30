package com.assessment.core;

import java.util.HashMap;

import com.assessment.config.Currencies;
import com.assessment.config.CurrencyConversionConfiguration;
import com.assessment.config.Precisions;

public class CurrencyConversionMatrix {
	private static CurrencyConversionMatrix instance = null;

	private int currencyCount = Precisions.getInstance().getCurrencyCount();

	public String[][] currencyConversionMatrix = new String[currencyCount][currencyCount];

	private static Object lockObject = new Object();

	public static final String SAME_CURRENCY = "1:1";
	public static final String INVERSE = "Inv";
	public static final String DIRECT_CHECK = "D";

	private CurrencyConversionMatrix() {
		buildCurrencyMatrix();
	}
	
	public String[][] getCurrencyConversionMatrix() {
		return currencyConversionMatrix;
	}



	public void setCurrencyConversionMatrix(String[][] currencyConversionMatrix) {
		this.currencyConversionMatrix = currencyConversionMatrix;
	}



	public static CurrencyConversionMatrix getInstance() {
		if (instance == null) {
			synchronized (lockObject) {
				if (instance == null) {
					instance = new CurrencyConversionMatrix();
				}

			}
		}

		return instance;
	}
	
	public String[] getCurrencyArray()
	{
		return Currencies.getCurrencyArray();
	}

	private void buildCurrencyMatrix() {
		String[] currencyArray =  getCurrencyArray();
		
		for (int i = 0; i < currencyArray.length; i++) {
			String baseCurrency = currencyArray[i];
			for (int j = 0; j < currencyArray.length; j++) {
				String toCurrency = currencyArray[j];
				if (i == j) {
					currencyConversionMatrix[i][j] = SAME_CURRENCY;
				} else {
					currencyConversionMatrix[i][j] = CurrencyConversionConfiguration.getInstance()
							.findTermCurrency(baseCurrency, toCurrency);
				}
			}
		}

	}

	public void printCurrencyMatrix() {
		String[] currencyArray =  getCurrencyArray();

		System.out.println();
		System.out.println(
				"========================================== Conversion Matrix =========================================");
		System.out.println(
				"______________________________________________________________________________________________________");
		System.out.format("%8s", "Base | ");
		for (int i = 0; i < currencyArray.length; i++) {
			System.out.format("%8s", currencyArray[i] + " | ");
		}
		System.out.println("");
		System.out.println(
				"______________________________________________________________________________________________________");

		for (int i = 0; i < currencyArray.length; i++) {
			System.out.format("%8s", currencyArray[i] + " | ");
			for (int j = 0; j < currencyArray.length; j++) {

				System.out.format("%8s", currencyConversionMatrix[i][j] + " | ");
			}
			System.out.println("");
			// System.out.println("-----------------------------------------------------------------------------------------------");
			System.out.println(
					"______________________________________________________________________________________________________");

		}
		System.out.println();
		System.out.println();

	}

}
