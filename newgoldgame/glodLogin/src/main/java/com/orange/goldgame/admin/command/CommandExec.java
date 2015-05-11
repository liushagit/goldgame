package com.orange.goldgame.admin.command;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.orange.goldgame.domain.ServerConfig;


public class CommandExec {

	public static JSONObject exec(String cmd,ServerConfig serverConfig){
		List<AdminCmd> chain = AdminCmdSet.getInstall().getChain();
		AdminRequest req = new AdminRequest(cmd);
		for (AdminCmd c : chain) {
			if (c.isResponsible(req)) {
				try {
					return c.process(serverConfig);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
