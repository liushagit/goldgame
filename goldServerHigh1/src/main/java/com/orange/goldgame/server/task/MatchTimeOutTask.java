package com.orange.goldgame.server.task;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.MatchTableSetManager;
import com.orange.goldgame.server.manager.GameTableManager;

public class MatchTimeOutTask implements TimeOutTask{

    //比赛开始确认超时
    private static final int MATCH_COMFIRM_LIMIT_TIME = 4;
    
    private Logger log = LoggerFactory.getLogger(MatchTimeOutTask.class);
    
    @Override
    public boolean isTimeOut() {
        return true;
    }

    @Override
    public void handleTimeOut() {
        Long currentTime = System.currentTimeMillis();
        Iterator<GameTable> ite = MatchTableSetManager.getInstance().getMatchTableList().iterator();
        while(ite.hasNext()){
            GameTable table = ite.next();
            if(table!=null){
            	if(GameTableManager.isMatch(table) && !table.isDealCard()
            			&& currentTime - table.getLastOperationTime() > MATCH_COMFIRM_LIMIT_TIME*1000){
            		log.debug("比赛开始超时");
            		try{
            			GameTableManager.startMatch(table);
            			table.resetDealCardNum();
            		} catch(Exception e){
            			log.error("开始比赛抛异常", e);
            			MatchTableSetManager.getInstance().removeMatchTable(table);
            		}
            	}           	
            }
        }
    }

}
