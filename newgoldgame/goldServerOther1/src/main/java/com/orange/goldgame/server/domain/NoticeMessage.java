package com.orange.goldgame.server.domain;

import java.util.Date;

import com.orange.goldgame.util.DateUtil;

/**
 * 通告消息
 */
public class NoticeMessage {

	private byte isNotice;
	private String message;
	private Date startTime;
	private int spaceTime;
	private Date endTime;
	private byte isSingle;
	private String sendPlayers;
	
	public NoticeMessage(){}
	
	public NoticeMessage(byte isNotice,String message,String startTime){
		this.isNotice = isNotice;
		this.message = message;
		this.startTime = getTime(startTime);
	}

	public byte getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(byte isNotice) {
		this.isNotice = isNotice;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = getTime(startTime);
	}

	public int getSpaceTime() {
		return spaceTime;
	}

	public void setSpaceTime(int spaceTime) {
		this.spaceTime = spaceTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = getTime(endTime);
	}

	public byte getIsSingle() {
		return isSingle;
	}

	public void setIsSingle(byte isSingle) {
		this.isSingle = isSingle;
	}

	public String getSendPlayers() {
		return sendPlayers;
	}

	public void setSendPlayers(String sendPlayers) {
		this.sendPlayers = sendPlayers;
	}
	
	private Date getTime(String time){
		return DateUtil.getDetailTime(time);
	}
	
	
}
