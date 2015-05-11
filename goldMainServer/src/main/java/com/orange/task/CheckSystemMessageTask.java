package com.orange.task;

import java.util.Timer;
import java.util.TimerTask;

import com.orange.core.ChatManager;
import com.orange.core.MemcachedResource;

/**
 * 每10分钟刷新系统广播
 * 
 * @author hqch
 * 
 */
public class CheckSystemMessageTask extends BaseTask {

	private static final String MSG_KEY = "sys_msg";
	
	private Timer timer;
	
	private static final int TIME = 1 * 1000 * 60 * 10;

	public CheckSystemMessageTask() {
		timer = new Timer();
		
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					startNoRepeat();
				} catch (Exception e) {
					logger.error("checkSystemMessageTask", e);
				}
			}
		}, 0,TIME);
	}

	@Override
	public void startNoRepeat() {
		logger.info("running checkSystemMessageTask....");
		String msg = MemcachedResource.get(MSG_KEY);
		if(msg == null){
			logger.info("have not system message");
			return;
		}
		String[] str = msg.split("#");
		ChatManager.getInstance().refreshMessage(str);
	}
}
