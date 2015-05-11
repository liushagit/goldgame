package com.orange.goldgame.util;

import java.io.IOException;
import java.io.InputStream;

public class KeysUtil {

	public static String alipay_private_key = "";
	public static String alipay_public_key = "";
	public static String app_key = "";
	static{
		InputStream in = OrangeMD5Util.class.getClassLoader().getResourceAsStream("key.txt");
		byte b[];
		try {
			b = new byte[in.available()];
			in.read(b);
			app_key = new String(b);
		} catch (IOException e) {
		}
		
		in = OrangeMD5Util.class.getClassLoader().getResourceAsStream("alipay_private_key.txt");
		try {
			b = new byte[in.available()];
			in.read(b);
			alipay_private_key = new String(b);
		} catch (IOException e) {
		}
		
		in = OrangeMD5Util.class.getClassLoader().getResourceAsStream("alipay_public_key.txt");
		try {
			b = new byte[in.available()];
			in.read(b);
			alipay_public_key = new String(b);
		} catch (IOException e) {
		}
	}
}
