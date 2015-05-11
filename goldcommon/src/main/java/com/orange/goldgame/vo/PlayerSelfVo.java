package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.util.IOutputMeasageVo;

public class PlayerSelfVo implements IOutputMeasageVo{
	private Player player;
	private int gold;
	
	public void setGold(int gold) {
		this.gold = gold;
	}

	private byte sex;
	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	@Override
	public int getLength() {
		int length = 0;
		length = 4*5 + 1 + 2*4 
				+ player.getNickname().getBytes().length
				+ player.getAccount().getBytes().length
				+ player.getAppellation().getBytes().length
				+ player.getTag().getBytes().length
				+ player.getConnect().getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage outputMsg = new OutputMessage();
		outputMsg.putInt(player.getId());
		outputMsg.putString(player.getNickname());
		outputMsg.putInt(gold);
		outputMsg.putString(player.getAccount());
		outputMsg.putByte((byte)player.getSex().intValue());
		outputMsg.putByte(Byte.parseByte(player.getHeadImage()));
		outputMsg.putString(player.getAppellation());
		outputMsg.putString(player.getTag());
		outputMsg.putInt(player.getWins());
		outputMsg.putInt(player.getLoses());
		return outputMsg;
	}
}
