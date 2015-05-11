package com.orange.goldgame.server.service;

import java.util.List;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.Task;
import com.orange.goldgame.domain.TaskConfig;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.manager.TaskManager;
/**
 * @author wuruihuang 2013.5.22
 */
public class TaskServer extends BaseServer{
	/**
	 * 11 玩家请求任务列表
	 */
	public void requestTasks(SocketRequest request, SocketResponse response) throws JuiceException {
	    InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        Player player = PlayerService.getPlayer(playerId, request.getSession());
        OutputMessage om = new OutputMessage();
        List<TaskConfig> taskConfigList = TaskManager.getInstance().showTaskByPlayer(player);
        om.putShort((short)taskConfigList.size());
        for(TaskConfig config : taskConfigList){
            om.putInt(config.getId());
            om.putString(config.getTaskName());
            om.putString(config.getIntroduction());
            om.putInt(Integer.parseInt(config.getAward()));
            Task task = TaskManager.getInstance().getTask(playerId, config.getId());
            boolean conplex = TaskManager.getInstance().isComplex(playerId, task);
            om.putByte(conplex?(byte)1:0);
            String type = config.getTaskType();
            om.putByte(Byte.parseByte(type.split("_")[0]));
            byte model = Byte.parseByte(type.split("_")[1]);
            om.putByte(model);
            om.putInt(TaskManager.getInstance().getTaskCount(playerId, config.getId()));
            om.putInt(TaskManager.getInstance().getTaskConfigLimit(config.getId()));
        }
        request.getSession().sendMessage(Protocol.RESPONSE_SYSTEM_TASK_LIST, om);
	}
	/**
	 * 13 玩家请求领取任务奖励
	 */
	public void requestReceiveTaskAword(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage msg = request.getInputMessage();
		int playerId = msg.getInt();
		int taskId = msg.getInt();
		Player player = PlayerService.getPlayer(playerId, request.getSession());
		Task task = TaskManager.getInstance().getTask(playerId, taskId);
		boolean conplex = TaskManager.getInstance().isComplex(playerId, task);
		if(!conplex){
		    sendErrorMsg(request.getSession(), (short)1, "您还未达到任务条件，请继续努力哦！");
		}else{
		    int gold = TaskManager.getInstance().receiveReward(player, taskId);
		    OutputMessage om = new OutputMessage();
		    om.putInt(gold);
		    om.putInt(GoldService.getLeftCopper(player));
		    List<TaskConfig> taskConfigList = TaskManager.getInstance().showTaskByPlayer(player);
	        om.putShort((short)taskConfigList.size());
	        for(TaskConfig config : taskConfigList){
	            om.putInt(config.getId());
	            om.putString(config.getTaskName());
	            om.putString(config.getIntroduction());
	            om.putInt(Integer.parseInt(config.getAward()));
	            task = TaskManager.getInstance().getTask(playerId, config.getId());
	            boolean conplex1 = TaskManager.getInstance().isComplex(playerId, task);
	            om.putByte(conplex1?(byte)1:0);
	            String type = config.getTaskType();
	            om.putByte(Byte.parseByte(type.split("_")[0]));
	            byte model = Byte.parseByte(type.split("_")[1]);
	            om.putByte(model);
	        }
	        request.getSession().sendMessage(Protocol.RESPONSE_TASK_AWARD, om);
		}
		
	}
	
}
