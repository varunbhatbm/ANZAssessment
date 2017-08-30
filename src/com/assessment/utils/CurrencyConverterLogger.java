package com.assessment.utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.assessment.config.CurrencyConversionConfiguration;

public class CurrencyConverterLogger {
	public static final String CURRENCY_CONVERTER_LOG_CATEGORY = "APP_LOGGER";

	public static Logger logger = LogManager.getLogger(CURRENCY_CONVERTER_LOG_CATEGORY);
			
	public static void debug(String logStatement)
	{
		logger.debug(logStatement);
	}
	
	public static void error(String logStatement)
	{
		logger.error(logStatement);
	}
	
	
}
