package com.orange.goldgame.server.task.robot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.Gamer;

public class RobotRequestPool {
    private static Logger log = LoggerFactory.getLogger(RobotRequestPool.class);
	private static final RobotRequestPool instance = new RobotRequestPool();
	public static RobotRequestPool getInstance() {
		return instance;
	}
	private RobotRequestPool(){}
	
	/**
	 * 只存放时间没到的请求
	 */
	public static Map<Integer, NotifyItem> robotRequestMap = new ConcurrentHashMap<Integer, RobotRequestPool.NotifyItem>();
	
	/**
	 * 只存放时间到的请求
	 */
	private LinkedBlockingQueue<NotifyItem> robotRequestQueue = new LinkedBlockingQueue<NotifyItem>();
	
	public LinkedBlockingQueue<NotifyItem> getRobotRequestQueue() {
		return robotRequestQueue;
	}
//	public Map<Integer, NotifyItem> getRobotRequestMap() {
//		return robotRequestMap;
//	}
	public boolean isExists(Gamer gamer){
//		for (NotifyItem ni  : robotRequestQueue) {
//			if (ni != null && ni.getGamer().getPlayerId() == gamer.getPlayerId()) {
//				return true;
//			}
//		}
		return robotRequestMap.containsKey(gamer.getPlayerId());
	}
	public void addRequest(GameTable table,GameRoom room,Gamer gamer,int delay_time){
	    this.addRequest(table, room, gamer, delay_time, 0);
	    NotifyItem ni = robotRequestMap.get(gamer.getPlayerId());
	    if (ni != null) {
	    	log.debug("addgamer:"+gamer.getPlayerId() + "===="+ni.getGamer().getPlayerId() +"|"+ ni.getDelay_time());
			
		}else {
			log.debug("addgamer:"+gamer.getPlayerId() + "====null");
		}
	}
	
	public void addRequest(GameTable table,GameRoom room,Gamer gamer,int delay_time,int operation){
        NotifyItem ni = new NotifyItem();
        ni.setRecvTime(System.currentTimeMillis());
        ni.setTable(table);
        ni.setRoom(room);
        ni.setGamer(gamer);
        ni.setDelay_time(delay_time);
        ni.setOperation(operation);
//        robotRequestQueue.add(ni);
        if (robotRequestMap.containsKey(gamer.getPlayerId())) {
        	robotRequestMap.put(gamer.getPlayerId() - 1024, ni);
		}else {
			robotRequestMap.put(gamer.getPlayerId(), ni);
		}
    }
	
	
	class NotifyItem {
		GameTable table;
		GameRoom room;
		Gamer gamer;
		int delay_time;
		long recvTime;
		int operation;
		
		public GameTable getTable() {
			return table;
		}

		public void setTable(GameTable table) {
			this.table = table;
		}

		public GameRoom getRoom() {
			return room;
		}

		public void setRoom(GameRoom room) {
			this.room = room;
		}

		public Gamer getGamer() {
			return gamer;
		}

		public void setGamer(Gamer gamer) {
			this.gamer = gamer;
		}

		public int getDelay_time() {
			return delay_time;
		}

		public void setDelay_time(int delay_time) {
			this.delay_time = delay_time;
		}

		public long getRecvTime() {
			return recvTime;
		}

		public void setRecvTime(long recvTime) {
			this.recvTime = recvTime;
		}

        public int getOperation() {
            return operation;
        }

        public void setOperation(int operation) {
            this.operation = operation;
        }
	}
}
