package com.orange.goldgame.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;

public class PropertiesUtil {

	private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
	private static final String dirty_words = "dirty_words.properties";

	public static Properties getDirtyWordsProperties() {
		return loadProperties(dirty_words);
	}

	public static Properties loadProperties(String fileName) {
		Properties properties = new Properties();

		InputStream inputStream = null;
		try {
			inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
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
