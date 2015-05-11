package com.orange.goldgame.vo;

import com.orange.goldgame.domain.PlayerFriend;


/**
 * @author wuruihuang 2013.7.15
 */
public class FriendVo{

	private PlayerFriend friend;
	private byte isOnline;
	private byte areaType;
	private byte areaId;
	
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
	public PlayerFriend getFriend() {
		return friend;
	}
	public void setFriend(PlayerFriend friend) {
		this.friend = friend;
	}
	public byte getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(byte isOnline) {
		this.isOnline = isOnline;
	}
}
