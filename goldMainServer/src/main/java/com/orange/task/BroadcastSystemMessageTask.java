package com.orange.task;

import java.util.Timer;
import java.util.TimerTask;

import com.orange.core.ChatManager;
import com.orange.core.Message;
import com.orange.core.ServerManager;
import com.orange.core.proxy.ChatMessage;

/**
 * 每分钟进行系统广播
 * 
 * @author hqch
 * 
 */
public class BroadcastSystemMessageTask extends BaseTask {

	private Timer timer;
	
	private static final int TIME = 1 * 1000 * 60;

	public BroadcastSystemMessageTask() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					startNoRepeat();
				} catch (Exception e) {
					logger.error("BroadcastSystemMessageTask", e);
				}
			}
		}, TIME,TIME);
	}

	@Override
	public void startNoRepeat() {
		logger.info("running BroadcastSystemMessageTask....");
		
		ChatMessage msg = ChatManager.getInstance().randomSystemMessage();
		if(msg != null){
			ServerManager.getInstance().broadcastMessage(ChatMessage.SYS_USER_ID,
					Message.CHAT_PROTOCOL,System.currentTimeMillis(),msg.getSystemMsg());
		}
	}
}
