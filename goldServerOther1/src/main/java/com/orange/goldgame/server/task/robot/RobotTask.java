package com.orange.goldgame.server.task.robot;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.emun.GamerState;
import com.orange.goldgame.server.exception.GoldenNOTEnoughException;
import com.orange.goldgame.server.manager.GameTableManager;
import com.orange.goldgame.server.manager.RobotManager;
import com.orange.goldgame.server.service.GameServer;
import com.orange.goldgame.server.task.robot.RobotRequestPool.NotifyItem;

public class RobotTask extends Thread{
	Logger logger = LoggerFactory.getLogger(RobotTask.class);
	@Override
	public void run() {
		LinkedBlockingQueue<NotifyItem> requestQueue = null;
		StringBuilder sb = null;
		GameTable table;
		GameRoom room;
		Gamer gamer;
		while(true){
			requestQueue = RobotRequestPool.getInstance().getRobotRequestQueue();
			try {
				NotifyItem ni = requestQueue.poll(100, TimeUnit.MILLISECONDS);
				int poolWarnLenght = 200;
				int poolSize  = 0;
				if (ni != null) {
					logger.debug("==========pull|" + ni + "|" + requestQueue.size());
					sb = new StringBuilder();
					table = ni.getTable();
					room = ni.getRoom();
					gamer = ni.getGamer();
//					if (timeout >= delay_time) {
						int operation = ni.getOperation();
		        	    if(operation==0 || operation == Constants.LOOK_CARD){
                            if(table.getGamingList().size()>1){
                            	try {
                            		operation = RobotManager.getInstance().nextOperation(gamer, table);
								} catch (GoldenNOTEnoughException e) {
								    if(table.getTurns()>=3){
								        operation = RobotManager.getInstance().fightCart(table, gamer);
								    }
								    else{
								        GameTableManager.gamerGiveUp(table, gamer);
								        operation = Constants.GIVEUP_CARD;
								    }
								}
                            }
		        	    }
						switch (operation) {//1跟牌，2叫牌，3比拍，4看牌，5弃牌，-1错误信息
						case Constants.FOLLOW_GOLD:
							GameServer.followCardSendMessage(gamer, table, room);
							logger.debug("机器人跟注:"+gamer.getPlayerId());
							break;
						case Constants.ADD_GOLD:
						    GameServer.sendAddGoldMessage(gamer, table, room);
						    logger.debug("机器人加注:"+gamer.getPlayerId());
							break;
						case Constants.COMP_CARD:
							GameServer.sendFightMessage(table.getSrc(), table.getDst(), table.isWin(), table, room);
							table.setSrc(null);
							table.setDst(null);
							table.setWin(false);
							break;
						case Constants.LOOK_CARD:
							GameServer.sendLookCardMessage(gamer, room, null);
							break;
						case Constants.GIVEUP_CARD:
						    GameServer.sendGiveMessage(gamer, room);
						    GameServer.responseNextGamer(room);
							break;
						case Constants.READY:
						    GameTableManager.gamerReady(table, gamer);
						    if(gamer.getState()==GamerState.GAME_READY){
						        GameServer.broadcastReadyInfo(room,gamer.getPlayerId());
						    }
						    break;
                        case Constants.EXITROOM:
                            GameTableManager.removeGamer(table, gamer);
                            GameServer.broadcastExitRoom(room, gamer.getPlayerId());
                            if(table.getCurrentPlayerId() == gamer.getPlayerId()){
                                GameServer.responseNextGamer(room);
                            }
                            RobotManager.getInstance().backOnePlayer(gamer);
                            break;
						}
			        		
			        	if(!(operation == Constants.COMP_CARD || 
			        	        operation == Constants.READY  ||
			        	        operation == Constants.EXITROOM )){
			        	    GameServer.checkNextRobot(table, room);
			        	}
					poolSize = requestQueue.size();
					if (poolSize >= poolWarnLenght) {
						sb.append("que").append(poolSize);
						logger.warn("poolSize:"+sb.toString());
					}
					
				}
			} catch (Exception e) {
				logger.error("WorkThread exception", e);
			}
		}
	}
}
