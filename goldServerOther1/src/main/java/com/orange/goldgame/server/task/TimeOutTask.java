package com.orange.goldgame.server.task;

public interface TimeOutTask {
    
    public boolean isTimeOut();
    
    public void handleTimeOut();
    
}
