package com.orange.goldgame.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private static final String KEY = "0fRI;$zn,y0e3LHQZD^i@I/)w4%=9(28rsjp)V3aJ3#jfEiDeCNx=BTIQ#u:cW!Jxo0bR1EaQ=,6KV2u,zFuBpgC6bu6$S;by0bxMxM)DEurzG(:v)F83wmvOPwxJHoT2R)fNG3!Rqb*+l#-pnZOB3/-76K";
	public static String encode(String input) {
		String input_msg = input + KEY;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] d = md5.digest(input_msg.getBytes() );
			return byte2hex(d,0,d.length);
		} catch (NoSuchAlgorithmException ex) {
			return "";
		}
	}
	
	
	public static String encode(String input,String key) {
		String input_msg = input + key;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] d = md5.digest(input_msg.getBytes() );
			return byte2hex(d,0,d.length);
		} catch (NoSuchAlgorithmException ex) {
			return "";
		}
	}
	
	private static String byte2hex(byte[] b, int start, int end) {
		//一个字节的数， 转成16进制字符串   

		String hs = "";
		String tmp = "";
		for (int n = start; n < end; n++) {
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

	public static void main(String[] args) throws Exception {
		System.out.println(MD5.encode("657464-adfdsads-qazsdWSXfdsfDssE11847_g"));
	}

}
