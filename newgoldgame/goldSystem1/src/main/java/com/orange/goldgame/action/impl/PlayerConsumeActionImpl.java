package com.orange.goldgame.action.impl;

import java.util.Date;
import java.util.List;

import com.orange.goldgame.action.PlayerConsumeAction;
import com.orange.goldgame.domain.PlayerConsume;
import com.orange.goldgame.system.DBManager;

public class PlayerConsumeActionImpl implements PlayerConsumeAction {

	@Override
	public void addPlayerConsume(PlayerConsume record) {
		DBManager.insertPlayerConsume(record);;
	}

	@Override
	public List<PlayerConsume> getPlayerConsumes(int playerId,byte consumeType,Date start,Date end) {
		return DBManager.queryPlayerConsumes(playerId, consumeType, start, end);
	}

}
