/**
 * SuperStarCommon
 * com.orange.superstar.login.domain
 * User.java
 */
package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.util.IOutputMeasageVo;

/**
 * @author	wuruihuang  2013.5.21
 */
public class PlayerVo implements IOutputMeasageVo{
	private Player player;
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	@Override
	public int getLength() {
		int length = 0;
		length = 4*3 + 2+player.getNickname().getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage outputMsg = new OutputMessage();
		outputMsg.putInt(player.getId());
		outputMsg.putString(player.getNickname());
		outputMsg.putInt(1000);
		outputMsg.putInt(player.getDays());
		return outputMsg;
	}
}
