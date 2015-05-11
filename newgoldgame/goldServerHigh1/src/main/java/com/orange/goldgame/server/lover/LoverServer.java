package com.orange.goldgame.server.lover;

import java.util.List;
import java.util.Map;

import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.FriendEngine;
import com.orange.goldgame.server.engine.FriendResponse;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.BaseServer;
import com.orange.goldgame.server.service.FriendService;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.vo.FriendVo;

public class LoverServer {

	/**
	 * 获取情人聊天内容
	 * 
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getLoverMessage(SocketRequest request, SocketResponse response)
			throws JuiceException {}
	
	/**
	 * 添加情人
	 * 
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void addLover(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int loverId = msg.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		Player lover = PlayerService.getSimplePlayerByPlayerId(loverId);
		
		ErrorCode res = LoverService.addLover(player, lover);
		if (res.getStatus() != ErrorCode.SUCC) {
			BaseServer.sendErrorMsg(request.getSession(), (short)-1, res.getMsg());
		}else {
			Map<Integer, PlayerFriend> playerLovers = (Map<Integer, PlayerFriend>) res.getObject();
			PlayerFriend playerLover = playerLovers.get(loverId);
			BaseServer.sendErrorMsg(request.getSession(), (short)-1, "添加" + playerLover.getFriendName() + "成功！");
			
			List<FriendVo> list = FriendEngine.getFriendList(player,FriendService.FRIENDTYPE);
			FriendResponse.listFriendResponse(response, player, list, FriendService.FRIENDTYPE);
			
			String ta = player.getSex().intValue() == 1 ? "她" : "他";
			String msg2 = player.getNickname() + "邀请你成为" + ta + "的情人！"; 
			
			PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(lover.getId(), true, Constants.LOVER, player.getId()+"",msg2);
	        GameSession session = SessionManger.getInstance().getSession(lover.getId());
	        if (session != null) {
	        	PlayerInfoCenterManager.getInstance().noticePlayerInfoCenter(session);
			}
		}
	}
	
	/**
	 * 查看邀请人信息
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void loverInfo(SocketRequest request, SocketResponse response)
			throws JuiceException {
		
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int loverId = msg.getInt();
		Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
		Player lover = PlayerService.getSimplePlayerByPlayerId(loverId);
		OutputMessage om = new OutputMessage();
		om.putInt(loverId);
		om.putString(lover.getNickname());
		om.putString(lover.getAppellation());
		om.putString(lover.getHeadImage());
		om.putInt(lover.getWins());
		om.putInt(lover.getLoses());
		om.putString(lover.getTag());
		om.putInt(lover.getGolds());
		Map<Integer, PlayerProps> playerProps = lover.getPlayerProps();
		PlayerProps pp = null;
		if (playerProps != null) {
			pp = lover.getPlayerProps().get(14);
		}
		if(pp == null){
			pp = PropCacheAction.getplayerPropByPropid(lover, 14);
		}
		om.putInt(pp == null ? 0 : pp.getNumber());
		om.putInt(lover.getCopper());
		FriendService.getPlayerFriendByPlayer(player, true, FriendService.LOVERTYPE);
		PlayerFriend pFriend = player.getLoverMap().get(lover.getId());
		if (pFriend != null && pFriend.getFriendStatus() == FriendService.LOVERSTATUSSUCC) {
			om.putByte((byte)1);//0未成功，1成功
		}else {
			om.putByte((byte)0);
		}
		response.sendMessage(Protocol.RESPONSE_LOVERINFO, om);
	}
	
	/**
	 * 处理情人请求
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void handleLover(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int loverId = msg.getInt();
		int handle = msg.getByte();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		Player lover = PlayerService.getSimplePlayerByPlayerId(loverId);
		if (handle == 0) {
			ErrorCode res = LoverService.agreeLover(player, lover);
			if (res.getStatus() != ErrorCode.SUCC) {
				BaseServer.sendErrorMsg(request.getSession(), (short)-1, res.getMsg());
			}else {
				BaseServer.sendErrorMsg(request.getSession(), (short)-1, "添加情人成功！");
			}
		}else {
			LoverService.refuseLover(player, lover);
			BaseServer.sendErrorMsg(request.getSession(), (short)-1, "成功拒绝情人请求");
		}
	}
	
	/**
	 * 删除情人
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void deleteLover(SocketRequest request, SocketResponse response)
			throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int loverId = msg.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		Player lover = PlayerService.getSimplePlayerByPlayerId(loverId);
		LoverService.deleteLover(player, lover, true,false);
		List<FriendVo> list = FriendEngine.getFriendList(player,FriendService.LOVERTYPE);
		FriendResponse.listFriendResponse(response, player, list, FriendService.LOVERTYPE);
	}
	
}
