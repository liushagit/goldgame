package com.orange.goldgame.server.manager;

import java.util.HashMap;
import java.util.Map;

public class RunTimePropertyManager {
    
    private static final RunTimePropertyManager instance = new RunTimePropertyManager();
    
    
    private Map<String,Object> bufferMap = new HashMap<String,Object>();
    
    private static final String ISABLEGIVEGOLD = "isAbleGiveGold";
    private static final String ISSETROBOT = "isSetRobot";

    private RunTimePropertyManager(){
        bufferMap.put(ISSETROBOT, true);
        bufferMap.put(ISABLEGIVEGOLD, false);
    }
    
    
    public boolean isSetRobot() {
        return (Boolean)bufferMap.get(ISSETROBOT);
    }

    public void setRobot(boolean isSetRobot) {
        bufferMap.put(ISSETROBOT, isSetRobot);
    }
    
    
    public boolean isAbleGiveGold() {
        return (Boolean)bufferMap.get(ISABLEGIVEGOLD);
    }

    public void setAbleGiveGold(boolean isAbleGiveGold) {
        bufferMap.put(ISABLEGIVEGOLD, isAbleGiveGold);
    }

    public static RunTimePropertyManager getInstance() {
        return instance;
    }
    
}
