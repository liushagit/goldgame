package com.orange.model;

/**
 * 消息抽象类
 * @author hqch
 *
 */
public abstract class Message {

	private static final int HEADER = 1000;
	private static final int END = 2000;
	
	/**添加缓存的协议号*/
	public static final String ADD_CACHE = "4444";
	/**去除缓存的协议号*/
	public static final String REMOVE_CACHE = "5555";
	/**获取缓存的协议号*/
	public static final String GET_CACHE = "6666";
	
	protected int putShort(byte[] datas, int index,short vol) {
		datas[index++] = (byte) ((vol >>> 8) & 0xFF);
		datas[index++] = (byte) (vol & 0xFF);
		return index;
	}
	
	/**
	 * 用2个字节代表协议类型
	 * @return 协议类型
	 */
	protected int putAgreeID(byte[] datas, int index,short type) {
		datas[index++] = (byte) ((type >>> 8) & 0xFF);
		datas[index++] = (byte) (type & 0xFF);
		return index;
	}
	
	/**
	 * 用2个字节代表包头
	 * */
	protected int putMsgHead(byte[] datas, int index) {
		datas[index++] = (byte) ((HEADER >>> 8) & 0xFF);
		datas[index++] = (byte) (HEADER & 0xFF);
		return index;
	}

	/**
	 * 写消息尾标识
	 * @param datas
	 * @param index
	 * @return
	 */
	protected int putMsgEnd(byte[] datas, int index) {
		datas[index++] = (byte) ((END >>> 8) & 0xFF);
		datas[index++] = (byte) (END & 0xFF);
		return index;
	}
}
