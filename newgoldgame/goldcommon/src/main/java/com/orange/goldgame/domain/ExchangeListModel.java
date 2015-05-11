package com.orange.goldgame.domain;
/**
 * 交换榜单
 * @author hcz
 *
 */
public class ExchangeListModel {
	private String nickname;
	private int exchangeVoucher;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getExchangeVoucher() {
		return exchangeVoucher;
	}
	public void setExchangeVoucher(int exchangeVoucher) {
		this.exchangeVoucher = exchangeVoucher;
	}
}
