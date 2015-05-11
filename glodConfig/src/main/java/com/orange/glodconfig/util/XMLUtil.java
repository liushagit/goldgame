package com.orange.glodconfig.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtil {

	private static Document document = null;
	private static Element root = null;
	public static void init(String file) throws Exception {
		SAXReader reader = new SAXReader();
		document = reader.read(XMLUtil.class.getClassLoader().getResourceAsStream(file));
		root = document.getRootElement();
		if (root == null) {
			throw new Exception("no root node in config");
		}
	}
	public static Element getRoot(String file,boolean reload) throws Exception {
		if(reload || root == null){
			init(file);
		}
		return root;
	}
}
