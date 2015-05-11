package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.ServerConfigAction;
import com.orange.goldgame.domain.ServerConfig;
import com.orange.goldgame.system.DBManager;

public class ServerConfigActionImpl implements ServerConfigAction{

	@Override
	public List<ServerConfig> quaryAllServerConfigs() {
		return DBManager.quaryAllServerConfigs();
	}

}
