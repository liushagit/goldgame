package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;


public class ResultVo implements IOutputMeasageVo{
	private int playerId;
	private String nickname;
	/**玩家赢取的金币数*/
	private int score;
	private byte cardId1;
	private byte cardId2;
	private byte cardId3;
	private byte isLookableCards;
	private int playerOtherId;
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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

	public byte getIsLookableCards() {
		return isLookableCards;
	}

	public void setIsLookableCards(byte isLookableCards) {
		this.isLookableCards = isLookableCards;
	}

	public int getPlayerOtherId() {
		return playerOtherId;
	}

	public void setPlayerOtherId(int playerOtherId) {
		this.playerOtherId = playerOtherId;
	}

	@Override
	public int getLength() {
		int length = 0;
		length = 4*3 + 4 + 2 + nickname.getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage(getLength());
		msg.putInt(playerId);
		msg.putString(nickname);
		msg.putInt(score);
		msg.putByte(cardId1);
		msg.putByte(cardId2);
		msg.putByte(cardId3);
		msg.putByte(isLookableCards);
		msg.putInt(playerOtherId);
		return null;
	}

}
