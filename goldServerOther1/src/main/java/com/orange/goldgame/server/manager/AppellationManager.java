package com.orange.goldgame.server.manager;

import java.util.List;

import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AppellationConfig;
import com.orange.goldgame.domain.Player;

public class AppellationManager {
    
    private AppellationManager(){}
    
    private static AppellationManager instance = new AppellationManager();
    
    public static AppellationManager getInstance(){
        return instance;
    }
    
    public String getAppellation(int amount){
        List<AppellationConfig> list = ResourceCache.getInstance().getAppellationConfigs();
        
        for(AppellationConfig config : list){
            if(amount>=config.getAmount()){
                return config.getAppellationName();
            }
        }
        return "";
    }
    
    public void changeAppellation(Player player,int amount){
        String appellation = getAppellation(amount);
        player.setAppellation(appellation);
    }
    
}
