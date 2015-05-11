/**
 * CardGameServer
 * com.card.game.mt.domain
 * GameTable.java
 */
package com.orange.goldgame.server.domain;

import java.util.List;
import java.util.Vector;


/**
 * @author yesheng
 */
public class MatchTableSetManager {
	
    private List<GameTable> matchTableList = new Vector<GameTable>();
    
    private static MatchTableSetManager instance = new MatchTableSetManager();
    
    public static MatchTableSetManager getInstance(){
        return instance;
    }
    
    public boolean isExist(GameTable matchTable){
        if(matchTableList.contains(matchTable)){
            return true;
        }
        return false;
    }
    
    public void addMatchTable(GameTable matchTable){
        if(matchTable != null){
            matchTableList.add(matchTable);
        }
    }
    
    public void removeMatchTable(GameTable matchTable){
        if(matchTable != null){
            matchTableList.remove(matchTable);
        }
    }
    
    public int getTableSize(){
        return matchTableList.size();
    }

    public List<GameTable> getMatchTableList() {
        return matchTableList;
    }
}
