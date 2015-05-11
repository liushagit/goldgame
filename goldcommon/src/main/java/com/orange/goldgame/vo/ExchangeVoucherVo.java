package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;

public class ExchangeVoucherVo implements IOutputMeasageVo{
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

	@Override
	public int getLength() {
		int length = 0;
		length = 4 + 2 + nickname.getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage message = new OutputMessage(getLength());
		message.putString(nickname);
		message.putInt(exchangeVoucher);
		return message;
	}

}
