package com.orange.goldgame.server.engine;

import org.apache.log4j.Logger;

import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.AppVersion;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.server.manager.SessionManger;

public class LoginHandlerEngine {

	private static final Logger log = LoggerFactory.getLogger(LoginHandlerEngine.class);
	public static void loginHandler(SocketRequest request,SocketResponse response,String nickname,
			String machineCode, String appId, String appVersion,
			int sessionNumber,String app_channel) {
		//检测版本是否是最新的版本
		AppVersion appInfo = BaseEngine.getInstace().getApplicationAction().getAppInfoById(appId);
		if (appInfo == null) {
			log.error("no app_info|" + appId);
			throw new GoldException("appinfo error");
		}
		if(Integer.parseInt(appVersion) >= Integer.parseInt(appInfo.getVersion())){
			Player player = BaseEngine.getInstace().getPlayerActionIpml().loadPlayerByCode(machineCode);
			if(player==null){
				player = BaseEngine.getInstace().getPlayerActionIpml().createPlayer(nickname,machineCode,app_channel);
			}
			if (app_channel != null && !app_channel.equals(player.getChannelId())) {
				player.setChannelId(app_channel);
				BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
			}
			if(player.getStatus() != Constants.PLAYER_STATUS_SEAL){
			    player.setIslogin(1);
			    player.setLoginTime(System.currentTimeMillis());
			    BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
			}else{
			    return;
			}
			
			SessionManger.getInstance().putSession(player.getId(), request.getSession());
			request.getSession().put(Constants.PLAYER_KEY, player);
			
			LoginResponseEngine.LoginableResponse(response,player,sessionNumber);
		}else {
			LoginResponseEngine.responseUpdateInfo(response,appInfo);
		}		
	}

}
