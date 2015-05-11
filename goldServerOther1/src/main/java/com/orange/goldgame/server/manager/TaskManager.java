package com.orange.goldgame.server.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.orange.goldgame.action.TaskAction;
import com.orange.goldgame.action.TaskConfigAction;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.Task;
import com.orange.goldgame.domain.TaskConfig;
import com.orange.goldgame.domain.TaskType;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.service.GoldService;
import com.orange.goldgame.util.DateUtil;



/**
 * 
 * 游戏任务管理器
 * @author yesheng
 *
 */
public class TaskManager {
    
    private static final TaskManager instance = new TaskManager();
    
    private TaskAction taskAction = BaseEngine.getInstace().getTaskActionIpml();
    private TaskConfigAction taskConfigAction = BaseEngine.getInstace().getTaskConfigActionImpl();
    
    
    public static TaskManager getInstance(){
        return instance;
    }
    
    private TaskManager(){
        
    }
    
    /**
     * 根据Player来筛选任务
     * @param player
     * @return
     */
    public List<TaskConfig> showTaskByPlayer(Player player){
        List<TaskConfig> list = new ArrayList<TaskConfig>();
        List<TaskConfig> taskConfigList = taskConfigAction.getAll();
        
        String taskType = "";
        for(TaskConfig config : taskConfigList){
            Task task = taskAction.getTaskByPlayerIdAndTaskId(player.getId(), config.getId());
            isComplex(player.getId() ,task.getTaskid());
            if(!taskType.equals(config.getTaskType()) && task.getState()==0){
                taskType = config.getTaskType();
                list.add(config);
            }
        }
        return list;
    }
    
    /**
     * 返回任务列表
     * @return
     */
    public List<TaskConfig> getTaskList(){
        List<TaskConfig> list = taskConfigAction.getAll();
        return list;
    }
    
    /**
     * 获得任务进度对象
     * @param taskId
     * @return
     */
    public Task getTask(int playerId ,int taskId){
        return taskAction.getTaskByPlayerIdAndTaskId(playerId, taskId);
    }
    
    /**
     * 获得任务进度
     * @param playerId
     * @param taskId
     * @return
     */
    public int getTaskCount(int playerId ,int taskId){
        Task task = TaskManager.getInstance().getTask(playerId, taskId);
        String degreeStr = task.getDegree();
        String[] degrees = degreeStr.split("_");
        int count = Integer.parseInt(degrees[0]);
        return count;
    }
    
    public int getTaskConfigLimit(int taskId){
        TaskConfig taskConfig = taskConfigAction.getTaskConfigById(taskId);
        String condiStr = taskConfig.getConditionLimit();
        String[] condis = condiStr.split("_");
        int condi = Integer.parseInt(condis[0]);
        return condi;
    }
    
    /**
     * 是否已完成
     */
    public boolean isComplex(int playerId ,int taskId){
        Task task = taskAction.getTaskByPlayerIdAndTaskId(playerId, taskId);
        TaskConfig taskConfig = taskConfigAction.getTaskConfigById(taskId);

        String[] taskType = taskConfig.getTaskType().split("_");
        int daily = Integer.parseInt(taskType[0]);
        Date update = task.getUpdatedate();
        //如果是每日任务，如果更新时间不是今天，重置所有进度
        if(daily==1){
            if(!DateUtil.isSameDay(update.getTime(), System.currentTimeMillis())){
                task.setDegree("0");
                task.setUpdatedate(new Date());
                task.setState(0);
                taskAction.update(task);
            }
        }
        String degreeStr = task.getDegree();
        String[] degrees = degreeStr.split("_");
        String condiStr = taskConfig.getConditionLimit();
        String[] condis = condiStr.split("_");
        int count = Integer.parseInt(degrees[0]);
        int condi = Integer.parseInt(condis[0]);
        
        if(count>=condi){
            return true;
        }
        return false;
    }
    
    /**
     * 领取任务奖励
     * @param player
     * @param taskId
     */
    public int receiveReward(Player player ,int taskId){
        TaskConfig config = taskConfigAction.getTaskConfigById(taskId);
        Task task = taskAction.getTaskByPlayerIdAndTaskId(player.getId(), taskId);
        
        task.setState(1);
        taskAction.update(task);
        
        int copper = Integer.parseInt(config.getAward());
        GoldService.addCopperAndUpdateGamer(player, copper);
        return copper;
    }
    
    /**
     * 驱动玩家信息任务进度
     * @param type
     */
    public void drivePlayerInfoRate(Player player ,int type){
        driveTaskRateById(player,12);
        driveTaskRateById(player,13);
    }
    
    /**
     * 驱动商城任务进度
     * @param player
     * @param type
     */
    public void driveShopTaskTate(Player player ,int type){
        switch(type){
            case 6:
                driveTaskRateById(player,14);
                break;
            case 7:
                driveTaskRateById(player,16);
                break;
        }
    }
    
    
    private void driveTaskRateById(Player player ,int taskId){
        List<TaskConfig> taskConfigList = new ArrayList<TaskConfig>();
        TaskConfig config = taskConfigAction.getTaskConfigById(taskId);
        if(config!=null){
            taskConfigList.add(config);
        }
        driveTaskList(player,taskConfigList);
    }
    
    /**
     * 驱动任务进度(胜负)
     * @param player
     */
    public void driveTaskRate(Player player,int iswin) {
    	if (player.getId() < 0) {
			return;
		}
        List<TaskConfig> taskConfigList = new ArrayList<TaskConfig>();
        //推进系统任务
        taskConfigList = taskConfigAction.getTaskListByType(TaskType.RACE_TATOL_TYPE);
        driveTaskList(player,taskConfigList);
        //推进每日任务
        taskConfigList = taskConfigAction.getTaskListByType(TaskType.RACE_TATOL_TYPE_DAILY);
        driveTaskList(player,taskConfigList);
        
        if(iswin==1){
            //推进系统任务
            taskConfigList = taskConfigAction.getTaskListByType(TaskType.RACE_TYPE);
            driveTaskList(player,taskConfigList);
            //推进每日任务
            taskConfigList = taskConfigAction.getTaskListByType(TaskType.RACE_TYPE_DAILY);
            driveTaskList(player,taskConfigList);
        }
    }
    
    //推进玩家的任务列表
    private void driveTaskList(Player player,List<TaskConfig> taskConfigList){
        for(TaskConfig config :taskConfigList){
            
            if(isComplex(player.getId(), config.getId())){
                continue;
            }
            taskAction.addDegree(player.getId(), config.getId());
            break;
        }
    }
}
