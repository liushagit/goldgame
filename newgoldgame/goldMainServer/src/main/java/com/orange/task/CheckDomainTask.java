package com.orange.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.orange.core.DomainManager;

/**
 * 每天监测配置DOMAIN信息，可连接时，加入管理中
 * 
 * @author hqch
 * 
 */
public class CheckDomainTask extends BaseTask {

	private Timer timer;
	
	private Date date;

	private static final int HOUR = 23;
	
	private static final int MIN = 59;
	
	private static final int SEC = 59;
	
//	private static final int TIME = 1 * 1000 * 60 * 60 * 24;
	
	private static final int TIME = 1 * 1000 * 60;

	public CheckDomainTask() {
		timer = new Timer();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(year, month, day, HOUR, MIN, SEC);
		date = calendar.getTime();
		
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					startNoRepeat();
				} catch (Exception e) {
					logger.error("checkDomainTask", e);
				}
			}
		}, date, TIME);
	}

	@Override
	public void startNoRepeat() {
		logger.info("running checkDomainTask....");
		DomainManager.getInstance().init(true);
	}
}
