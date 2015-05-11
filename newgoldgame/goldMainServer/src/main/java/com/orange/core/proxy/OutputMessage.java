package com.orange.core.proxy;

import com.orange.core.Message;

public class OutputMessage extends Message {

	/**玩家登陆服务器返回协议号*/
	private static final int LOGIN_PROTOCOL = 5001;

	private String sessionID;
	private int protocol;
	private int userID;
	private byte[] msg;
	private long createTime;
	
	public OutputMessage(String sessionID, byte[] msg) {
		this.sessionID = sessionID;
		this.msg = msg;
		this.protocol = byteArrayToShort(new byte[] { msg[0],msg[1] });
		if(this.protocol == LOGIN_PROTOCOL){
			this.userID = byteArrayToInt(new byte[] { msg[7],msg[8],msg[9],msg[10] });
		}
		
		createTime = System.currentTimeMillis();
	}

	public long getCreateTime(){
		return this.createTime;
	}
	
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public byte[] getOriginalDatas(){
		return this.msg;
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

	public int getUserID() {
		return userID;
	}

	public int getProtocol() {
		return protocol;
	}
	
	@Override
	public String toString() {
		return "OutputMessage [sessionID=" + sessionID + ", protocol="
				+ protocol + ", userID=" + userID + ", msg="
				+ msg.length + "]";
	}
}
