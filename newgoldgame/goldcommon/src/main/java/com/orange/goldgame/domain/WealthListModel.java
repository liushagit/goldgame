package com.orange.goldgame.domain;

import java.io.Serializable;

/**
 * 财富榜单
 * @author Administrator
 *
 */
public class WealthListModel implements Serializable{
	private String nickname;
	private String appellation;
	private int golds;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAppellation() {
		return appellation;
	}
	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}
	public int getGolds() {
		return golds;
	}
	public void setGolds(int golds) {
		this.golds = golds;
	}
}
