package com.orange.goldgame.business.entity;

import java.util.HashMap;
import java.util.Map;

public class GameContextMatch extends GameContext{

    //最高场次
    public static final int MAXINNING = 8;
    
    private Map<Integer ,Integer> gamerLeftGoldMap = new HashMap<Integer,Integer>();
    
    public Map<Integer, Integer> getGamerLeftGoldMap() {
        return gamerLeftGoldMap;
    }
    
    
}
