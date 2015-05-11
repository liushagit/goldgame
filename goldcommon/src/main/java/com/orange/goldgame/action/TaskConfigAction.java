package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.TaskConfig;

public interface TaskConfigAction {
    
    /**
     * 获取所有的TaskConfig
     * @return
     */
    public List<TaskConfig> getAll();
    
    public TaskConfig getTaskConfigById(int id);
    
    public List<TaskConfig> getTaskListByType(String type);
    
    
}
