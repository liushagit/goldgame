package com.orange.goldgame.vo;



/**
 * @author wuruihuang 2013.7.15
 */
public class FriendVo{

	private int friendId;
	private String friendName;
	private byte isOnline;
	private byte areaType;
	private byte areaId;
	private byte isLover;
	private int qinMiDu;
	private byte isMessage;
	
	public byte getIsLover() {
		return isLover;
	}
	public void setIsLover(byte isLover) {
		this.isLover = isLover;
	}
	public int getQinMiDu() {
		return qinMiDu;
	}
	public void setQinMiDu(int qinMiDu) {
		this.qinMiDu = qinMiDu;
	}
	public byte getIsMessage() {
		return isMessage;
	}
	public void setIsMessage(byte isMessage) {
		this.isMessage = isMessage;
	}
	public byte getAreaType() {
		return areaType;
	}
	public void setAreaType(byte areaType) {
		this.areaType = areaType;
	}
	public byte getAreaId() {
		return areaId;
	}
	public void setAreaId(byte areaId) {
		this.areaId = areaId;
	}
	public byte getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(byte isOnline) {
		this.isOnline = isOnline;
	}
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
}
