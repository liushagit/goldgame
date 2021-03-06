package com.orange.goldgame.server.engine;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.ActivityConfig;
import com.orange.goldgame.domain.AppVersion;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerActivityCpa;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.lover.LoverMessageService;
import com.orange.goldgame.server.manager.DailyPrizeManager;
import com.orange.goldgame.server.manager.NoticeManager;
import com.orange.goldgame.server.pay.PayActivityService;
import com.orange.goldgame.vo.CPAActivity;
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
		boolean isget = DailyPrizeManager.getInstance().isRecive(player);
		om.putByte(isget?(byte)0:(byte)1);
//		GetOnce getOnce = GetOnceManager.getPlayerGetOnce(player,Constants.FIRST_LOGIN);
//		if (GetOnceManager.checkToday(getOnce, 1)) {
//			om.putByte((byte)1);
//			GetOnceManager.addOnceTime(getOnce, 1);
//		}else {
//			om.putByte((byte)0);
//		}
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
		
//		MemcachedResource.save(Constants.PLAYER_LAST_TALBE + joingamer.getPlayerId(), table.getAreaId(), 10);
		try {
			Object object = MemcachedResource.get(Constants.PLAYER_LAST_TALBE + player.getId());
			int area_id = Integer.parseInt(object.toString());
			
			om.putByte((byte)1);//是否需要重连
			om.putByte((byte)(area_id > 3 ? 2 : 1));//场地类型1、普通，2、比赛
			om.putByte((byte)area_id);//场地id
		} catch (Exception e) {
			om.putByte((byte)0);//是否需要重连
			om.putByte((byte)1);//场地类型1、普通，2、比赛
			om.putByte((byte)1);//场地id
		}
		response.sendMessage(Protocol.RESPONSE_PLAYER_LOGIN_INFO, om);
		

        //服务器返回发送公告所需要的金币数
        int needGold = NoticeManager.getInstance().getNoticeNeedGolds();
        OutputMessage om2 = new OutputMessage();
        om2.putInt(needGold);
        response.sendMessage(Protocol.RESPONSE_NOTICE_GOLES, om2);
        
        //玩家上线
        MemcachedResource.save(Constants.PLAYER_STATUS_KEY + player.getId(), Constants.PLAYER_STATUS_ONLINE, 24 * 60 * 60);
        int id = LoverMessageService.checkNewMessage(player);
        if (id > 0) {
        	OutputMessage om1 = new OutputMessage();
			om1.putInt(id);
			response.sendMessage(Protocol.RESPONSE_LOVERMESSAGE_SENDER, om1);
		}
        
        ErrorCode res = PayActivityService.checkPlayerActivityCpas(player);
        if (res.getStatus() == ErrorCode.SUCC) {
        	List<PlayerActivityCpa> list = (List<PlayerActivityCpa>) res.getObject();
    		OutputMessage om1 = new OutputMessage();
    		om1.putString("活动返还宝石");
    		om1.putInt(0xffffffff);
    		om1.putInt(25);
    		om1.putShort((short)(list.size() + 1));
    		om1.putString(res.getMsg());
    		om1.putInt(0xffc5ff81);
    		om1.putInt(23);
    		StringBuffer sb = null;
    		Calendar calendar = Calendar.getInstance();
    		CPAActivity activity;
    		int mon;
    		for (PlayerActivityCpa cpa : list) {
    			sb = new StringBuffer();
    			calendar.setTime(cpa.getCreateTime());
    			sb.append("您在").append(calendar.get(Calendar.MONTH) + 1).append("月");
    			sb.append(calendar.get(Calendar.DAY_OF_MONTH)).append("日成功充值");
    			sb.append(cpa.getPayMoney()).append("元，已返还");
    			activity = PayActivityService.cpaActivityMap.get(cpa.getPayMoney());
    			mon = activity.getTodayMoney() + activity.getOtherMoney() * (cpa.getReceives() - 1);
    			sb.append(mon).append("宝石");
    			long times = System.currentTimeMillis() - cpa.getCreateTime().getTime();
				int day = (int)(times / (24 * 60 * 60 * 1000));
				mon = activity.getOtherMoney() * (activity.getAllTimes() - day - 1);
    			if (mon <= 0) {
					sb.append("，今天已全部返还！");
				}else {
					sb.append("，剩下");
					sb.append(mon).append("宝石将在接下来的").append(activity.getAllTimes() - day - 1);
					sb.append("天内每天首次登陆时返还！");
					
				}
    			om1.putString(sb.toString());
    			om1.putInt(0xff81bbff);
        		om1.putInt(15);
    		}
    		response.sendMessage(Protocol.RESPONSE_CPA_ACTIVITY, om1);
			
		}
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
		outMsg.putString("");
		response.sendMessage(Protocol.RESPONSE_PLAYER_LOGIN_INFO, outMsg);
	}

}
