package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.TaskAction;
import com.orange.goldgame.domain.Task;
import com.orange.goldgame.domain.TaskExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.TaskDAO;
import com.orange.goldgame.system.dao.TaskDAOImpl;
import com.orange.goldgame.system.dbutil.DBService;

public class TaskActionImpl implements TaskAction{

    private Logger log = LoggerFactory.getLogger(TaskActionImpl.class);
    
    private TaskDAO taskDAO = (TaskDAO)DBManager.getDao(TaskDAOImpl.class);
    
    @Override
    public Task getTaskByPlayerIdAndTaskId(int playerId, int taskId) {
        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andPlayeridEqualTo(playerId)
                                    .andTaskidEqualTo(taskId);
        List<Task> taskList = null;
        try {
            taskList = taskDAO.selectByExample(taskExample);
            if(taskList == null || taskList.size()<=0){
                //初始化任务进度
                initTask(playerId,taskId);
                taskList = taskDAO.selectByExample(taskExample);
            }
        } catch (SQLException e) {
            log.error("获取任务进度列表有误", e);
        }
        return taskList.get(0);
    }
    
    private void initTask(int playerId, int taskId){
        Task task = new Task();
        task.setDegree(0+"");
        task.setPlayerid(playerId);
        task.setTaskid(taskId);
        task.setUpdatedate(new Date());
        task.setState(0);
        try {
            taskDAO.insert(task);
        } catch (SQLException e) {
            log.error("添加任务进度失败", e);
        }
    }

    @Override
    public List<Task> getTaskListByPlayerId(int playerId) {
        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andPlayeridEqualTo(playerId);
        List<Task> taskList = null;
        try {
            taskList = taskDAO.selectByExample(taskExample);
        } catch (SQLException e) {
            log.error("获取任务进度列表有误", e);
        }
        return taskList;
    }

    @Override
    public void update(Task task) {
        try {
//            taskDAO.updateByPrimaryKey(task);
        	DBService.commit(task);
        } catch (Exception e) {
            log.error("更新任务失败", e);
        }
    }

    @Override
    public void addDegree(int playerId,int taskId) {
        Task task = getTaskByPlayerIdAndTaskId(playerId, taskId);
        String deg = task.getDegree();
        int degint = Integer.parseInt(deg);
        degint++;
        task.setDegree(degint+"");
        update(task);
    }

}
