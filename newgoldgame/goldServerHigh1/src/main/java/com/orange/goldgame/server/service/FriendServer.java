/**
 * SuperStarServer
 * com.orange.superstar.server.service
 * FriendServer.java
 */
package com.orange.goldgame.server.service;

import java.util.List;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.engine.FriendEngine;
import com.orange.goldgame.server.engine.FriendResponse;
import com.orange.goldgame.server.exception.GoldenNOTEnoughException;
import com.orange.goldgame.server.manager.GameRoomManager;
import com.orange.goldgame.server.manager.GameTableManager;
import com.orange.goldgame.vo.FriendVo;

/**
 * @author wuruihuang 2013.5.24
 */
public class FriendServer extends BaseServer {
	
	
	/***
	 * 获取好友列表
	 */
	public void getFriends(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage im = request.getInputMessage();
		int playerId = im.getInt();
		int friendType = 0;//0为好友，1为情人
		try {
			friendType = im.getByte();
		} catch (Exception e) {
		}
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		List<FriendVo> list = FriendEngine.getFriendList(player,friendType);
		String fr = (friendType == FriendService.FRIENDTYPE) ? "好友" : "情人";
		if(list.size()<=0){
			sendErrorMsg(request.getSession(), (short)1, "您现在还没有" + fr + "哦");
		}
		FriendResponse.listFriendResponse(response,player, list,friendType);
	}
	
	/***
	 * 玩家请求礼物列表
	 */
	public void requestGiftList(SocketRequest request, SocketResponse response) throws JuiceException {
		List<PropsConfig> giftList = FriendEngine.getGiftList();
		FriendResponse.giftListResponse(request,giftList);
	}
	
	/**
	 * 处理赠送金币请求
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void requestSendGold(SocketRequest request, SocketResponse response) throws JuiceException {
	    InputMessage im = request.getInputMessage();
        int playerId = im.getInt();
        int friendId = im.getInt();
        int golds = im.getInt();
        
        Player player = PlayerService.getPlayer(playerId, request.getSession());
        Player friend = PlayerService.getSimplePlayerByPlayerId(friendId);
        
        if(playerId == friendId){
            sendErrorMsg(response, (short)1, "您不能给自己赠送金币！");
            return;
        }
        
        try{
            
            int leftGold = GoldService.getLeftCopper(player);
            if(leftGold < golds && leftGold < 0){
                sendErrorMsg(response, (short)Constants.NO_GOLD, "您的金币不足！");
                return;
            }
            GoldService.consumeCopperAndUpdateGamer(player, golds);
            GoldService.addCopperAndUpdateGamer(friend, golds);
            
            sendErrorMsg(response, (short)1, "赠送成功！");
            
        } catch(GoldenNOTEnoughException e){
            sendErrorMsg(response, (short)Constants.NO_GOLD, e.getMessage());
        }  catch(GoldException e){
            sendErrorMsg(response, (short)1, e.getMessage());
        }
	}
	
	/***
	 * 玩家请求送礼
	 */
	public void requestSendGift(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage im = request.getInputMessage();
		int playerId = im.getInt();
		int friendId = im.getInt();
		byte giftType = im.getByte();
		byte type = im.getByte();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		Player friend = PlayerService.getSimplePlayerByPlayerId(friendId);
		
		if(playerId == friendId){
		    sendErrorMsg(response, (short)1, "您不能给自己送礼！");
		    return;
		}
		
		try{
		    FriendEngine.giveGift(player, friend, giftType);
	        
	        OutputMessage om = new OutputMessage();
	        int leftCop=GoldService.getLeftCopper(player);
	        om.putInt(leftCop);
	        if(type==0){
	            request.getSession().sendMessage(Protocol.RESPONSE_LEFTGOLD, om);
	        }else if(type==1){
	            om.putInt(player.getId());
	            om.putInt(friendId);
	            om.putByte(giftType);
	            GameRoomManager.getInstance().getRoomByPlayerId(playerId).sendMessage(Protocol.RESPONSE_GIFT_GAMING, om);
	        }
//	        SynMoney money=new SynMoney();
//	        money.setCopper(leftCop);
//	        ShareGoldCache.setCacheGold(playerId, money);
		} catch(GoldenNOTEnoughException e){
            sendErrorMsg(response, (short)Constants.NO_GOLD, e.getMessage());
        }  catch(GoldException e){
		    sendErrorMsg(response, (short)1, e.getMessage());
		}
	}
	
	/***
	 * 添加好友
	 * <table>
	 * 	<tr><td>参数</td><td>类型</td><td>描述</td></tr>
	 * 	<tr><td>userId</td><td>int</td><td>用户Id</td></tr>
	 * 	<tr><td>frinedId</td><td>int</td><td>好友Id</td></tr>
	 * </table>
	 */
	
	/**
	 * 玩家游戏中添加好友协议
	 */
	public void addFriend(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int friendId = msg.getInt();
		byte giftType = msg.getByte();
		byte type = msg.getByte();

        Player player = PlayerService.getPlayer(playerId, request.getSession());
		if(playerId == friendId){
		    sendErrorMsg(response, (short)1, "亲，您不能加自己为好友！");
		    List<FriendVo> list = FriendEngine.getFriendList(player,FriendService.FRIENDTYPE);
	        FriendResponse.listFriendResponse(response,player, list,FriendService.FRIENDTYPE);
		    return;
		}
		
		Player friend = PlayerService.getSimplePlayerByPlayerId(friendId);
		try{
			if (friend != null) {
				FriendEngine.giveGift(player, friend, giftType);
	            FriendEngine.addFriend(player,friend);
		        sendErrorMsg(response, (short)1, "添加好友成功！");
		        int leftCop=0;
		        if(type==0){
		        	OutputMessage om = new OutputMessage();
		        	leftCop=GoldService.getLeftCopper(player);
		        	om.putInt(leftCop);
		            request.getSession().sendMessage(Protocol.RESPONSE_LEFTGOLD, om);
		        }
		        if(type==1){
		        	leftCop=GoldService.getLeftCopper(player);
		            FriendResponse.gameTableListFriendResponse(response,leftCop,player.getId(),friendId,giftType);
		        }
			}else {
				sendErrorMsg(response, (short)-1, "添加好友不存在！");
			}
//	        SynMoney money=new SynMoney();
//	        money.setCopper(leftCop);
//	        ShareGoldCache.setCacheGold(playerId, money);
		} catch(GoldenNOTEnoughException e){
            sendErrorMsg(response, (short)Constants.NO_GOLD, e.getMessage());
        } catch(GoldException e){
		    sendErrorMsg(response, (short)1, e.getMessage());
		}
		List<FriendVo> list = FriendEngine.getFriendList(player,FriendService.FRIENDTYPE);
		if(type==0){
		    FriendResponse.listFriendResponse(response,player, list,FriendService.FRIENDTYPE);
		}
        
	}
	
	/**
	 * 玩家请求删除好友
	 */
	public void removeFriend(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int friendId = msg.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		try{
		    FriendEngine.removeFriend(player,friendId);
		    
		    sendErrorMsg(response, (short)1, "删除好友成功！");
		} catch(GoldException e){
		    sendErrorMsg(response, (short)1, e.getMessage());
		}
        List<FriendVo> list = FriendEngine.getFriendList(player,FriendService.FRIENDTYPE);
        FriendResponse.listFriendResponse(response,player, list,FriendService.FRIENDTYPE);
        if(list.size()<=0){
            sendErrorMsg(request.getSession(), (short)1, "您现在还没有好友哦");
        }
	}
	
	/**
	 * 处理跟踪好友请求
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void followFriend(SocketRequest request, SocketResponse response) throws JuiceException {
	    InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        int friendId = msg.getInt();
        Player friend = PlayerService.getSimplePlayerByPlayerId(friendId);
        Player player = PlayerService.getPlayer(playerId, request.getSession());
//        SessionManger.getInstance().putSession(playerId, request.getSession());
        GameTable table = GameRoomManager.getInstance().getTableByPlayerId(friend.getId());
        GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(friend.getId());
        if(table == null || room == null){
            sendErrorMsg(request.getSession(), (short)1, "您的好友没有进行游戏！");
            return ;
        }
        
        AreaConfig config = ResourceCache.getInstance().getAreaConfigs().get(table.getAreaId());
//        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
        if(player.getCopper()>config.getTopLimitGolds()){
            sendErrorMsg(request.getSession(),Constants.ENTER_ROOM_ERROR,"您的金币超出了该场次的上限，请选择其他场次！");
            return ;
        }
        if(!GameTableManager.isMatch(table)){
            if(table.isFull()){
                GameServer.enterRoomByPlayerIdAndRoomId(playerId, room.getRoomId(),Constants.ENTER_WATCH,friendId);
            }else{
                GameServer.enterRoomByPlayerIdAndRoomId(playerId, room.getRoomId(),Constants.ENTER_SEAT,0);
            }
        }else{
            sendErrorMsg(request.getSession(), (short)1, "您的好友正在比赛！");
            return ;
        }
        
	}
}
