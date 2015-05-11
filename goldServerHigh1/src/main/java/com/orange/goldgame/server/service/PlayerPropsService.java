package com.orange.goldgame.server.service;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.domain.Card;
import com.orange.goldgame.server.manager.GameRoomManager;

public class PlayerPropsService {
	
	/**
	 * 获取道具数量
	 * @param request
	 * @param response
	 * @throws GoldException
	 */
	public void getPlayerPropInfo(SocketRequest request, SocketResponse response)
			throws GoldException {
		InputMessage im=request.getInputMessage();
		int player_id=im.getInt();
		int prop_id = im.getByte();
		Player player = PlayerService.getPlayer(player_id, request.getSession());
		PropsConfig pc = ResourceCache.getInstance().getPropsConfigs().get(prop_id);
		//大于0时为翻倍卡，需要返回4倍卡和8倍卡数量
		PlayerProps pp = null;
		short size = 1;
		OutputMessage om = new OutputMessage();
		if (pc.getMultiple() > 0) {
			size = 2;
			om.putShort(size);
			pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.four_card);
			om.putByte((byte)PropCacheAction.four_card);
			om.putInt(pp == null?0:pp.getNumber());
			pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.eight_card);
			om.putByte((byte)PropCacheAction.eight_card);
			om.putInt(pp == null?0:pp.getNumber());
		}else {
			om.putShort(size);
			pp = PropCacheAction.getplayerPropByPropid(player, prop_id);
			om.putByte((byte)prop_id);
			om.putInt(pp == null?0:pp.getNumber());
		}
		response.sendMessage(Protocol.RESPONSE_PLAYER_PROP_INFO, om);
	}
	
	/**
	 * 使用道具
	 * @param request
	 * @param response
	 * @throws GoldException
	 */
	public void getPlayerPropUse(SocketRequest request, SocketResponse response)
			throws GoldException {
		InputMessage im=request.getInputMessage();
		int player_id=im.getInt();
		int prop_id = im.getByte();
		byte poker_id = 0;
		try {
			poker_id = im.getByte();
		} catch (Exception e) {
		}
		Player player = PlayerService.getPlayer(player_id, request.getSession());
		ErrorCode res = null;
		switch (prop_id) {
		case PropCacheAction.change_card://换牌
			res = PropCacheAction.useExchangeProps(player, poker_id);
			break;
		case PropCacheAction.four_card://四倍
			res = PropCacheAction.useNCard(player, prop_id);
			break;
		case PropCacheAction.eight_card://八倍
			res = PropCacheAction.useNCard(player, prop_id);
			break;
		case PropCacheAction.band_card://禁比卡
			res = PropCacheAction.useNoCompareCard(player);
			break;
		}
		
		if (res == null) {
			BaseServer.sendErrorMsg(response, (short)1, "请求错误");
			return;
		}
		if (res.getStatus() != ErrorCode.SUCC) {
			BaseServer.sendErrorMsg(response, (short)res.getStatus(), res.getMsg());
			return;
		}
		byte card_id = 100;
		if (prop_id == PropCacheAction.change_card) {
			Card card = (Card)res.getObject();
			card_id = card.getCardId();
		}
		
		OutputMessage om = new OutputMessage();
		om.putInt(player_id);
		om.putByte((byte)prop_id);
		om.putByte(card_id);
		GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(player_id);
		room.sendMessage(Protocol.RESPONSE_PLAYER_PROP_USE, om);
		
	}
}
