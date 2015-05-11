package com.orange.goldgame.server.task;

import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.manager.GameRoomManager;
import com.orange.goldgame.server.manager.GameTableManager;
import com.orange.goldgame.server.service.GameServer;



/**
 * 玩家赌注超时的管理
 * 单例
 * @author yesheng
 *
 */
public class GameStakeThread{

    private LinkedBlockingQueue<GameTable> tableList = new LinkedBlockingQueue<GameTable>();
    
    private static Logger log = LoggerFactory.getLogger(GameReadyThread.class);
    
    private Timer timer = new Timer();
    private GregorianCalendar calendar = new GregorianCalendar();
    
    //叫注限制时间 24秒
    private static final int STAKE_LIMIT_TIME = 22;
    //比牌限制时间5秒
    private static final int COMPARECARD_LIMIT_TIME = 8;
    //比赛开始超时
    private static final int MATCH_LIMIT_TIME = 2;
    
    
    private static GameStakeThread instance = null;
    
    public static GameStakeThread getInstance(){
        if(instance == null){
            instance = new GameStakeThread();
        }
        return instance;
    }
    
    private GameStakeThread (){}
    
    
    public void start() {
        
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    doTask();
                } catch (Exception e) {
                    log.error("gamestake exception", e);
                }
            }
        }, calendar.getTime(), 100);
        
            
    }
    
    
    //执行任务
    private void doTask(){
        try{
            for(GameTable table : tableList){
                if(table == null){
                    removeTable(table);
                    continue;
                }
                
                Long currentTime = System.currentTimeMillis();
                if(table.isStartGame()
                        && currentTime - table.getLastOperationTime() > MATCH_LIMIT_TIME * 1000){
                    GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(table.getCurrentPlayerId());
                    GameServer.startGameBussiness(table,room);
                }
                
                if(currentTime - table.getLastOperationTime() > STAKE_LIMIT_TIME*1000){
                    Gamer gamer = table.getCurrentGamer();
                    if(gamer == null) {
                        removeTable(table);
                        continue;
                    };
                    log.debug("Gamer-"+gamer.getPlayerId()+":"
                            + "加注超时！");
                    GameTableManager.gamerGiveUp(table, gamer);
                    GameRoom room = GameRoomManager.getInstance().getRoomByTable(table);
                    if(!GameTableManager.isMatch(table)){
                        GameServer.checkNextRobot(table, room);
                    }
                    GameServer.sendGiveMessage(gamer, room);
                    GameServer.responseNextGamer(room);
                }
                //比牌超时判断，如果超时，自动确认
                if(table.isCompareCard() 
                        && currentTime - table.getLastOperationTime() > COMPARECARD_LIMIT_TIME*1000){
                    GameRoom room = GameRoomManager.getInstance().getRoomByTable(table);
                    if(room == null){
                        removeTable(table);
                        continue;
                    }
                    GameServer.compareCardComfirm(room, table);
                }
                
                
            }
        }catch(Exception e){
            log.error("GameStakeThread Error", e);
        }
    }
    
    
    //加入游戏桌
    public void addTable(GameTable table){
        log.debug("table-"+table.getTableId()+":加入叫注超时管理");
        tableList.add(table);
    }
    
    //移除游戏桌
    public void removeTable(GameTable table){
        tableList.remove(table);
    }
    
    
}
