package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;


public class RaceVo implements IOutputMeasageVo{
	private String nickname; 
	private int raceNumber;
	private int exchangeVoucher;
	private int golds;
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
	@Override
	public int getLength() {
		int length = 0;
		length = 4*3 + 2 + nickname.getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage outputMsg = new OutputMessage(getLength());
		outputMsg.putString(nickname);
		outputMsg.putInt(raceNumber);
		outputMsg.putInt(exchangeVoucher);
		outputMsg.putInt(golds);
		return outputMsg;
	}
	
}
