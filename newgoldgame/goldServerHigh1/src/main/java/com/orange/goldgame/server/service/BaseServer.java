/**
 * JuiceGameServer
 * com.orange.game.service
 * BaseServer.java
 */
package com.orange.goldgame.server.service;


import java.util.concurrent.LinkedBlockingQueue;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.ActionAware;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.manager.GameRoomManager;

/**
 * @author shaojieque
 * 2013-3-24
 */
public class BaseServer extends ActionAware{
	public static final short HEART_PROTOCOL = 100;
	
	public static final String ROOM_PREFIX = "Superstar";
	
	public BaseServer(){
	}
	
	public static void sendErrorMsg(SocketResponse response,short id,String msg){
		OutputMessage om = new OutputMessage();
		om.putShort(id);
		om.putString(msg);
		response.sendMessage(Protocol.RESPONSE_ERROR_MESSAGE, om);
	}
	
	public static void sendErrorMsg(GameSession session,short id,String msg){
        OutputMessage om = new OutputMessage();
        om.putShort(id);
        om.putString(msg);
        session.sendMessage(Protocol.RESPONSE_ERROR_MESSAGE, om);
    }
	public static void sendNextMessage(GameTable table){
	    LinkedBlockingQueue<Gamer> gamers = table.getGamingList();
		if (gamers == null || gamers.size() <= 0) {
			return;
		}
        GameRoom room = GameRoomManager.getInstance().getRoomByTable(table);
        OutputMessage om = new OutputMessage();
        om.putByte((byte)table.getTurns());
        room.sendMessage(Protocol.RESPONSE_TURNS, om);
        int real = 0;
        for (Gamer g : gamers) {
        	real = g.getUseBanCard();
        	g.coumUseBanCard();
        	if (real > 0 && g.getUseBanCard() <= 0) {
        		OutputMessage om1 = new OutputMessage();
        		om1.putInt(g.getPlayerId());
				room.sendMessage(Protocol.RESPONSE_BAND_CARD, om1);
			}
			
		}
        
    }
}
