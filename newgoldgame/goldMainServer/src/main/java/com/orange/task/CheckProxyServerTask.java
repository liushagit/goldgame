package com.orange.task;

import java.util.Timer;
import java.util.TimerTask;

import com.orange.core.ServerManager;

/**
 * 每半小时监测目标服务器状态，可连接时，加入管理中
 * @author hqch
 *
 */
public class CheckProxyServerTask extends BaseTask {

	private Timer timer;
	private static ServerManager serverManager = ServerManager.getInstance();
	
	private static final int TIME = 1 * 1000 * 60 * 30;
	
//	private static final int TIME = 1 * 1000 * 60;
	
	public CheckProxyServerTask(){
		timer = new Timer();
		
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					startNoRepeat();
				} catch (Exception e) {
					logger.error("CheckProxyServerTask", e);
				}
			}
		}, TIME, TIME);
	}
	
	@Override
	public void startNoRepeat(){
		logger.info("running CheckProxyServerTask....");
		serverManager.checkProxyServer();
	}
}
