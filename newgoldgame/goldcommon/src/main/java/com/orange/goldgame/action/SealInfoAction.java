package com.orange.goldgame.action;

import com.orange.goldgame.domain.SealInfo;


public interface SealInfoAction {
    
    public void insert(SealInfo info);
    
    public SealInfo getSealInfo(int playerId);
    
    public void updateSealInfo(SealInfo info);
    
}
