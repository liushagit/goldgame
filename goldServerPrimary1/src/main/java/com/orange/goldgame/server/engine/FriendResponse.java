package com.orange.goldgame.server.engine;

import java.util.List;

import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.manager.GameRoomManager;
import com.orange.goldgame.server.manager.RunTimePropertyManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.vo.FriendVo;

public class FriendResponse {
    
       public static void gameTableListFriendResponse(SocketResponse response,
               int copper,int playerId,int friendId,byte giftType){
           OutputMessage om = new OutputMessage();
           om.putInt(copper);
           om.putInt(playerId);
           om.putInt(friendId);
           om.putByte(giftType);
           //response.sendMessage(Protocol.RESPONSE_GIFT_GAMING, om);
           GameRoomManager.getInstance().getRoomByPlayerId(playerId).sendMessage(Protocol.RESPONSE_GIFT_GAMING, om);
       }
    
	   public static void listFriendResponse(SocketResponse response,
	            Player player, List<FriendVo> list){
	       
	       int copper = player.getCopper();//玩家剩余金币
		   OutputMessage om = new OutputMessage();
		   boolean isSendGold = RunTimePropertyManager.getInstance().isAbleGiveGold();
		   om.putByte(isSendGold?(byte)1:0);
		   om.putInt(copper);
		   short size = (short)list.size();
		   om.putShort(size);
		   for(FriendVo fo :list){
			   om.putInt(fo.getFriend().getFriendId());
			   om.putString(fo.getFriend().getFriendName());
			   GameSession session = SessionManger.getInstance().getSession(fo.getFriend().getFriendId());
			   Player friend = null;
			   if (session != null) {
				   friend = PlayerService.getOnLinePlayer(session);
			   }
			   if (friend == null) {
				friend = PlayerService.getSimplePlayerByPlayerId(fo.getFriend().getFriendId());
			}
			   if(friend != null){
			       om.putByte(Byte.parseByte(friend.getSex()+""));
	               om.putString(friend.getHeadImage());
	               om.putByte(fo.getIsOnline());
			   }else{
			       om.putByte(Byte.parseByte("0"));
	               om.putString("1");
	               om.putByte((byte)0);
			   }
			   om.putByte(fo.getAreaType());
			   om.putByte(fo.getAreaId());
		   }
		   response.sendMessage(Protocol.RESPONSE_FRIEND_LIST, om);
	    }

	public static void addFriendResultResponse(SocketResponse response,
			FriendVo fo) {
		byte isSuccess = 0;
		if(fo!=null){
			isSuccess = 1;
			
		}
		OutputMessage om = new OutputMessage();
		om.putByte(isSuccess);
		om.putInt(fo.getFriend().getFriendId());
		om.putString(fo.getFriend().getFriendName());
		om.putByte(fo.getIsOnline());
		response.sendMessage(Protocol.RESPONSE_ADD_FRIEND_RESUILT, om);
	}

	public static void removeFriendResultResponse(SocketResponse response,
			byte isSuccess) {
		OutputMessage om = new OutputMessage();
		om.putByte(isSuccess);
		response.sendMessage(Protocol.RESPONSE_REMOVE_FRIEND_IS_SUCCESS, om);
	}

	public static void giftListResponse(SocketRequest request,
			List<PropsConfig> giftList) {
		short size = (byte)giftList.size();
		OutputMessage om = new OutputMessage();
		om.putShort(size);
		for(PropsConfig pc :giftList){
			int id = pc.getId();
			om.putInt(id);
			om.putInt(pc.getCopper());
		}
		request.getSession().sendMessage(Protocol.RESPONSE_GIFT_LIST, om);
	}

	public static void sendGiftResonse(SocketResponse response, byte isSendable) {
		OutputMessage om = new OutputMessage();
		om.putByte(isSendable);
		response.sendMessage(Protocol.RESPONSE_ISSENDABLE, om);
	}
}
