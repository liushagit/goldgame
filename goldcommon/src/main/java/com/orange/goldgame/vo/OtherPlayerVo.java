package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.util.IOutputMeasageVo;

public class OtherPlayerVo implements IOutputMeasageVo{

	private Player player;
	private int gemstone;
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
	@Override
	public int getLength() {
		int length = 0;
		length = 4*4 + 2*3 
				+ player.getNickname().getBytes().length 
				+ player.getAppellation().getBytes().length 
				+ player.getTag().getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage outputMsg = new OutputMessage();
		outputMsg.putInt(player.getId());
		outputMsg.putInt(player.getId());
		outputMsg.putString(player.getNickname());
		outputMsg.putString(player.getAppellation());
		outputMsg.putInt(player.getWins());
		outputMsg.putInt(player.getLoses());
		outputMsg.putInt(gemstone);
		outputMsg.putString(player.getTag());
		return outputMsg;
	}
}
