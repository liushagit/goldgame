package com.orange.model;

/**
 * 缓存用户信息
 * @author hqch
 *
 */
public class CacheMessage extends Message {

	private String protocol;
	
	private int userID;
	
	private byte[] msg;

	public CacheMessage(String protocol, int userID, byte[] msg) {
		this.protocol = protocol;
		this.userID = userID;
		this.msg = msg;
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
		return msg;
	}

	public void setMsg(byte[] msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "CacheMessage [protocol=" + protocol + ", userID=" + userID
				+ ", msg=" + msg.length + "]";
	}
	
}
