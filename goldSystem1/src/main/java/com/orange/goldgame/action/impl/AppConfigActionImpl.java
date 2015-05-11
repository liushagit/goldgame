package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.AppConfigAction;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.system.DBManager;

public class AppConfigActionImpl implements AppConfigAction{

	@Override
	public List<AppConfig> quaryAllAppConfigs() {
		return DBManager.quaryAllAppConfig();
	}

	@Override
	public AppConfig findAppConfigByKey(String key) {
		return DBManager.loadAppConfigByKey(key);
	}

}
