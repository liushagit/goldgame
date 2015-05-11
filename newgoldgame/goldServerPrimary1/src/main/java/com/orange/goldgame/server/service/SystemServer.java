package com.orange.goldgame.server.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.PlayerInfoCenter;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;

/**
 * 
 * @author wuruihuang 2013.5.24
 *
 */
public class SystemServer {
	private static final Logger logger = LoggerFactory.getLogger(SystemServer.class);
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 86  玩家请求系统信息或者是个人信息（即好友消息）或者是兑换信息
	 */
	public void requestMessageList(SocketRequest request, SocketResponse response) throws JuiceException {
	    InputMessage in= request.getInputMessage();
	    int playerId = in.getInt();
	    logger.debug("玩家查看个人信息:"+playerId);
	    List<PlayerInfoCenter>  list = PlayerInfoCenterManager.getInstance()
	            .getPlayerInfoCeterListByPlayerId(playerId);
	    OutputMessage om = new OutputMessage();
	    om.putShort((short)list.size());
	    for(PlayerInfoCenter pic : list){
	        om.putString(df.format(pic.getCreateTime()));
	        om.putString(pic.getContent());
	        om.putByte((byte)pic.getMessageType().intValue());
	        om.putString(pic.getMessageInfo());
	    }
	    request.getSession().sendMessage(Protocol.RESPONSE_MESSAGEINTO_LIST, om);
	}
	
}
