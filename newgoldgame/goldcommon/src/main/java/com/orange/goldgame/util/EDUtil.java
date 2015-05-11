package com.orange.goldgame.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class EDUtil {


	private static final byte[] KEY = "orangegame".getBytes();
	/**
	 * 加密
	 * @param encrypt
	 * @return
	 */
	public static String encrypt(String encrypt){
		
		if (encrypt == null) {
			return "";
		}
		byte b[];
		try {
			b = encrypt.getBytes("utf-8");
			System.out.println(Arrays.toString(b));
			int len = b.length;
			for (int i = 0; i < len; i++) {
				b[i] = (byte) (b[i] ^ KEY[ i % KEY.length]);
			}
			return byte2hex(b);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 解密
	 * @param encrypt
	 * @return
	 */
	public static String decrypt(String encrypt){
		if (encrypt == null) {
			return "";
		}
		byte b[] = hex2Byte(encrypt);
		System.out.println(Arrays.toString(b));
		int len = b.length;
		for (int i = 0; i < len; i++) {
			b[i] = (byte) (b[i] ^ KEY[ i % KEY.length]);
		}
		try {
			return new String(b,"utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		return "";
		
	}
	
	public static byte[] hex2Byte(String hex) {
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			b[(i / 2)] = (byte) Integer.decode("0x" + hex.substring(i, i + 2))
					.intValue();
		}
		return b;
	} 
	
	private static String byte2hex(byte[] b) {
		//一个字节的数， 转成16进制字符串   

		String hs = "";
		String tmp = "";
		for (int n = 0; n < b.length; n++) {
			//整数转成十六进制表示   

			tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (tmp.length() == 1) {
				hs = hs + "0" + tmp;
			} else {
				hs = hs + tmp;
			}
		}
		tmp = null;
		return hs;
	}
	
	public static void main(String[] args) {
		String sssString = "测试";
		System.out.println(sssString);
		sssString = encrypt(sssString);
		System.out.println(sssString);
		sssString = decrypt(sssString);
		System.out.println(sssString);
	}
}
