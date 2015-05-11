package com.orange.goldgame.system.ao;

import com.orange.goldgame.domain.ExpectConsumeLog;

public class ExpectConsumeLogAO extends BaseAO{
    
    public void addLog(ExpectConsumeLog log){
        this.factory.getExpectConsumeLogDAO().addLog(log);
    }
    
}
