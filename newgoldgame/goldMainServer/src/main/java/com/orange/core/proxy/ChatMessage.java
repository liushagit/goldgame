package com.orange.core.proxy;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.orange.core.Message;
import com.orange.log.LoggerFactory;

/**
 * 聊天信息
 * @author hqch
 *
 */
public class ChatMessage extends Message {

	private static Logger logger = LoggerFactory.getLogger(ChatMessage.class);
	
	/**系统角色ID*/
	public static final int SYS_USER_ID = -1;
	
	/**玩家登陆服务器返回协议号*/
	private static final int CHAT_PROTOCOL = 5025;
	
	private int userID;
	
	private String userName;
	
	private int curGold;
	
	private byte[] msg;

	public ChatMessage(byte[] datas) {
		this.userID = byteArrayToInt(new byte[] { datas[2],datas[3],datas[4],datas[5] });
		this.msg = datas;
	}
	
	/***
	 * 系统广播
	 * @param message
	 */
	public ChatMessage(String message){
		this.userID = SYS_USER_ID;
		this.userName = "系统消息";
		this.curGold = 0;
		try {
			this.msg = message.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	/**
	 * 玩家广播
	 * @return
	 */
	public byte[] getUserMsg() {
		//需减去msg头部的协议号
		int len = 8 + msg.length - 2;
		byte[] datas = new byte[len];
		int index = 0;
		index = putMsgHead(datas, index);
		index = putShort(datas, index, (short)len);
		index = putShort(datas, index, (short)CHAT_PROTOCOL);
		System.arraycopy(msg, 2, datas, index, msg.length - 2);
		index += msg.length - 2;
		index = putMsgEnd(datas, index);
		
		return datas;
	}
	
	/**
	 * 系统广播
	 * @return
	 */
	public byte[] getSystemMsg() {
		byte[] nameByte = null;
		try {
			nameByte = userName.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		
		int len = 20 + msg.length + nameByte.length;
		byte[] datas = new byte[len];
		int index = 0;
		index = putMsgHead(datas, index);
		index = putShort(datas, index, (short)len);
		index = putShort(datas, index, (short)CHAT_PROTOCOL);
		index = putInt(datas, index, userID);
		
		index = putShort(datas, index, (short)nameByte.length);
		System.arraycopy(nameByte, 0, datas, index, nameByte.length);
		index += nameByte.length;
		
		index = putShort(datas, index, (short)msg.length);
		System.arraycopy(msg, 0, datas, index, msg.length);
		index += msg.length;
		
		index = putInt(datas, index, curGold);
		index = putMsgEnd(datas, index);
		
		return datas;
	}

	public void setMsg(byte[] msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ChatMessage [userID=" + userID + ", msg=" + msg.length + "]";
	}

	@Override
	public byte[] getMsg() {
		return null;
	}
	
}
