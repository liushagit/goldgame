package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.Task;



public interface TaskAction {
	
    public Task getTaskByPlayerIdAndTaskId(int playerId,int taskId);
    
    public List<Task> getTaskListByPlayerId(int playerId);
    
    public void update(Task task);
    
    public void addDegree(int playerId,int taskId);
    
    
}
