package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;

public class MeiliVo implements IOutputMeasageVo{
	private String nickname; 
	private int giftNumber;
	private int giftWealth;
	
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

	
	@Override
	public int getLength() {
		int length = 0;
		length = 4*2 + 2 + nickname.getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage outputMsg = new OutputMessage(getLength());
		outputMsg.putString(nickname);
		outputMsg.putInt(giftNumber);
		outputMsg.putInt(giftWealth);
		return outputMsg;
	}

}
