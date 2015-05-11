package com.orange.goldgame.domain;

import java.io.Serializable;

/**
 * 好友
 * @author hcz
 *
 */
public class Friends implements Serializable{
	private int friendId;
	private String friendName;
	private int isOnline;
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
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	
}
