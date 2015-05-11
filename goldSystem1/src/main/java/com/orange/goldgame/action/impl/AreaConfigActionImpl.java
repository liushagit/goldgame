package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.AreaConfigAction;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.system.DBManager;

public class AreaConfigActionImpl implements AreaConfigAction{

	@Override
	public List<AreaConfig> quaryAllAreaConfigs() {
		return DBManager.quaryAllAreaConfigs();
	}

	@Override
	public AreaConfig quaryAreaConfigById(int areaConfigId) {
		return DBManager.findAreaConfigById(areaConfigId);
	}

	@Override
	public AreaConfig quaryAreaConfigByTowId(int areaTypeId, int areaClassId) {
		return DBManager.loadAreaConfigByTowId(areaTypeId,areaClassId);
	}

}
