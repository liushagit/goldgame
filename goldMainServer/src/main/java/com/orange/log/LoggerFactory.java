/**
 * Juice
 * com.juice.orange.game.log
 * LoggerFactory.java
 */
package com.orange.log;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author shaojieque
 * 2013-3-21
 */
public class LoggerFactory {
	private static Logger logger;
	//
	public static Logger getLogger(Class<?> clazz) {
		setupLogger();
		logger = Logger.getLogger(clazz);
		return logger;
	}
	
	public static Logger getLogger(){
		setupLogger();
		logger = Logger.getRootLogger();
		return logger; 
	}
	
	private static void setupLogger() {
		try {
			LogManager.resetConfiguration();// 还原到最初的配置
			Properties pro = new Properties();
			LoggerFactory.class.getClassLoader();
			//
			InputStream input = ClassLoader
					.getSystemClassLoader().getResourceAsStream("log4j.properties");
			pro.load(input);
			PropertyConfigurator.configure(pro);
			//
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
