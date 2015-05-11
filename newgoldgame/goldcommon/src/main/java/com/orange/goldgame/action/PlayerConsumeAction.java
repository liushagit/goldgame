package com.orange.goldgame.action;

import java.util.Date;
import java.util.List;

import com.orange.goldgame.domain.PlayerConsume;

public interface PlayerConsumeAction {
	
	public void addPlayerConsume(PlayerConsume record);
	
	public List<PlayerConsume> getPlayerConsumes(int playerId,byte consumeType,Date start,Date end);

}
