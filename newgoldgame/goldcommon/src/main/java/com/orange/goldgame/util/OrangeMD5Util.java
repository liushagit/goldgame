package com.orange.goldgame.util;

import java.io.IOException;
import java.io.InputStream;



public class OrangeMD5Util {

	public static String key = "";
	static{
		InputStream in = OrangeMD5Util.class.getClassLoader().getResourceAsStream("key.txt");
		byte b[];
		try {
			b = new byte[in.available()];
			in.read(b);
			key = new String(b);
		} catch (IOException e) {
		}
	}
	
//	public static String getCheckInfo1(String pUsername, String pPassword, String pIMEI, String pRandomNumber, String pChannel) {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(pUsername);
//		buffer.append(key.substring(0, 20));
//		buffer.append(pPassword);
//		return OrangeMD5.getMD5Code(buffer.toString());
//	}
//
//	public static String getCheckInfo2(String pUsername, String pPassword, String pIMEI, String pRandomNumber, String pChannel) {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(pUsername);
//		buffer.append(pIMEI);
//		buffer.append(pRandomNumber);
//		buffer.append(pChannel);
//		buffer.append(key.substring(0, 20));
//		buffer.append(pPassword);
//		return OrangeMD5.getMD5Code(buffer.toString());
//	}
//	
	public static String getCheckInfo1(String pUsername, String pPassword, String pIMEI, String pRandomNumber, String pChannel) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(pUsername);
		String keyStr = key.substring(0, 10);
		buffer.append(keyStr);
		buffer.append(pPassword);
		return OrangeMD5.getMD5Code(buffer.toString());
	}

	public static String getCheckInfo2( String pUsername, String pPassword, String pIMEI, String pRandomNumber, String pChannel) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(pUsername);
		buffer.append(pIMEI);
		buffer.append(pRandomNumber);
		buffer.append(pChannel);
		String keyStr = key.substring(0, 10);
		buffer.append(keyStr);
		buffer.append(pPassword);
		return OrangeMD5.getMD5Code(buffer.toString());
	}


}
