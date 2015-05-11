package com.orange.task;

import java.util.Timer;
import java.util.TimerTask;

import com.orange.core.ServerManager;

/**
 * 每五分钟监测服务器session状态，并清理
 * @author hqch
 *
 */
public class CheckSessionTask extends BaseTask {

	private Timer timer;
	private static ServerManager serverManager = ServerManager.getInstance();
	
	private static final int TIME = 1 * 1000 * 60 * 5;
	
//	private static final int TIME = 1 * 1000 * 60;
	
	public CheckSessionTask(){
		timer = new Timer();
		
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					startNoRepeat();
				} catch (Exception e) {
					logger.error("CheckSessionTask", e);
				}
			}
		}, TIME, TIME);
	}
	
	@Override
	public void startNoRepeat() {
		logger.info("running CheckSessionTask....");
		serverManager.clearSession();
	}
}
