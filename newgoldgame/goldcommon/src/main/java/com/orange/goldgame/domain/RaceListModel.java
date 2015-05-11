package com.orange.goldgame.domain;

import java.io.Serializable;

/**
 * 比赛榜单
 * @author hcz
 *
 */
public class RaceListModel implements Serializable{
	private String nickname;// 
	private int raceNumber; //（比赛次数）
	private int exchangeVoucher;// （兑换券）
	private int golds;// 
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getRaceNumber() {
		return raceNumber;
	}
	public void setRaceNumber(int raceNumber) {
		this.raceNumber = raceNumber;
	}
	public int getExchangeVoucher() {
		return exchangeVoucher;
	}
	public void setExchangeVoucher(int exchangeVoucher) {
		this.exchangeVoucher = exchangeVoucher;
	}
	public int getGolds() {
		return golds;
	}
	public void setGolds(int golds) {
		this.golds = golds;
	}	
}
