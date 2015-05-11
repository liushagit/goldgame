package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;


public class CardVo implements IOutputMeasageVo{
	private int playerId;
	private byte cardId1; 
	private byte cardId2;
	private byte cardId3;
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public byte getCardId1() {
		return cardId1;
	}

	public void setCardId1(byte cardId1) {
		this.cardId1 = cardId1;
	}

	public byte getCardId2() {
		return cardId2;
	}

	public void setCardId2(byte cardId2) {
		this.cardId2 = cardId2;
	}

	public byte getCardId3() {
		return cardId3;
	}

	public void setCardId3(byte cardId3) {
		this.cardId3 = cardId3;
	}
	
	@Override
	public int getLength() {
		int length = 0;
		length = 4 + 3;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage(getLength());
		msg.putInt(playerId);
		msg.putByte(cardId1);
		msg.putByte(cardId2);
		msg.putByte(cardId3);
		return msg;
	}

}
