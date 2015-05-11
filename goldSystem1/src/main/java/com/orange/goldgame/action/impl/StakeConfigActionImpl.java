package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.StakeConfigAction;
import com.orange.goldgame.domain.StakeConfig;
import com.orange.goldgame.system.DBManager;

public class StakeConfigActionImpl implements StakeConfigAction{

	@Override
	public StakeConfig getStakeConfigByStakeId(int stakeId) {
		return DBManager.loadStakeConfigByStakeId(stakeId);
	}

	@Override
	public List<StakeConfig> getStakeConfigByAreaId(int areaId) {
		return DBManager.loadStakeConfigByAreaId(areaId);
	}

	@Override
	public List<StakeConfig> getAllStakeConfig() {
		return DBManager.loadAllStakeConfig();
	}

}
