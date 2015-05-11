package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.PropsAction;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.system.DBManager;

public class PropsActionImpl implements PropsAction {
	@Override
	public void insertPlayerProps(int playerid) {
		List<PropsConfig> list = getAllPropsConfig();
		for(PropsConfig pc :list){
			PlayerProps pp = new PlayerProps();
			pp.setPlayerId(playerid);
			pp.setPropsConfigId(pc.getId());
			pp.setNumber(0);
			DBManager.insertPlayerProps(pp);
		}
	}
	
	@Override
	public List<PropsConfig> getAllPropsConfig() {
		return DBManager.loadAllPropsConfig();
	}

	@Override
	public List<PropsConfig> queryPropsByType(int type) {
		return DBManager.queryPropsByType(type);
	}

	@Override
	public PackageProps queryPackagePropsById(int propsConfigId) {
		return DBManager.queryPackagePropsById(propsConfigId);
	}
	
	@Override
	public List<PackageProps> queryAllPackageProp() {
		return DBManager.loadAllPackageProps();
	}

	@Override
	public PropsConfig queryPropsById(int propsConfigId) {
		return DBManager.loadPropsConfigById(propsConfigId);
	}

	@Override
	public void updatePropsNumberByTowId(int playerId, int propsId,int propsNumber) {
		DBManager.modifyPropsNumberByTowId(playerId,propsId,propsNumber);
	}

}
