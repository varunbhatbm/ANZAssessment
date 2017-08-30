package com.assessment.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.assessment.utils.CurrencyConverterLogger;

public class Currencies {

	private static HashMap<String, Integer> currencyIndexMap = new HashMap<String, Integer>();
	private static Precisions precisions = Precisions.getInstance();
	public static String [] currencyArray = new String[getPrecisions().getCurrencyCount()];
	
	static
	{
		// Build Currency Map
		CurrencyConverterLogger.debug("Building CurrencyMap..");

		int index = 0;
		
		Set currencySet = getPrecisions().getCurrencySet();
		
		Iterator<String> setIter = currencySet.iterator();
		
		while(setIter.hasNext())
		{
			currencyIndexMap.put(setIter.next(), index);
			index++;
		}
		
		CurrencyConverterLogger.debug("CurrencyMap "+currencyIndexMap);
		
		// Build Currency Array
		CurrencyConverterLogger.debug("Building CurrencyArray..");
		index = 0;
		
		currencySet = getPrecisions().getCurrencySet();
		
		setIter = currencySet.iterator();
		
		while(setIter.hasNext())
		{
			currencyArray[index] = setIter.next();
			index++;
		}

		
	}
	
	public static int getCurrencyIndex(String currency)
	{
		int currencyIndex = -1;
		if(currencyIndexMap.containsKey(currency))
		{
			currencyIndex = currencyIndexMap.get(currency);
		}
		return currencyIndex;
	}

	public static void printCurrencyArray()
	{
		System.out.print("CurrencyArray : ");
		for(int i = 0 ;i<currencyArray.length;i++)
		{
			CurrencyConverterLogger.debug("["+i+"]=["+currencyArray[i]+"] ");
			System.out.print("["+i+"]=["+currencyArray[i]+"] ");
		}
		System.out.println(" ");
	}

	public static HashMap<String, Integer> getCurrencyIndexMap() {
		return currencyIndexMap;
	}

	public static void setCurrencyIndexMap(HashMap<String, Integer> currencyIndexMap) {
		Currencies.currencyIndexMap = currencyIndexMap;
	}

	public static Precisions getPrecisions() {
		return precisions;
	}

	public static void setPrecisions(Precisions precisions) {
		Currencies.precisions = precisions;
		
		currencyArray = new String[precisions.getCurrencyCount()];
		
		// Build Currency Array
		CurrencyConverterLogger.debug("Building CurrencyArray..");
		int index = 0;
		
		Set currencySet = getPrecisions().getCurrencySet();
		
		Iterator<String> setIter = currencySet.iterator();
		
		while(setIter.hasNext())
		{
			currencyArray[index] = setIter.next();
			index++;
		}

		
	}

	public static String[] getCurrencyArray() {
		return currencyArray;
	}

	public static void setCurrencyArray(String[] currencyArray) {
		Currencies.currencyArray = currencyArray;
	}
	
}
