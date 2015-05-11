package com.orange.goldgame.domain;

import java.io.Serializable;

/**
 * 魅力榜
 * @author Administrator
 *
 */
public class CharmListModel implements Serializable{
	private String nickname;// string
	private int giftNumber;// （礼物数量）
	private int giftWealth;// （礼物价值）
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getGiftNumber() {
		return giftNumber;
	}
	public void setGiftNumber(int giftNumber) {
		this.giftNumber = giftNumber;
	}
	public int getGiftWealth() {
		return giftWealth;
	}
	public void setGiftWealth(int giftWealth) {
		this.giftWealth = giftWealth;
	}
	
	
}
