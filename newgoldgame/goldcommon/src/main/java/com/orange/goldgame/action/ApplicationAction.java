package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.AppVersion;

public interface ApplicationAction {
	boolean checkVersion(String appId, String appVersion);

	AppVersion getAppInfoById(String appId);

	List<AppVersion> getAllAppVersions();
}
