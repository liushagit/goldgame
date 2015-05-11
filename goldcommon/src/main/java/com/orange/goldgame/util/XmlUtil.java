package com.orange.goldgame.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.juice.orange.game.log.LoggerFactory;


public class XmlUtil {
	private static final Logger log = LoggerFactory.getLogger(XmlUtil.class);

	public static Document strToXml(String str){
		Document document = null;
		try {
			document = DocumentHelper.parseText(str);
		} catch (DocumentException e) {
			log.error("strToXml" , e);
		}
		return document;
	}
}
