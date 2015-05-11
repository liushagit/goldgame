package com.orange.model;

import java.io.UnsupportedEncodingException;

/**
 * 聊天信息
 * @author hqch
 *
 */
public class ChatMessage extends Message {
	
	private String protocol;
	
	private int userID;
	
	private byte[] msg;

	public ChatMessage(String protocol, int userID, byte[] msg) {
		this.protocol = protocol;
		this.userID = userID;
		this.msg = msg;
	}
	
	public ChatMessage(int userID, String message){
		this.userID = userID;
		try {
			this.msg = message.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public byte[] getMsg() {
		int len = 6 + msg.length;
		byte[] datas = new byte[len];
		int index = 0;
		index = putMsgHead(datas, index);
		index = putShort(datas, index, (short)len);
		System.arraycopy(msg, 0, datas, index, msg.length);
		index += msg.length;
		index = putMsgEnd(datas, index);
		
		return datas;
	}

	public void setMsg(byte[] msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ChatMessage [protocol=" + protocol + ", userID=" + userID
				+ ", msg=" + msg.length + "]";
	}
	
}
