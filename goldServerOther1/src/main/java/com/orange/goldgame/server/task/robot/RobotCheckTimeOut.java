package com.orange.goldgame.server.task.robot;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.server.task.robot.RobotRequestPool.NotifyItem;

public class RobotCheckTimeOut {
	private Logger log = LoggerFactory.getLogger(RobotCheckTimeOut.class);
	private Timer timer = new Timer();
	public void start() {
		GregorianCalendar calendar = new GregorianCalendar();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					Map<Integer, NotifyItem> robotRequestMap = RobotRequestPool.robotRequestMap;
					List<Integer> removeKeys = new ArrayList<Integer>();
					long now = 0;
					long timeout = 0;
					now = System.currentTimeMillis();
					long delay_time = 0;
					NotifyItem ni = null;
					for (Integer key : robotRequestMap.keySet()) {
						ni = robotRequestMap.get(key);
						timeout = now - ni.getRecvTime();
						delay_time = ni.getDelay_time();
						if (timeout >= delay_time) {
							removeKeys.add(key);
						}
					}
					for (int key : removeKeys) {
						RobotRequestPool.getInstance().getRobotRequestQueue().put(robotRequestMap.get(key));
						robotRequestMap.remove(key);
						log.debug("++++++++remove" + key + "|"+RobotRequestPool.getInstance().getRobotRequestQueue().size());
					}
				} catch (Exception e) {
					log.error("RobotCheckTimeOut",e);
				}
			}
		}, calendar.getTime(), 1 * 1000);
	}
}
