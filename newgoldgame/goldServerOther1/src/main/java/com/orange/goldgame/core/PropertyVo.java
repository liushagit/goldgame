package com.orange.goldgame.core;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.util.IOutputMeasageVo;

public class PropertyVo implements IOutputMeasageVo{
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
		length = 4*7;
		return length;
	}
	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage();
		if (player != null) {
			msg.putInt(player.getId());
			msg.putInt(player.getGolds());
			PlayerProps pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.exchange_prop);
			msg.putInt(pp == null?0:pp.getNumber());
			pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.change_card);
			msg.putInt(pp == null?0:pp.getNumber());
			pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.four_card);
			msg.putInt(pp == null?0:pp.getNumber());
			pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.eight_card);
			msg.putInt(pp == null?0:pp.getNumber());
			pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.band_card);
			msg.putInt(pp == null?0:pp.getNumber());
		}else {
			msg.putInt(0);
			msg.putInt(0);
			msg.putInt(0);
			msg.putInt(0);
			msg.putInt(0);
			msg.putInt(0);
			msg.putInt(0);
		}
		
		return msg;
	}
}
