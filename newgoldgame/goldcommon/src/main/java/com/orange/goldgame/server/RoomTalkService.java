package com.orange.goldgame.server;

import java.util.Date;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.PlayerConsume;
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
	

	
	/**初始化玩家消费记录**/
	public static PlayerConsume  addPlayerConsumeRecord(int playerId,int before,int packagePropsId,
			int propMoney, int after,byte type) {
		PlayerConsume record = new PlayerConsume();
        record.setPlayerId(playerId);
        record.setBeforeNum(before);
        record.setConsumeNum(propMoney);
        record.setPropConfigId(packagePropsId);
        record.setAfterNum(after);
        record.setComsumeTime(new Date());
        record.setState(type);
        return record;
	}
}
