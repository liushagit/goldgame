package com.orange.goldgame.server.lover;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.domain.PlayerMessage;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.BaseServer;
import com.orange.goldgame.server.service.FriendService;
import com.orange.goldgame.server.service.GiftService;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.util.DateUtil;

public class LoverServer {

	/**
	 * 获取情人聊天内容
	 * 
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getLoverMessage(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int loverId = msg.getInt();
		int index = 1;
		try {
			index = msg.getInt();
		} catch (Exception e) {
		}
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		Player lover = PlayerService.getSimplePlayerByPlayerId(loverId);
		LoverService.loadPlayerLover(player);
		PlayerFriend playerLover = player.getFriendMap().get(loverId);
		if (playerLover == null) {
			BaseServer.sendErrorMsg(request.getSession(), (short)-1, "情人不存在");
			return;
		}
		GiftService.getInstance().reloadPlayerGift(player);
		GiftService.getInstance().reloadPlayerGift(lover);
		FriendService.getPlayerFriendByPlayer(player,true,FriendService.LOVERTYPE);
		sendMessage(player, lover, playerLover,request.getSession(),index,true);
	}
	
	public void sendLoveMessage(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int loverId = msg.getInt();
		String message = msg.getUTF();
		int index = 1;
		try {
			index = msg.getInt();
		} catch (Exception e) {
		}
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		Player lover = PlayerService.getSimplePlayerByPlayerId(loverId);
		ErrorCode res = LoverMessageService.addPlayerMessage(player, lover, message);
		if (res.getStatus() != ErrorCode.SUCC) {
			BaseServer.sendErrorMsg(request.getSession(), (short)-1, res.getMsg());
			return;
		}
		PlayerFriend playerLover = player.getFriendMap().get(loverId);
		if (playerLover == null) {
			BaseServer.sendErrorMsg(request.getSession(), (short)-1, "情人不存在");
			return;
		}
		sendMessage(player, lover, playerLover,request.getSession(),index,true);
		GameSession session = SessionManger.getInstance().getSession(lover.getId());
		if (session != null) {
			sendMessage(lover, player, playerLover,session,index,false);
			OutputMessage om = new OutputMessage();
			om.putInt(player.getId());
			session.sendMessage(Protocol.RESPONSE_LOVERMESSAGE_SENDER, om);
		}
		
	}
	
	private void sendMessage(Player player, Player lover,PlayerFriend playerLover,GameSession session,int index,boolean read){
		OutputMessage om = new OutputMessage();
		om.putString(lover.getNickname());
		om.putString(player.getNickname());
		om.putString(lover.getHeadImage());
		om.putString(player.getHeadImage());
		
		OutputMessage giftOm = new OutputMessage();
		//礼物列表，只能加情人后的才算
		FriendService.getPlayerFriendByPlayer(player, true, FriendService.LOVERTYPE);
		PlayerFriend pFriend = player.getLoverMap().get(lover.getId());
		FriendService.getPlayerFriendByPlayer(lover, true, FriendService.LOVERTYPE);
		PlayerFriend lFriend = lover.getLoverMap().get(player.getId());
		int intimacy = 0;
		if (pFriend == null || pFriend.getFriendStatus() != FriendService.LOVERSTATUSSUCC || lFriend == null || lFriend.getFriendStatus() != FriendService.LOVERSTATUSSUCC) {
			om.putInt(0);
			om.putInt(0);
			om.putInt(0);
			om.putInt(0);
			om.putInt(0);
		}else {
			String selfGifts = pFriend.getLoverGifts();
			String loverGifts = lFriend.getLoverGifts();
			Map<Integer, Integer> selfGiftMap = new HashMap<Integer, Integer>();
			Map<Integer, Integer> loverGiftMap = new HashMap<Integer, Integer>();
			Object o = MemcachedResource.get(LoverService.getKey(pFriend));
			if (o != null) {
				selfGifts = o.toString();
			}
			o = MemcachedResource.get(LoverService.getKey(lFriend));
			if (o != null) {
				loverGifts = o.toString();
			}

			String tmp[];
			String tmp2[];
			if (selfGifts.length() > 0) {
				tmp = selfGifts.split("\\|");
				for (int i = 0; i < tmp.length; i++) {
					tmp2 = tmp[i].split("_");
					selfGiftMap.put(Integer.parseInt(tmp2[0]), Integer.parseInt(tmp2[1]));
				}
			}
			
			if (loverGifts.length() > 0) {
				tmp = loverGifts.split("\\|");
				for (int i = 0; i < tmp.length; i++) {
					tmp2 = tmp[i].split("_");
					loverGiftMap.put(Integer.parseInt(tmp2[0]), Integer.parseInt(tmp2[1]));
				}
			}
			PropsConfig pc = null;
			int sel = 0;
			int lov = 0;
			if (selfGiftMap.containsKey(PropCacheAction.egg)) {
				sel = selfGiftMap.get(PropCacheAction.egg);
			}
			if (loverGiftMap.containsKey(PropCacheAction.egg)) {
				lov = loverGiftMap.get(PropCacheAction.egg);
			}
			pc = ResourceCache.getInstance().getPropsConfigs().get(PropCacheAction.egg);
			intimacy += (sel + lov) * pc.getCopper();
			giftOm.putInt(sel + lov);
			
			
			sel = 0;
			lov = 0;
			if (selfGiftMap.containsKey(PropCacheAction.flower)) {
				sel = selfGiftMap.get(PropCacheAction.flower);
			}
			if (loverGiftMap.containsKey(PropCacheAction.flower)) {
				lov = loverGiftMap.get(PropCacheAction.flower);
			}
			pc = ResourceCache.getInstance().getPropsConfigs().get(PropCacheAction.flower);
			intimacy += (sel + lov) * pc.getCopper();
			giftOm.putInt(sel + lov);
			
			sel = 0;
			lov = 0;
			if (selfGiftMap.containsKey(PropCacheAction.car)) {
				sel = selfGiftMap.get(PropCacheAction.car);
			}
			if (loverGiftMap.containsKey(PropCacheAction.car)) {
				lov = loverGiftMap.get(PropCacheAction.car);
			}
			pc = ResourceCache.getInstance().getPropsConfigs().get(PropCacheAction.car);
			intimacy += (sel + lov) * pc.getCopper();
			giftOm.putInt(sel + lov);
			
			sel = 0;
			lov = 0;
			if (selfGiftMap.containsKey(PropCacheAction.boat)) {
				sel = selfGiftMap.get(PropCacheAction.boat);
			}
			if (loverGiftMap.containsKey(PropCacheAction.boat)) {
				lov = loverGiftMap.get(PropCacheAction.boat);
			}
			pc = ResourceCache.getInstance().getPropsConfigs().get(PropCacheAction.boat);
			intimacy += (sel + lov) * pc.getCopper();
			giftOm.putInt(sel + lov);
			
			sel = 0;
			lov = 0;
			if (selfGiftMap.containsKey(PropCacheAction.house)) {
				sel = selfGiftMap.get(PropCacheAction.house);
			}
			if (loverGiftMap.containsKey(PropCacheAction.house)) {
				lov = loverGiftMap.get(PropCacheAction.house);
			}
			pc = ResourceCache.getInstance().getPropsConfigs().get(PropCacheAction.house);
			intimacy += (sel + lov) * pc.getCopper();
			giftOm.putInt(sel + lov);
			
			
		}
		
		om.putInt(intimacy / 1000);
		MemcachedResource.save(LoverService.getIntimacyKey(pFriend), (intimacy / 1000) + "", 24 * 60 * 60);
		if (pFriend.getLoverIntimacy() != intimacy / 1000) {
			pFriend.setLoverIntimacy(intimacy / 1000);
			BaseEngine.getInstace().getFriendActionIpml().updateFriend(pFriend);
		}
		if (lFriend.getLoverIntimacy() != intimacy / 1000) {
			lFriend.setLoverIntimacy(intimacy / 1000);
			BaseEngine.getInstace().getFriendActionIpml().updateFriend(lFriend);
		}
		om.putInt(player.getGolds() + lover.getGolds());
		om.putInt(player.getCopper() + lover.getCopper());
		om.putByte(giftOm.getBytes());
		
		
		//聊天列表
		
		ErrorCode res = LoverMessageService.getLoverMessage(player, lover,index,read);
		List<PlayerMessage> playerMessages = (List<PlayerMessage>) res.getObject();
		om.putShort((short)playerMessages.size());
		
		PlayerMessage pMessage = null;
		PlayerMessage last = null;
		String date = "";
		for (int i = 0; i < playerMessages.size(); i++) {
			date = "";
			pMessage = playerMessages.get(i);
			om.putString(pMessage.getMsg());
			byte b = (byte)(player.getId().intValue() == pMessage.getPlayerId().intValue() ? 0 : 1);
			om.putByte(b);
			if (i != 0) {
				last = playerMessages.get(i - 1);
			}
			if (i == 0) {
				date = DateUtil.getDetailDate(pMessage.getCreateTime());
				om.putString(date);
			}else {
				int times = (int)(pMessage.getCreateTime().getTime() - last.getCreateTime().getTime());
				if (times > 3 * 60 * 1000) {
					date = DateUtil.getDetailDate(pMessage.getCreateTime());
					om.putString(date);
				}else {
					om.putString(date);
				}
			}
			
					
		}
		
		om.putInt(lover.getId());
		session.sendMessage(Protocol.RESPONSE_LOVERMESSAGE, om);
	}
}

