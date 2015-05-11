package com.orange.goldgame.action.impl;

import java.util.Date;

import com.orange.goldgame.action.LogAction;
import com.orange.goldgame.system.Application;

public class LogActionImpl implements
		LogAction {
	@Override
	public boolean addLog(int playerId, int goodsId, String cellphone) {
		StringBuilder stringType = new StringBuilder();
		stringType.append(playerId);
		stringType.append(goodsId);
		stringType.append(cellphone);
		Application.getLogAO().add(playerId, stringType.toString(), new Date());
		return true;
	}

}
