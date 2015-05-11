package com.orange.goldgame.server.lover;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juice.orange.game.cached.MemcachedResource;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;
import com.orange.goldgame.server.service.FriendService;
import com.orange.goldgame.server.service.GoldService;

public class LoverService {

	/**
	 * 添加情人
	 * @param player
	 * @param lover
	 * @return
	 */
	public static ErrorCode addLover(Player player,Player lover){
		//1、检查必要条件
		ErrorCode res = checkAddLover(player, lover);
		if (res.getStatus() != ErrorCode.SUCC) {
			return res;
		}
		res = checkAddLover(lover, player);
		if (res.getStatus() != ErrorCode.SUCC) {
			return res;
		}
		//2、扣除添加情人所需要道具以及金币
		int need_copper = getNeedCopper();
		if (!GoldService.consumeInCopper(player, need_copper)) {
			return new ErrorCode(-1,"添加情人失败，金币不足");
		}
		//3、添加情人
		PlayerFriend playerLover = creaatePlayerLover(player, lover);
		
		if (playerLover == null) {
			return new ErrorCode(-1,"添加情人失败");
		}
		Map<Integer, PlayerFriend> playerLovers = player.getLoverMap();
		playerLovers.put(playerLover.getFriendId(), playerLover);
		return new ErrorCode(ErrorCode.SUCC,playerLovers);
	}
	
	/**
	 * 检查是否能够添加情人
	 * @param player
	 * @param lover
	 * @return
	 */
	private static ErrorCode checkAddLover(Player player,Player lover){
		if (player == null || lover == null) {
			return new ErrorCode(-1, "情人不存在，请重新选择！");
		}
		//1、金币是否足够
//		int need_copper = getNeedCopper();
//		if (player.getCopper() < need_copper) {
//			return new ErrorCode(-1, "金币不足" + need_copper + "！");
//		}
		//2、已经在改玩家列表中
		Map<Integer, PlayerFriend> playerLovers = player.getLoverMap();
		if (playerLovers == null) {
			playerLovers = loadPlayerLover(player);
		}
		if (playerLovers.containsKey(lover.getId())) {
			PlayerFriend pf = playerLovers.get(lover.getId());
			if (pf.getFriendType().intValue() == FriendService.LOVERTYPE) {
				if (pf.getFriendStatus() != 1) {
					return new ErrorCode(-1, "情人请求未处理，不能重复加情人！");
				}
				return new ErrorCode(-1, "情人已存在，不需要添加了！");
			}
		}
		return new ErrorCode(ErrorCode.SUCC);
	}
	
	private static int getNeedCopper(){
		AppConfig ac = ResourceCache.getInstance().getAppConfigs().get(Constants.ADD_LOVER);
		if (ac != null) {
			return Integer.parseInt(ac.getAppValue());
		}
		return 100000;
	}
	
	public static Map<Integer, PlayerFriend> loadPlayerLover(Player player){
		List<PlayerFriend> lovers = BaseEngine.getInstace().getFriendActionIpml().getFriendsList(player.getId());
		Map<Integer, PlayerFriend> playerlovers = new HashMap<Integer, PlayerFriend>();
		if (lovers != null) {
			for (PlayerFriend pl : lovers) {
				playerlovers.put(pl.getFriendId(), pl);
			}
		}
		player.setFriendMap(playerlovers);
		return playerlovers;
	}
	
	private static PlayerFriend creaatePlayerLover(Player player,Player lover){
		return FriendService.addFriend(player, lover, FriendService.LOVERTYPE);
	}
	
	/**
	 * 同意添加情人请求
	 * @param player
	 * @param lover
	 * @return
	 */
	public static ErrorCode agreeLover(Player player,Player lover){
		//1、检查是否能正常同意
		ErrorCode res = checkAgreeLover(player, lover);
		if (res.getStatus() != ErrorCode.SUCC) {
			return res;
		}
		PlayerFriend pFriend = (PlayerFriend) res.getObject();
		//2、为自己添加一条记录
		PlayerFriend pf = FriendService.addFriend(player, lover, FriendService.LOVERTYPE,0);
		if (pf == null) {
			return new ErrorCode(-1,"添加情人失败");
		}
		//3、更新对方记录
		pFriend.setFriendStatus(FriendService.LOVERSTATUSSUCC);
		BaseEngine.getInstace().getFriendActionIpml().updateFriend(pFriend);
		
		//4、通知对方添加成功
		PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(lover.getId(), true,Constants.DEFALUT_MESSAGE,"",player.getNickname() + "答应您的请求，您与"+player.getNickname()+"成为了情人！");
		
		return new ErrorCode(ErrorCode.SUCC, pf);
	}
	/**
	 * 检查是否能正常同意
	 * @param player
	 * @param lover
	 * @return
	 */
	private static ErrorCode checkAgreeLover(Player player,Player lover){
		if (player == null || lover == null) {
			return new ErrorCode(-1, "添加情人不存在！");
		}
		//1、玩家是否正在进行申请
		Map<Integer,PlayerFriend> friendMap = lover.getLoverMap();
		PlayerFriend pFried = null;
		if (friendMap != null) {
			pFried = friendMap.get(player.getId());
		}
		if (pFried == null) {
			friendMap = FriendService.getPlayerFriendByPlayer(lover,true,FriendService.LOVERTYPE);
		}
		pFried = friendMap.get(player.getId());
		if (pFried == null || pFried.getFriendType() != FriendService.LOVERTYPE) {
			return new ErrorCode(-1, "添加情人不存在！");
		}
		
		
		Map<Integer,PlayerFriend> friendMapself = player.getLoverMap();
		PlayerFriend pFriedself = null;
		if (friendMapself != null) {
			pFriedself = friendMapself.get(lover.getId());
		}
		if (pFriedself == null) {
			friendMapself = FriendService.getPlayerFriendByPlayer(player,true,FriendService.LOVERTYPE);
		}
		pFriedself = friendMapself.get(lover.getId());
		
		if (pFriedself != null && pFriedself.getFriendType() == FriendService.LOVERTYPE) {
			return new ErrorCode(-1, "情人已经存在不需要添加了");
		}
		
		return new ErrorCode(ErrorCode.SUCC, pFried);
	}
	/**
	 * 拒绝添加情人请求
	 * @param player
	 * @param lover
	 * @return
	 */
	public static ErrorCode refuseLover(Player player,Player lover){
		// 1、删除对方记录
		deleteLover(lover, player, true,true);
		// 2、通知对方被拒绝
		PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(lover.getId(), true,Constants.DEFALUT_MESSAGE,"",player.getNickname() + "拒绝您的请求，请继续努力");
		
		return new ErrorCode(ErrorCode.SUCC);
	}
	
	/**
	 * 删除情人
	 * @param player
	 * @param lover
	 * @param deleteSelf
	 * @return
	 */
	public static ErrorCode deleteLover(Player player,Player lover,boolean deleteSelf,boolean refuse){
		//删除双方记录
		// 1、删除自己记录
		Map<Integer,PlayerFriend> friendMap = player.getLoverMap();
		PlayerFriend pFried = null;
		if (friendMap != null) {
			pFried = friendMap.get(lover.getId());
		}
		if (pFried == null) {
			friendMap = FriendService.getPlayerFriendByPlayer(player,true,FriendService.LOVERTYPE);
		}
		pFried = friendMap.get(lover.getId());
		if (refuse && pFried != null && pFried.getFriendStatus() == FriendService.LOVERSTATUSSUCC) {
			return new ErrorCode(-1,"已经同意了，不能再拒绝了！");
		}
		if (pFried != null) {
			friendMap.remove(lover.getId());
			BaseEngine.getInstace().getFriendActionIpml().deleteFriend(pFried);
		}
		if (pFried != null) {
			String key = getKey(pFried);
			MemcachedResource.save(key, "", 1);
		}
		// 2、删除对方记录
		if (deleteSelf) {
			friendMap = lover.getLoverMap();
			pFried = null;
			if (friendMap != null) {
				pFried = friendMap.get(player.getId());
			}
			if (pFried == null) {
				friendMap = FriendService.getPlayerFriendByPlayer(lover,true,FriendService.LOVERTYPE);
			}
			pFried = friendMap.get(player.getId());
			if (pFried != null) {
				friendMap.remove(player.getId());
				BaseEngine.getInstace().getFriendActionIpml().deleteFriend(pFried);
			}
			if (pFried != null) {
				String key = getKey(pFried);
				MemcachedResource.save(key, "", 1);
			}
		}
		if (pFried != null) {
			MemcachedResource.save(getIntimacyKey(pFried), "0", 1);
		}
		return new ErrorCode(ErrorCode.SUCC);
	}
	
	public static void addGift(Player send , Player receive,int giftId){
		FriendService.getPlayerFriendByPlayer(send, true, FriendService.LOVERTYPE);
		PlayerFriend pFriend = send.getLoverMap().get(receive.getId());
		if (pFriend == null || pFriend.getFriendStatus() != FriendService.LOVERSTATUSSUCC) {
			return;
		}
		FriendService.getPlayerFriendByPlayer(receive, true, FriendService.LOVERTYPE);
		PlayerFriend lFriend = receive.getLoverMap().get(send.getId());
		if (lFriend == null || lFriend.getFriendStatus() != FriendService.LOVERSTATUSSUCC) {
			return;
		}
		String key = getKey(lFriend);
		Object o = MemcachedResource.get(key);
		String loverGifts = lFriend.getLoverGifts();
		if (o != null) {
			loverGifts = o.toString();
		}
		Map<Integer, Integer> giftsMap = new HashMap<Integer, Integer>();
		String gifts[];
		if (loverGifts.length() > 0) {
			gifts = loverGifts.split("\\|");
			String giftIdNum[];
			for (int i = 0; i < gifts.length; i++) {
				giftIdNum = gifts[i].split("_");
				giftsMap.put(Integer.parseInt(giftIdNum[0]), Integer.parseInt(giftIdNum[1]));
			}
		}
		
		if (giftsMap.containsKey(giftId)) {
			giftsMap.put(giftId, giftsMap.get(giftId) + 1);
		}else {
			giftsMap.put(giftId, 1);
		}
		
		StringBuffer sb = new StringBuffer();
		for (int ids : giftsMap.keySet()) {
			sb.append(ids).append("_").append(giftsMap.get(ids)).append("|");
		}
		MemcachedResource.save(key, sb.toString(), 15 * 24 * 60 * 60);
		lFriend.setLoverGifts(sb.toString());
		PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(giftId);
		pFriend.setLoverIntimacy(pFriend.getLoverIntimacy() + pc.getCopper() / 1000);
		lFriend.setLoverIntimacy(lFriend.getLoverIntimacy() + pc.getCopper() / 1000);
		MemcachedResource.save(LoverService.getIntimacyKey(pFriend), pFriend.getLoverIntimacy() + "", 24 * 60 * 60);
		BaseEngine.getInstace().getFriendActionIpml().updateFriend(lFriend);
	}
	
	/**
	 * @param pFriend
	 * @return
	 */
	public static String getIntimacyKey(PlayerFriend pf){
		if (pf.getPlayerId() > pf.getFriendId()) {
			return "lover_intimacy_" + pf.getFriendId() + "_" + pf.getPlayerId();
		}else {
			return "lover_intimacy_" + pf.getPlayerId() + "_" + pf.getFriendId();
		}
	}

	public static String getKey(PlayerFriend pf){
		return "lover_gift_" + pf.getPlayerId() + "_" + pf.getFriendId();
	}
}
