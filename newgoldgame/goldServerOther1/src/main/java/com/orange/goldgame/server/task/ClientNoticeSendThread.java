package com.orange.goldgame.server.task;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.server.domain.SendMessage;
import com.orange.goldgame.server.manager.PlayerInfoCenterManager;

public class ClientNoticeSendThread extends Thread{
	
	private static Logger logger = LoggerFactory.getLogger(ClientNoticeSendThread.class);

	@Override
	public void run() {
		LinkedBlockingDeque<SendMessage> msgQueue = null;
		while(true){
			msgQueue = ClientNoticeTask.getInstance().getMessages();
			try{
				SendMessage msg =msgQueue.poll(100, TimeUnit.MILLISECONDS);
				if(msg != null){
					int playerId = msg.getPlayerId();
					String message = msg.getMsg();
					PlayerInfoCenterManager.getInstance().addPlayerInfoCenter(playerId,true,Constants.SPECIAL_INFO,"",message);
				}
			}catch(Exception e){
				logger.error("client notice send exception .....");
			}
		}
	}
	
	

}
