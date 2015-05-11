package com.orange.goldgame.server.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.SealInfo;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.NoticeManager;
import com.orange.goldgame.server.manager.RobotManager;
import com.orange.goldgame.server.manager.RunTimePropertyManager;
import com.orange.goldgame.server.manager.SessionManger;

public class AdminServer {

	private final Logger log = LoggerFactory.getLogger(AdminServer.class);
	/**
	 * 获取在线人数
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void getOnlineNum(SocketRequest request, SocketResponse response) throws JuiceException {
		int onle_num = SessionManger.getInstance().getGameSessions().size();
		OutputMessage om = new OutputMessage();
		om.putInt(onle_num);
		response.sendMessage(Protocol.RESPONSE_ONLENUM, om);
	}
	
	/**
	 * 关闭或开启机器人
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void closeOpenRobot(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage im = request.getInputMessage();
		int close = im.getInt();
		if (close == 1) {
			RobotManager.getInstance().setUseRobot(true);
		}else {
			RobotManager.getInstance().setUseRobot(false);
		}
		OutputMessage om = new OutputMessage();
		om.putString(RobotManager.getInstance().isUseRobot()?"used":"closed");
		response.sendMessage(Protocol.RESPONSE_CLOSEROBOT, om);
	}
	
	public void sendNotice(SocketRequest request, SocketResponse response) throws JuiceException {
	    InputMessage im = request.getInputMessage();
	    String content = im.getUTF();
	    NoticeManager.getInstance().sendPublicMessage(null, content);
	    OutputMessage om = new OutputMessage();
        om.putString("发送成功！");
        response.sendMessage(Protocol.RESPONSE_SENDSYSTEM_NOTICE, om);
	}
	
	/**
	 * 关闭或开启好友赠送
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void closePressentGolds(SocketRequest request, SocketResponse response) throws JuiceException {
		InputMessage im = request.getInputMessage();
		int close = im.getInt();
		RunTimePropertyManager.getInstance().setAbleGiveGold(close == 1?true:false);
		OutputMessage om = new OutputMessage();
		om.putString(RunTimePropertyManager.getInstance().isAbleGiveGold()?"present":"closed");
		response.sendMessage(Protocol.RESPONSE_CLOSEROBOT, om);
	}
	
	/**
	 * 封号处理
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public void sealAccount(SocketRequest request, SocketResponse response) throws JuiceException {
	    InputMessage im = request.getInputMessage();
	    int playerId = im.getInt();
	    int type = im.getInt();
	    String reason = im.getUTF();
	    //封号
	    if(type==Constants.PLAYER_STATUS_SEAL){
	        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
	        player.setStatus(Constants.PLAYER_STATUS_SEAL);
	        BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
	        
	        SealInfo info = new SealInfo();
	        info.setPlayerId(playerId);
	        info.setSealReason(reason);
	        info.setSealTime(new Date());
	        BaseEngine.getInstace().getSealInfoAction().insert(info);
	    }
	    //解封
	    else if(type==0){
	        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
            player.setStatus(0);
            BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
	    }
	    //禁言
	    else if(type==Constants.PLAYER_STATUS_BAN_SYSTEM){
	        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
            player.setStatus(Constants.PLAYER_STATUS_BAN_SYSTEM);
            BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
            
            SealInfo info = new SealInfo();
            info.setPlayerId(playerId);
            info.setSealReason(reason);
            info.setSealTime(new Date());
            BaseEngine.getInstace().getSealInfoAction().insert(info);
	    }
	    OutputMessage om = new OutputMessage();
	    om.putString("success");
	    response.sendMessage(Protocol.RESPONSE_SEAL, om);
	}
	
	public void adminPay(SocketRequest request, SocketResponse response)throws JuiceException{
		InputMessage im = request.getInputMessage();
	    String imei = im.getUTF();
	    String pay_id = im.getUTF();
	    String info = im.getUTF();
	    String orderid = im.getUTF();
	    
	    ErrorCode res = SDKPayService.pay(imei, pay_id, info, orderid);
	    OutputMessage om = new OutputMessage();
	    om.putString(res.getStatus()+"");
	    response.sendMessage(Protocol.RESPONSE_PAY, om);
	}
}
