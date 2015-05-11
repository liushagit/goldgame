package com.orange.core;

/**
 * 消息抽象类
 * @author hqch
 *
 */
public abstract class Message {

	private static final int HEADER = 1000;
	private static final int END = 2000;
	
	/**服务器返回聊天协议号*/
	public static final int CHAT_PROTOCOL = 5025;
	/**客户端心跳包协议号*/
	public static final String HEARTBEAT_PROTOCOL = "1106";
	/**客户端没有ID的协议号*/
	public static final String NOT_ID_PROTOCOL = "2001";
	/**客户端跟踪好友协议号*/
	public static final String TRACE_PROTOCOL = "1120";
	/**与目标服务器心跳包协议号*/
	public static final int HEARTBEAT_SERVER_PROTOCOL = -1000;
	/**断线重连协议号*/
	public static final String RECONNECT_PROTOCOL = "1131";
	/**支付宝请求协议号*/
	public static final String ALIPAY_PROTOCOL = "-2000";
	
	public static final String BAIFUBAO_PROTOCOL = "-2001";
	/** mm支付***/
	public static final String MOBELLMM_PROTOCOL = "-2002";
	
	/**服务器返回支付宝请求协议号*/
	public static final int RESPONSE_ALIPAY_PROTOCOL = -5000;
	public static final int RESPONSE_BFB_PROTOCOL = -5001;
	public static final int RESPONSE_MOBELLMM_PROTOCOL = -5002;
	
	protected int putShort(byte[] datas, int index,short vol) {
		datas[index++] = (byte) ((vol >>> 8) & 0xFF);
		datas[index++] = (byte) (vol & 0xFF);
		return index;
	}
	
	protected int putInt(byte[] datas, int index,int vol) {
		datas[index++] = (byte) ((vol >>> 24) & 0xFF);
		datas[index++] = (byte) ((vol >>> 16) & 0xFF);
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
	
	/**
	 * byte[]转short
	 * @param b
	 * @return
	 */
	protected short byteArrayToShort(byte[] b) {
		return (short) (((b[0] << 8) | b[1] & 0xff));
	}
	
	/**
	 * byte[]转int
	 * @param bytes
	 * @return
	 */
	protected int byteArrayToInt(byte[] bytes) {
		int value = 0;
		// 由高位到低位
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;// 往高位游
		}
		return value;
	}
	
	public abstract byte[] getMsg();
}
