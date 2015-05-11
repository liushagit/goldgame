/**
 * SuperStarCommon
 * com.orange.superstar.domain
 * UserRank.java
 */
package com.orange.goldgame.vo;

import java.io.Serializable;

/**
 * @author shaojieque 2013-5-13
 */
public class UserRank implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private int id;
	private int userId;
	private String userName;
	private String userLogo;
	private int userExp;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLogo() {
		return userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	public int getUserExp() {
		return userExp;
	}

	public void setUserExp(int userExp) {
		this.userExp = userExp;
	}

	@Override
	public String toString() {
		return "UserRank [id=" + id + ", userId=" + userId + ", userName="
				+ userName + ", userLogo=" + userLogo + ", userExp=" + userExp
				+ "]";
	}
}
