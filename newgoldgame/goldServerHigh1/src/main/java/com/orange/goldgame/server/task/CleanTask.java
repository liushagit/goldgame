package com.orange.goldgame.server.task;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.GameTableSeat;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.emun.GamerState;
import com.orange.goldgame.server.engine.ResponseEngine;
import com.orange.goldgame.server.manager.AppellationManager;
import com.orange.goldgame.server.manager.GameRoomManager;
import com.orange.goldgame.server.manager.GameTableManager;
import com.orange.goldgame.server.manager.GamerSet;
import com.orange.goldgame.server.manager.RobotManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.server.task.robot.RobotRequestPool;

public class CleanTask{


	private Timer timer = new Timer();
	private Random rd = new Random();
	private Logger log = LoggerFactory.getLogger(CleanTask.class);

	public CleanTask() {}

	/**
	 * 计时器启动
	 * 每分钟清理一次
	 */
	public void start() {
		GregorianCalendar calendar = new GregorianCalendar();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
				    cleanSession();
				    //cleanErrorRooms();
				    runTableTask();
				} catch (Exception e) {
				    log.error("CleanTaskError", e);
				}
			}
		}, calendar.getTime(), 1 * 1000);
	}
	
	
	private void cleanSession() throws GoldException{
	    List<Integer> playerids = SessionManger.getInstance().getScanIds();
        GameRoomManager.getInstance().scan(playerids);
        GamerSet.getInstance().scan(playerids);
        SessionManger.getInstance().scan(playerids);
	}
	
	//游戏桌的逻辑
    private void runTableTask()  throws GoldException{
        for(GameRoom room : GameRoomManager.getInstance().getGameRoomSet().values()){
            GameTable table = (GameTable)room.getObject(Constants.ROOM_TO_TABLE_KEY);
            if (RobotManager.getInstance().isUseRobot()) {
            	//进入房间成功后如果只有一个玩家需要加入机器人,并且不是比赛场
            	int nes = rd.nextInt(10);
            	if (nes > 7 && !table.isFull() && !GameTableManager.isMatch(table)) {
            		addRobotPlayer(table,room);
            	}
			}
            tableRobotTask(table,room);
        }
    }
	
	private void addRobotPlayer(GameTable table,GameRoom room){
	    try{
    	    Gamer robot = null;
    	    robot = RobotManager.getInstance().getOneGamer();
            robot.reset();
            AreaConfig config = ResourceCache.getInstance().getAreaConfigs().get(table.getAreaId()+3);
            Player player = RobotManager.getInstance().getRobotPlayer(robot.getPlayerId());
            if (player.getCopper() == null || player.getCopper() <= config.getLimitGolds()) {
                if (player.getCopper() <= 0) {
                    AreaConfig config2 = ResourceCache.getInstance().getAreaConfigs().get(table.getAreaId()+3);
                    player.setCopper(config2.getLimitGolds()+rd.nextInt(5)*10000);
                    player.setAppellation(AppellationManager.getInstance().getAppellation(player.getCopper()));
                    RobotManager.savePlayer(player, RobotManager.getInstance().getInfoKey(robot.getPlayerId()));
                }
            }
            
            if(player.getCopper()>config.getTopLimitGolds()){
                RobotManager.getInstance().backOnePlayer(robot);
                return;
            }
            
            GameTableManager.addGamer(table, robot);
            robot.setRoomId(room.getRoomId());
            Player enterPlayer = PlayerService.getCachePlayerByGamer(robot);
            ResponseEngine.sendHasEnterRoom(enterPlayer, room,table);
	    } catch(GoldException e){
	        log.error("add robot error", e);
	    }
	}
	
	private void tableRobotTask(GameTable table,GameRoom room){
	    boolean isNoPlayer = true;
        
        for(GameTableSeat seat : table.getSeats()){
            Gamer robot = seat.getGamer();
            
            if(robot !=null && !robot.isRobot()){
                isNoPlayer = false;
            }
            
            int op = rd.nextInt(20);
            try{
                if(robot !=null && robot.isRobot() 
                        && robot.getState() == GamerState.GAME_NOREADY
                        && !GameTableManager.isMatch(table)
                        && !table.isDealCard()
                        && !robot.isOperate()){
                    if (RobotRequestPool.getInstance().isExists(robot)) {
                        continue;
                    }
                    if(op < 18){
                        if(table.getGamingList().size()<=0 ){
                            RobotRequestPool.getInstance().addRequest(table, room, robot, rd.nextInt(4)*1000+4000, Constants.READY);
                            robot.setOperate(true);
                        }
                    }
                    else{
                        RobotRequestPool.getInstance().addRequest(table, room, robot, rd.nextInt(3)*1000+4000, Constants.EXITROOM);
                        robot.setOperate(true);
                    }
                }
            } catch(GoldException e){
                log.error("table task error", e);
            }
        }
        
        //如果房间内没有任何在线的玩家，销毁游戏桌
        if(isNoPlayer){
            isNoPlayer = true;
            GameRoomManager.backOnePlayer(table);
            GameRoomManager.getInstance().destoryRoom(room);
        }
	}
	
	
}
