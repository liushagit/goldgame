package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.util.IOutputMeasageVo;


public class PlayerIntoRoomVo implements IOutputMeasageVo{

	private Player player;
	/**玩家玩牌的状态*/
	private byte playerCardState;
	/**玩家玩牌的结果状态*/
	private byte playerCardResultState;
	private int jeton;
	private int golds;
	private int gemstone;
	private byte cardId1;
	private byte cardId2;
	private byte cardId3;
	public int getGemstone() {
		return gemstone;
	}

	public void setGemstone(int gemstone) {
		this.gemstone = gemstone;
	}
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	public byte getPlayerCardState() {
		return playerCardState;
	}

	public void setPlayerCardState(byte playerCardState) {
		this.playerCardState = playerCardState;
	}

	public byte getPlayerCardResultState() {
		return playerCardResultState;
	}

	public void setPlayerCardResultState(byte playerCardResultState) {
		this.playerCardResultState = playerCardResultState;
	}

	public int getJeton() {
		return jeton;
	}

	public void setJeton(int jeton) {
		this.jeton = jeton;
	}

	public int getGolds() {
		return golds;
	}

	public void setGolds(int golds) {
		this.golds = golds;
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
		int  length = 4*4+ 1*5+ 2*2+player.getNickname().getBytes().length+player.getAppellation().getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage();
		msg.putInt(player.getId());
		msg.putByte(playerCardState);
		msg.putByte(playerCardResultState);
		msg.putInt(jeton);
		msg.putString(player.getNickname());
		msg.putString(player.getAppellation());
		msg.putInt(golds);
		msg.putInt(gemstone);
		msg.putByte(cardId1);
		msg.putByte(cardId2);
		msg.putByte(cardId3);
		return msg;
	}
	
}
