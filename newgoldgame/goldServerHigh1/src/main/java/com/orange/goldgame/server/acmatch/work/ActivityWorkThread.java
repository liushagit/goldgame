/**
 * 
 */
package com.orange.goldgame.server.acmatch.work;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.juice.orange.game.util.NameThreadFactory;

/**
 * @author guojiang
 *
 */
public class ActivityWorkThread {

	private static ActivityWorkThread instance = new ActivityWorkThread();
	private ExecutorService pool;
	private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new NameThreadFactory("ACTIVITY-WORK-THREAD"));
	public static ActivityWorkThread getInstance() {
		return instance;
	}
	public static void setInstance(ActivityWorkThread instance) {
		ActivityWorkThread.instance = instance;
	}
	private ActivityWorkThread(){
		pool = Executors.newCachedThreadPool(new NameThreadFactory("ACTIVITY-WORK-THREAD"));
	}
	public void begin(Thread thread){
		pool.execute(thread);
	}
}
