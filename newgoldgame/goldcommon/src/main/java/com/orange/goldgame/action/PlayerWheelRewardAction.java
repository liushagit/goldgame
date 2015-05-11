package com.orange.goldgame.action;

import com.orange.goldgame.domain.PlayerWheelReward;

public interface PlayerWheelRewardAction {

	PlayerWheelReward quaryPlayerWheelReward(int playerId);
	
	void updatePlayerWheelReward(PlayerWheelReward pwr);
	
	int insertPlayerWheelReward(PlayerWheelReward pwr);
	
}
