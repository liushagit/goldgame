package com.orange.goldgame.server.engine;

import java.util.List;
import java.util.Map;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.ActivityConfig;
import com.orange.goldgame.domain.AppVersion;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.GetOnce;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.manager.DailyPrizeManager;
import com.orange.goldgame.server.manager.GetOnceManager;
import com.orange.goldgame.server.manager.NoticeManager;
import com.orange.goldgame.vo.PlayerVo;

public class LoginResponseEngine{

	public static void LoginableResponse(SocketResponse response, Player player, int onlineNumber) {
		byte isLoginable = 1;
		//获取用户信息
		PlayerVo playerInfo = new PlayerVo();
		playerInfo.setPlayer(player);
		
		List<AreaConfig> areaList = BaseEngine.getInstace().getAreaConfigAction().quaryAllAreaConfigs();
		short areaSize = (short) areaList.size();
		
		OutputMessage om = new OutputMessage();
		om.putByte(isLoginable);
		om.putInt(onlineNumber);
		om.putInt(player.getId());
		om.putString(player.getNickname());
		om.putInt(player.getCopper());
		om.putString(player.getHeadImage());
		om.putByte(DailyPrizeManager.getInstance().isRecive(player)?(byte)1:0);
		om.putShort(areaSize);
		for (AreaConfig ac : areaList) {
			om.putShort((short)ac.getId().intValue());
			om.putShort((short)ac.getAreaType().intValue());
			om.putShort((short)ac.getAreaClass().intValue());
			om.putInt(ac.getLimitGolds());
			om.putInt(ac.getSingleGolds());
			String string = ac.getAword();
			String[] awords = string.split("\\|");
			int awordGolds = 0;
			int awordVouche = 0;
			for(String aw:awords){
				String[] aword = aw.split("_");
				String awordType = aword[0];
				if("1".equals(awordType)){
					awordGolds = Integer.valueOf(aword[1]);
				}else if("2".equals(awordType)){
					awordVouche = Integer.valueOf(aword[1]);
				}
			}
			om.putInt(awordGolds);
			om.putInt(awordVouche);
		}
		
		//是否是第一次登陆
		GetOnce getOnce = GetOnceManager.getPlayerGetOnce(player,Constants.FIRST_LOGIN);
		if (GetOnceManager.checkToday(getOnce, 2)) {
			om.putByte((byte)1);
			GetOnceManager.addOnceTime(getOnce, 1);
		}else {
			om.putByte((byte)0);
		}
		//是否需要显示新手引导
		if (player.getIsNewPlayer() == 1) {
			om.putByte((byte)1);
		}else {
			om.putByte((byte)0);
		}
		//是否有新活动
		Map<Integer,ActivityConfig> activityConfigs = ResourceCache.getInstance().getActivityConfigs();
		if (activityConfigs != null && activityConfigs.size() > 0) {
			om.putByte((byte)1);
		}else {
			om.putByte((byte)0);
		}
		response.sendMessage(Protocol.RESPONSE_PLAYER_LOGIN_INFO, om);
		

        //服务器返回发送公告所需要的金币数
        int needGold = NoticeManager.getInstance().getNoticeNeedGolds();
        OutputMessage om2 = new OutputMessage();
        om2.putInt(needGold);
        response.sendMessage(Protocol.RESPONSE_NOTICE_GOLES, om2);
        
        //玩家上线
        MemcachedResource.save(Constants.PLAYER_STATUS_KEY + player.getId(), Constants.PLAYER_STATUS_ONLINE, 24 * 60 * 60);
	}

	public static void responseUpdateInfo(SocketResponse response,
			AppVersion appInfo) {
		byte isLoginable = 0;
		String appUrl = appInfo.getUrl();
		String appVersion = appInfo.getVersion();
		OutputMessage outMsg = new OutputMessage();
		outMsg.putByte(isLoginable);
		outMsg.putString(appUrl);
		outMsg.putString(appVersion);
		response.sendMessage(Protocol.RESPONSE_PLAYER_LOGIN_INFO, outMsg);
	}

}
