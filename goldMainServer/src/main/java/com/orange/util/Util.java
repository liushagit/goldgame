package com.orange.util;

public class Util {

	/**
	 * byte[]转int
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		// 由高位到低位
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;// 往高位游
		}
		return value;
	}
	
	public static int putShort(byte[] datas, int index,short vol) {
		datas[index++] = (byte) ((vol >>> 8) & 0xFF);
		datas[index++] = (byte) (vol & 0xFF);
		return index;
	}
	
	public static int putInt(byte[] datas, int index,int vol) {
		datas[index++] = (byte) ((vol >>> 24) & 0xFF);
		datas[index++] = (byte) ((vol >>> 16) & 0xFF);
		datas[index++] = (byte) ((vol >>> 8) & 0xFF);
		datas[index++] = (byte) (vol & 0xFF);
		return index;
	}
}
