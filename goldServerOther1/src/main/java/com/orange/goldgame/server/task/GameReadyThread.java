package com.orange.goldgame.server.task;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.GameTableSeat;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.manager.GameRoomManager;
import com.orange.goldgame.server.manager.GameTableManager;
import com.orange.goldgame.util.IOutputMeasageVo;
import com.orange.goldgame.util.OutPutMessageUtils;
import com.orange.goldgame.vo.GamingPlayerSeat;


/**
 * 此线程用于检测游戏者玩家的准备状态，2人准备后，倒计时10秒，
 * 无人再准备则准备好的玩家开始比赛，有则重复，直至所有5人都准备。
 * @author yesheng
 *
 */
public class GameReadyThread{
    
    private Logger logger = LoggerFactory.getLogger(GameReadyThread.class);
    
    private static GameReadyThread instance = null;
    
    public static GameReadyThread getInstance(){
        if(instance == null){
            instance = new GameReadyThread();
        }
        return instance;
    }
    
    private GameReadyThread(){}

    //默认倒计时
    private static int COUNT_DOWN_NUMBER = 5;
    //需要准备的玩家数量
    private static int REQUEST_GAME_NUMBER = 2;
    
    private Timer timer = new Timer();
    private GregorianCalendar calendar = new GregorianCalendar();
    //游戏桌列表，用于检查游戏桌中玩家的准备状态
    private LinkedBlockingQueue<GameTable> tableList = new LinkedBlockingQueue<GameTable>();
    
    
    public void start() {
//            timer.schedule(new TimerTask() {
//                public void run() {
//                    try {
//                        doTask();
//                    } catch (Exception e) {
//                    	logger.error("task exception",e);
//                    }
//                }
//            }, calendar.getTime(), 100);
            
    }
    
    private void doTask(){
//        try{
//            GameTable table = null;
//            Iterator<GameTable> iterator = tableList.iterator();
//            while(iterator.hasNext()){
//                table = iterator.next();
//                
//                if(table.getGamingNum() < 2){
//                    removeTable(table);
//                    continue;
//                }
//                
//                //如果所有玩家准备了，就直接开始
//                if(table.getGamingNum() == table.getSeatGamerNum() && table.getGamingNum()>=REQUEST_GAME_NUMBER){
//                    removeTable(table);
//                    logger.debug("所有玩家都准备，直接发牌");
//                    GameTableManager.dealCard(table);
//                    broadcast(table);
//                    continue;
//                }
//                
//                Long currentTime = System.currentTimeMillis();
//                //如果当前时间与房间的最后准备时间相差超过10秒，并且准备的玩家数量大于等于2，则直接发牌。
//                if(currentTime - table.getLastOperationTime() > COUNT_DOWN_NUMBER*1000
//                        && table.getGamingNum()>=REQUEST_GAME_NUMBER){
//                    removeTable(table);
//                    logger.debug("准备的人数不足5，直接发牌");
//                    GameTableManager.dealCard(table);
//                    broadcast(table);
//                    continue;
//                }
//            }
//        } catch(Exception e){
//            logger.error("gameReadyThread task error", e);
//        }
        
    }
    
    /**
     * 广播准备信息
     * @param table
     */
    private void broadcast(GameTable table){
        GameRoom room = GameRoomManager.getInstance().getRoomByTable(table);

        List<IOutputMeasageVo> list = new ArrayList<IOutputMeasageVo>();
        for(Gamer gamer:table.getGamingList()){
            GameTableSeat seat = table.getGamerSeat(gamer);
            if(seat!=null){
                GamingPlayerSeat gps = new GamingPlayerSeat();
                gps.setPlayerId(gamer.getPlayerId());
                gps.setSeatId((byte)table.getGamerSeat(gamer).getId());
                list.add(gps);
            }
        }
        
        OutputMessage om = OutPutMessageUtils.parseList(list);
        
        room.sendMessage(Protocol.RESPONSE_BEGIN_DISPACHER_CARD, om);
    }
    
    //加入游戏桌
    public void addTable(GameTable table){
        logger.debug("table-"+table.getTableId()+":加入准备超时管理");
        tableList.add(table);
    }
    
    //移除游戏桌
    public void removeTable(GameTable table){
        logger.debug("table-"+table.getTableId()+":退出准备超时管理");
        tableList.remove(table);
    }
   

}
