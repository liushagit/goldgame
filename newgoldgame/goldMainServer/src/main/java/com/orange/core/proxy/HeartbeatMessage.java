package com.orange.core.proxy;

import java.io.UnsupportedEncodingException;

import com.orange.core.Message;

/**
 * 心跳包
 * @author hqch
 *
 */
public class HeartbeatMessage extends Message {

	private static final  String MSG = "sessionID";
	
	private byte[] msg;
	
	public HeartbeatMessage(){
		try {
			this.msg = MSG.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] getMsg(){
		int len = 10 + msg.length;
		byte[] datas = new byte[len];
		int index = 0;
		index = putMsgHead(datas, index);
		index = putShort(datas, index, (short)len);
		index = putShort(datas, index, (short)HEARTBEAT_SERVER_PROTOCOL);
		index = putShort(datas, index, (short)msg.length);
		System.arraycopy(msg, 0, datas, index, msg.length);
		index += msg.length;
		index = putMsgEnd(datas, index);
		
		return datas;
	}
}
