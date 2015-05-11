package com.orange.goldgame.domain;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**消息的Id*/
	private int messageId;
	/**表示消息是属于那个玩家的*/
	private int playerId;
	/**消息的内容*/
	private String message;
	/**玩家是否查看过本条消息*/
	private byte isCheck;
	/**玩家受到消息的时间*/
	private String sendTime;
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public byte getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(byte isCheck) {
		this.isCheck = isCheck;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
