package com.orange.goldgame.admin.command;

import org.json.JSONException;
import org.json.JSONObject;

import com.orange.goldgame.domain.ServerConfig;



public abstract class AdminCmd {
	protected AdminRequest adminReq;
	public abstract boolean isResponsible(AdminRequest req);
	public abstract JSONObject process(ServerConfig serConfig) throws JSONException;
	
}
