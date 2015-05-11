package com.orange.goldgame.core;

import java.util.Map;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.domain.Gift;
import com.orange.goldgame.util.IOutputMeasageVo;

public class GiftVo implements IOutputMeasageVo{

	private Map<Integer, Gift> gift;
	

	public Map<Integer, Gift> getGift() {
		return gift;
	}

	public void setGift(Map<Integer, Gift> gift) {
		this.gift = gift;
	}

	@Override
	public int getLength() {
		int length = 0;
		length = 4*5;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage();
		if (gift != null) {
			Gift g = gift.get(PropCacheAction.flower);
			msg.putInt(g == null ? 0:g.getAmont());
			g = gift.get(PropCacheAction.egg);
			msg.putInt(g == null ? 0:g.getAmont());
			g = gift.get(PropCacheAction.car);
			msg.putInt(g == null ? 0:g.getAmont());
			g = gift.get(PropCacheAction.house);
			msg.putInt(g == null ? 0:g.getAmont());
			g = gift.get(PropCacheAction.boat);
			msg.putInt(g == null ? 0:g.getAmont());
		}else {
			msg.putInt(0);
			msg.putInt(0);
			msg.putInt(0);
			msg.putInt(0);
			msg.putInt(0);
		}
		return msg;
	}

}
