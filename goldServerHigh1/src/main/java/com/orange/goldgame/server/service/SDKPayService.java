package com.orange.goldgame.server.service;

import java.util.Map;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.util.DateUtil;
import com.orange.goldgame.vo.ActivityConfigVo;

public class SDKPayService {

	private static final Logger log = LoggerFactory.getLogger(SDKPayService.class);
	public static ErrorCode pay(String imei,  String pay_id , String info, String orderid){
		//1、获取玩家
//		Player player = BaseEngine.getInstace().getPlayerActionIpml().loadPlayerByCode(imei);
//	    if (player == null) {
//	    	log.error("player is null imei is :" + imei);
//	    	return new ErrorCode(-1);
//		}
//	    Player cachPlayer = PlayerService.getSimplePlayerByPlayerId(player.getId());
//	    
//	    //2、获取支付信息
//	    PayInfo pi = getPayInfo(pay_id);
//	    
//	    //3、支付
//	    PlayerPay playerPay = new PlayerPay();
//        playerPay.setOrderId(orderid);
//        playerPay.setPayTime(new Date());
//        playerPay.setPlayerId(cachPlayer.getId());
//        playerPay.setPayInfoId(pi.getPayId());
//        playerPay.setGold(pi.getMoney());
//        playerPay.setPayType(pi.getPayType()+"");
//        
//        
//        PlayerPay pp = BaseEngine.getInstace().getPlayerPayAction().quaryPlayerPay(playerPay.getPlayerId(), playerPay.getOrderId());
//		if (pp != null) {
//			return new ErrorCode(-2);
//		}else {
//			int result = 0;
//			int times = 0;
//			do {
//				result = BaseEngine.getInstace().getPlayerPayAction().insertPlayerPay(playerPay);
//				times ++;
//			} while (result < 0 && times < 3);
//		}
//		addGolds(cachPlayer, playerPay);
//		
//		//添加信息
//		PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(), true,Constants.PAY,"","充值成功，您购买了"+playerPay.getGold()+"宝石！");
//       
//		// 检查活动
//		checkActivity(player, playerPay);
//		
//    	GameSession session = SessionManger.getInstance().getSession(cachPlayer.getId());
//		if (session != null) {
//			BaseServer.sendErrorMsg(session, (short)1, "充值成功");
//		}
		return new ErrorCode(ErrorCode.SUCC);
	}
	
	private static void checkActivity(Player player,PlayerPay playerPay){
		if (DateUtil.isInDateRange(ActivityService.beginTime, ActivityService.endTime)) {
			PayInfo pi = getPayInfo(playerPay.getPayInfoId());
			if (pi != null && ActivityService.getActivityVos().containsKey(pi.getMoney())) {
				ActivityConfigVo vo = ActivityService.getActivityVos().get(pi.getMoney());
				GoldService.addCopper(player, vo.getGolds());
				StringBuffer sb = new StringBuffer();
				sb.append("充值成功，赠送" + vo.getGolds() + "金币");
				if (vo.getProps() != null) {
					PropsConfig pc = null;
					for (Integer propid : vo.getProps().keySet()) {
						pc = ResourceCache.getInstance().getPropsConfigs().get(propid);
						if (pc != null) {
							PropCacheAction.addProp(player, pc.getId(), vo.getProps().get(propid));
							sb.append("，").append(pc.getName()).append("+").append(vo.getProps().get(propid));
						}
					}
				}
				String msg = sb.toString();
				PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(player.getId(), false, Constants.PAY, "",msg);
				GameSession session = SessionManger.getInstance().getSession(player.getId());
		        if (session != null) {
		        	PlayerInfoCenterManager.getInstance().noticePlayerInfoCenter(session);
				}
			}
		}
	}
	
	private static void addGolds(Player player , PlayerPay pay){
		PayInfo pi = isExistPayInfo(pay);
		if(pi != null){
	        pay.setGold(pi.getGold());
	    }
//	    PayInfo pi = getPayInfo(pay.getPayInfoId());
		GoldService.addGold(player, pi.getGold());
		
	}
	
	private static PayInfo isExistPayInfo(PlayerPay info){
		return getPayInfo(info.getPayInfoId());
	}
	private static PayInfo getPayInfo(String payId){
		PayInfo payInfo = null;
		Map<Integer, Map<Integer, Map<Integer, PayInfo>>> payinfos = ResourceCache.getInstance().getPayinfos();
        for (Map<Integer, Map<Integer, PayInfo>> map : payinfos.values()) {
        	for (Map<Integer, PayInfo> map2 : map.values()) {
				for (PayInfo pi : map2.values()) {
					if (pi.getPayId().equals(payId)) {
						payInfo = pi;
						break;
					}
					
				}
			}
        }
        return payInfo;
	}
}
