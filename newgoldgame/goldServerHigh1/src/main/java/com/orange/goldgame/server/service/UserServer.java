/**
 * SuperStarLogin
 * com.orange.superstar.login.server
 * UserServer.java
 */
package com.orange.goldgame.server.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.ActivityConfig;
import com.orange.goldgame.domain.GetOnce;
import com.orange.goldgame.domain.Gift;
import com.orange.goldgame.domain.NoticeMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.engine.HandlerEngine;
import com.orange.goldgame.server.engine.LoginHandlerEngine;
import com.orange.goldgame.server.engine.ResponseEngine;
import com.orange.goldgame.server.manager.GetOnceManager;
import com.orange.goldgame.server.manager.NoticeManager;
import com.orange.goldgame.server.manager.SessionManger;

/**
 * @author wuruihuang 2013.5.22
 */
public class UserServer extends BaseServer{
	public static final short PROTOCOL_CREATE = 1002;
	public static final short PROTOCOL_LOGIN = 1003;
	public static final short PROTOCOL_MODIFY = 1004;
	public static int SESSION_NUMBER = 1004;
	

    private static final Logger logger = LoggerFactory.getLogger(UserServer.class);
	
	public void test(SocketRequest request, SocketResponse response)throws JuiceException{
		request.getInputMessage();
	}
	
	
	/***
	 * 玩家进入大厅  即是游戏登录界面
	 */
	public void requestIntoHall(SocketRequest request, SocketResponse response) throws JuiceException {
	}
	
	/**
	 * 客户端玩家请求登陆服务器
	 */
	public void userLogin(SocketRequest request, SocketResponse response) throws JuiceException {
		//接收机器码和渠道号和版本号
		int number = SessionManger.getInstance().getGameSessions().size();
		logger.info("userNumber|" + number);
		if (number >= SessionManger.MAX_NUM) {
			BaseServer.sendErrorMsg(request.getSession(), (short)1, "服务器忙，请稍后进入");
			return;
		}
		InputMessage msg = request.getInputMessage();
		String nickname = msg.getUTF();
		String machineCode = msg.getUTF();
		String appId = msg.getUTF();
		String appVersion = msg.getUTF();
		String app_channel = "orangegame002";
		try {
			app_channel = msg.getUTF();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//在线人数+1
		SESSION_NUMBER = SESSION_NUMBER+1;
		LoginHandlerEngine.loginHandler(request,response,nickname,machineCode,appId,appVersion,SESSION_NUMBER,app_channel);
		
	}
	
	
	/**
	 * 60 玩家查看他人信息
	 */
	public void requestOtherInfo(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage msg = request.getInputMessage();
		//主动者
		int userId1 =  msg.getInt();
		//被动者
		int userId2 =  msg.getInt();
		
		Player player = PlayerService.getSimplePlayerByPlayerId(userId1);
		Player otherPlayer = PlayerService.getSimplePlayerByPlayerId(userId2);

        boolean isExist = FriendService.getPlayerFriendByPlayer(player,FriendService.FRIENDTYPE).get(userId2)!=null;
        
		OutputMessage om = new OutputMessage();
		om.putByte(isExist?(byte)1:0);
		om.putInt(otherPlayer.getId());
		om.putString(otherPlayer.getNickname());
		om.putString(otherPlayer.getAppellation());
		om.putInt(otherPlayer.getWins());
		om.putInt(otherPlayer.getLoses());
		om.putByte(otherPlayer.getSex()==1?(byte)1:0);
		om.putString(otherPlayer.getHeadImage());
		om.putString(otherPlayer.getTag());
		
		Gift flower = GiftService.getInstance().getPlayerGift(otherPlayer, PropCacheAction.flower);
		Gift egg = GiftService.getInstance().getPlayerGift(otherPlayer, PropCacheAction.egg);
		Gift car = GiftService.getInstance().getPlayerGift(otherPlayer, PropCacheAction.car);
		Gift house = GiftService.getInstance().getPlayerGift(otherPlayer, PropCacheAction.house);
		Gift boat = GiftService.getInstance().getPlayerGift(otherPlayer, PropCacheAction.boat);
		om.putInt(flower==null?0:flower.getAmont());
		om.putInt(egg==null?0:egg.getAmont());
		om.putInt(car==null?0:car.getAmont());
		om.putInt(house==null?0:house.getAmont());
		om.putInt(boat==null?0:boat.getAmont());
		
		request.getSession().sendMessage(Protocol.RESPONSE_OTHER_INFO, om);
	}
	
	/**
	 * 63 玩家请求查看自己个人的信息
	 */
	public void requestInfo(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId =  msg.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		
		OutputMessage om = new OutputMessage();
		om.putInt(player.getId());
		om.putString(player.getNickname());
		om.putInt(player.getCopper());
		om.putString(player.getAccount());
		om.putByte(Byte.parseByte(player.getSex()+""));
		om.putString(player.getHeadImage());
		om.putString(player.getAppellation());
		om.putString(player.getTag());
		om.putInt(player.getWins());
		om.putInt(player.getLoses());
		
		PlayerProps exchange = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.exchange_prop);
		PlayerProps change = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.change_card);
		PlayerProps four = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.four_card);
		PlayerProps eight = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.eight_card);
		PlayerProps band = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.band_card);
		om.putInt(1);
		om.putInt(player.getGolds());
		om.putInt(exchange==null?0:exchange.getNumber());
		om.putInt(change==null?0:change.getNumber());
		om.putInt(four==null?0:four.getNumber());
		om.putInt(eight==null?0:eight.getNumber());
		om.putInt(band==null?0:band.getNumber());
		
		
		Gift flower = GiftService.getInstance().getPlayerGift(player, PropCacheAction.flower);
        Gift egg = GiftService.getInstance().getPlayerGift(player, PropCacheAction.egg);
        Gift car = GiftService.getInstance().getPlayerGift(player, PropCacheAction.car);
        Gift house = GiftService.getInstance().getPlayerGift(player, PropCacheAction.house);
        Gift boat = GiftService.getInstance().getPlayerGift(player, PropCacheAction.boat);
        om.putInt(flower==null?0:flower.getAmont());
        om.putInt(egg==null?0:egg.getAmont());
        om.putInt(car==null?0:car.getAmont());
        om.putInt(house==null?0:house.getAmont());
        om.putInt(boat==null?0:boat.getAmont());
        
        request.getSession().sendMessage(Protocol.RESPONSE_MYSELF_INFO, om);
	}
	
	/**
	 * 14 玩家请求更新个人信息
	 */
	public void requestUpdateInfo(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int userId =  msg.getInt();
		byte sex =  msg.getByte();
		String nickname = msg.getUTF();
		String tag = msg.getUTF();
		String head = msg.getUTF();
		Player player = PlayerService.getPlayer(userId, request.getSession());
		HandlerEngine.handlerUpdatePlayerInfo(player, sex,nickname, tag, head);
		sendErrorMsg(request.getSession(), (short)1, "修改成功！");
	}
	
	
	/**
	 * 玩家请求个人物品列表
	 */
	public void requestPersonalItems(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		List<PlayerProps> personalItemList = PropCacheAction.quaryAllPlayerProps(player);
		int gameStone = player.getGolds();
		ResponseEngine.personalItemListResponse(response,personalItemList,gameStone);
		
	}
	
	/**
	 * 玩家发送喊话协议
	 */
	public void requestBroadcast(SocketRequest request, SocketResponse response) throws JuiceException {
	    InputMessage im = request.getInputMessage();
		int playerId = im.getInt();
		String msg = im.getUTF();
		
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		
		if(player.getStatus() == Constants.PLAYER_STATUS_BAN_SYSTEM){
			sendErrorMsg(response, (short)1,"您没有发表公告的权限");
            return ;
        }
		
		NoticeMessage nm = new NoticeMessage();
		nm.setMessage(msg);
		nm.setNickName(player.getNickname());
		try{
		    NoticeManager.getInstance().sendPlayerMessage(player, nm);
		}catch(GoldException e){
		    sendErrorMsg(response, (short)1, e.getMessage());
            return;
		}
	}
	
	/***
	 * 请求是否是第一次登录
	 * @param request
	 * @param response
	 */
	public void requestIsFirstLogin(SocketRequest request, SocketResponse response){
	    InputMessage im = request.getInputMessage();
        int playerId = im.getInt();
        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
        GetOnce once = GetOnceManager.getPlayerGetOnce(player, Constants.FIRST_LOGIN);
        
        OutputMessage om = new OutputMessage();
        if(once!=null){
            om.putByte((byte)1);
        }else{
            om.putByte((byte)0);
        }
	}
	
	
	//请求活动
	public void requestActivities(SocketRequest request, SocketResponse response){
	    Map<Integer,ActivityConfig> map = ResourceCache.getInstance().getActivityConfigs();
	    OutputMessage om = new OutputMessage();
	    if(map == null){
	        om.putShort((short)0);
	    }else{
	        om.putShort((short)map.size());
	        for(ActivityConfig config : map.values()){
	            om.putString(config.getPicUrl());
	        }
	    }
	    request.getSession().sendMessage(Protocol.RESPONSE_ACTIVITY_LIST, om);
	}
	
	
	/**
	 * 心跳处理
	 * @param request
	 * @param response
	 */
	public void requestHart(SocketRequest request, SocketResponse response){
		InputMessage im = request.getInputMessage();
		int player_id = im.getInt();
		PlayerService.getPlayer(player_id, request.getSession());
		logger.debug("hart" + player_id);
	}
	
	/**
	 * 玩家请求完成新手引导
	 * @param request
	 * @param response
	 */
	public void requestNewPlayerEnd(SocketRequest request, SocketResponse response){
		InputMessage im = request.getInputMessage();
		int player_id = im.getInt();
		Player player = PlayerService.getPlayer(player_id, request.getSession());
		player.setIsNewPlayer((byte)0);
		BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
		OutputMessage msg = new OutputMessage();
		response.sendMessage(Protocol.RESPONSE_NEW_END, msg);
	}
}
