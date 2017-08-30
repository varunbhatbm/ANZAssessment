package com.assessment;

import java.util.HashMap;

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

	private void buildCurrencyMatrix() {
		for (int i = 0; i < Currencies.currencyArray.length; i++) {
			String baseCurrency = Currencies.currencyArray[i];
			for (int j = 0; j < Currencies.currencyArray.length; j++) {
				String toCurrency = Currencies.currencyArray[j];
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
		System.out.println();
		System.out.println(
				"====================================== Conversion Matrix =====================================");
		System.out.println(
				"_______________________________________________________________________________________________");
		System.out.format("%8s", "Base | ");
		for (int i = 0; i < Currencies.currencyArray.length; i++) {
			System.out.format("%8s", Currencies.currencyArray[i] + " | ");
		}
		System.out.println("");
		System.out.println(
				"_______________________________________________________________________________________________");

		for (int i = 0; i < Currencies.currencyArray.length; i++) {
			System.out.format("%8s", Currencies.currencyArray[i] + " | ");
			for (int j = 0; j < Currencies.currencyArray.length; j++) {

				System.out.format("%8s", currencyConversionMatrix[i][j] + " | ");
			}
			System.out.println("");
			// System.out.println("-----------------------------------------------------------------------------------------------");
			System.out.println(
					"_______________________________________________________________________________________________");

		}
		System.out.println();
		System.out.println();

	}

}
