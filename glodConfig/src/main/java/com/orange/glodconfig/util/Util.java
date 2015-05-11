package com.orange.glodconfig.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;


/**
 * ClassName:Util 通用工具类
 * 
 * @author 
 * @version
 * @since Ver 1.0
 * @Date 
 * 
 * @see
 */
public class Util {
	private static final Logger log = LoggerFactory.getLogger(Util.class);


	/**
	 * Load properties
	 * 
	 * @param @param fileName
	 * @param @return
	 * @return Properties
	 * @throws
	 */
	public static Properties loadProperties(String fileName) {
		Properties properties = new Properties();

		InputStream inputStream = null;
		try {
			inputStream = Util.class.getClassLoader().getResourceAsStream(fileName);
			properties.load(inputStream);
		} catch (Exception e) {
			log.error("exception", e);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("exception", e);
				}
		}

		return properties;
	}
}
