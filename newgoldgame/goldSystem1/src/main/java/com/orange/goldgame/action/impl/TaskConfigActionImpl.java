package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.TaskConfigAction;
import com.orange.goldgame.domain.TaskConfig;
import com.orange.goldgame.domain.TaskConfigExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.TaskConfigDAO;
import com.orange.goldgame.system.dao.TaskConfigDAOImpl;

public class TaskConfigActionImpl implements TaskConfigAction{
    
    private Logger log = LoggerFactory.getLogger(TaskConfigActionImpl.class);
    
    private TaskConfigDAO taskConfigDAO = (TaskConfigDAO)DBManager.getDataDao(TaskConfigDAOImpl.class);

    @Override
    public List<TaskConfig> getAll() {
        try {
            return taskConfigDAO.selectByExample(null);
        } catch (SQLException e) {
            log.error("获取任务列表出错", e);
        }
        return null;
    }

    @Override
    public TaskConfig getTaskConfigById(int id) {
        try {
            return taskConfigDAO.selectByPrimaryKey(id);
        } catch (SQLException e) {
            log.error("获取任务列表出错", e);
        }
        return null;
    }

    @Override
    public List<TaskConfig> getTaskListByType(String type) {
        TaskConfigExample example = new TaskConfigExample();
        example.createCriteria().andTaskTypeEqualTo(type);
        try {
            return taskConfigDAO.selectByExample(example);
        } catch (SQLException e) {
            log.error("获取任务列表出错", e);
        }
        return null;
    }

    
    

}
