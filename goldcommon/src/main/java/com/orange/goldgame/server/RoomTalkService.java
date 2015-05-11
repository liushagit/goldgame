package com.orange.goldgame.server;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.protocol.Protocol;

public class RoomTalkService {
	
	/**
	 * 传递房间聊天信息给房间别的玩家
	 * @param type 
	 */
	public static void sendTalkMessage(GameRoom room,int playerId,byte type, String msg){
		OutputMessage om=new OutputMessage();
		om.putInt(playerId);
		om.putByte(type);
		om.putString(msg);
		room.sendMessage(Protocol.RESPONSE_PLAYER_CHATMESSAGE,om);
	}

}
