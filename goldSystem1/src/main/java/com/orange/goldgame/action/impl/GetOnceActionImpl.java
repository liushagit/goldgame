package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.GetOnceAction;
import com.orange.goldgame.domain.GetOnce;
import com.orange.goldgame.system.DBManager;

public class GetOnceActionImpl implements GetOnceAction{

	@Override
	public void insert(int playerId) {
		
	}

	@Override
	public GetOnce getGetOnce(int playerId, String dateKey) {
		
		return DBManager.findGetOnce(playerId,dateKey);
	}

	@Override
	public List<GetOnce> getAllGetOnce(int playerId, String dateKey) {
		return null;
	}

	@Override
	public void modify(GetOnce getOnce) {
		DBManager.updateGetOnce(getOnce);
	}

	@Override
	public void insert(GetOnce getOnce) {
		DBManager.insert(getOnce);
	}

}
