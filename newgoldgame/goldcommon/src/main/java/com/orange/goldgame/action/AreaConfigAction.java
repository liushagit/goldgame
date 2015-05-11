package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.AreaConfig;

public interface AreaConfigAction {
	List<AreaConfig> quaryAllAreaConfigs();
	AreaConfig quaryAreaConfigById(int areaConfigId);
	/**areaType代表普通场和竞技场,areaClasse代表初中高的分类*/
	AreaConfig quaryAreaConfigByTowId(int areaTypeId,int areaClassId);
}
