package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.StakeConfig;

public interface StakeConfigAction {
	StakeConfig getStakeConfigByStakeId(int stakeId);
	List<StakeConfig> getStakeConfigByAreaId(int areaId);
	List<StakeConfig> getAllStakeConfig();
}
