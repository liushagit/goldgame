package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.AppConfig;

public interface AppConfigAction {
//	List<AppConfig> findAllAppConfig();

//	List<AppConfig> findAppConfigs();
	
	AppConfig findAppConfigByKey(String key);
	
	List<AppConfig> quaryAllAppConfigs();
}
