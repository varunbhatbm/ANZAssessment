package com.assessment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Currencies {

	private static HashMap<String, Integer> currencyIndexMap = new HashMap<String, Integer>();
	public static String [] currencyArray = new String[Precisions.getInstance().getCurrencyCount()];

	static
	{
		// Build Currency Map
		//System.out.println("Building CurrencyMap..");
		int index = 0;
		
		Set currencySet = Precisions.getInstance().getCurrencySet();
		
		Iterator<String> setIter = currencySet.iterator();
		
		while(setIter.hasNext())
		{
			currencyIndexMap.put(setIter.next(), index);
			index++;
		}
		
		System.out.println("CurrencyMap "+currencyIndexMap);
		
		// Build Currency Array
		//System.out.println("Building CurrencyArray..");
		index = 0;
		
		currencySet = Precisions.getInstance().getCurrencySet();
		
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
			System.out.print("["+i+"]=["+currencyArray[i]+"] ");
		}
		System.out.println(" ");
	}

	
}
