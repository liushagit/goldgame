package com.orange.model;

import java.util.List;

/**
 * 用户缓存
 * @author hqch
 *
 */
public class UserCache {

	private String sessionID;
	
	private int userID;
	
	/**连接的目标服务器*/
	private List<String> connServerList;

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public List<String> getConnServerList() {
		return connServerList;
	}

	public void setConnServerList(List<String> connServerList) {
		this.connServerList = connServerList;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public byte[] getMsg(){
		//TODO
		return null;
	}
	
	@Override
	public String toString() {
		return "UserCache [sessionID=" + sessionID + ", userID=" + userID
				+ ", connServerList=" + connServerList + "]";
	}
	
}
