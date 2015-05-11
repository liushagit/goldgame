package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.ApplicationAction;
import com.orange.goldgame.domain.AppVersion;
import com.orange.goldgame.system.DBManager;

/**
 * @author wuruihuang 2013.7.5
 * ApplicationActionImpl --->调用dao
 */
public class ApplicationActionImpl implements ApplicationAction{

	@Override
	public boolean checkVersion(String appId, String appVersion) {
		AppVersion app = DBManager.getApplicationById(appId);
		if(app.getVersion().equals(appVersion)){
			return true;
		}
		return false;
	}

	@Override
	public AppVersion getAppInfoById(String appId) {
		return DBManager.getApplicationById(appId);
	}

	@Override
	public List<AppVersion> getAllAppVersions() {
		return DBManager.loadAllAppVersiona();
	}
}
