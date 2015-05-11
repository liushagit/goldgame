/**
 * SuperStarCommon
 * com.orange.superstar.domain
 * Friend.java
 */
package com.orange.goldgame.vo;

import java.io.Serializable;

/**
 * @author shaojieque 
 * 2013-5-6
 */
public class Friend implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private int id;
	private int userId;
	private int friendId;
	private String friendName;
	private String friendLogo;
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getFriendLogo() {
		return friendLogo;
	}

	public void setFriendLogo(String friendLogo) {
		this.friendLogo = friendLogo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Friend [id=" + id + ", userId=" + userId + ", friendId="
				+ friendId + ", friendName=" + friendName + ", friendLogo="
				+ friendLogo + ", status=" + status + "]";
	}
}
