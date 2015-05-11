package com.orange.goldgame.server.task;

import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;

public class TimeOutTaskManager{

    private Logger log = LoggerFactory.getLogger(TimeOutTaskManager.class);
    private LinkedBlockingQueue<TimeOutTask> taskList = new LinkedBlockingQueue<TimeOutTask>();
    private static TimeOutTaskManager instance;
    
    public static TimeOutTaskManager getInstance(){
        if(instance == null) instance = new TimeOutTaskManager();
        return instance;
    }
    
    private TimeOutTaskManager(){
        super();
    }
    
    private static Timer timer = new Timer();  
    private GregorianCalendar calendar = new GregorianCalendar();
    
    public void runManager() {
        TimeOutTaskManager.getInstance().addTask(new MatchTimeOutTask());
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    
                    for(TimeOutTask task : taskList){
                        if(task.isTimeOut()){
                            task.handleTimeOut();
                        }
                    }
                    
                } catch (Exception e) {
                    log.error("超时任务异常", e);
                }
            }
        }, calendar.getTime(), 100);
    }
    
    
    public void addTask(TimeOutTask task){
        taskList.add(task);
    }
    
    public void removeTask(TimeOutTask task){
        taskList.remove(task);
    }
    
}
