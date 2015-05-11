package com.orange.goldgame.server.task;

import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.server.wheel.WheelFortuneService;

public class WheelTask {
	private Timer timer = new Timer();
	private Logger log = LoggerFactory.getLogger(WheelTask.class);
	/**
	 * 计时器启动
	 * 每分钟清理一次
	 */
	public void start() {
		GregorianCalendar calendar = new GregorianCalendar();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					WheelFortuneService.sendMessage(null,null);
				} catch (Exception e) {
				    log.error("CleanTaskError", e);
				}
			}
		}, calendar.getTime(), 10 * 1000);
	}
}
