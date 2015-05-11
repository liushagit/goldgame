package com.orange.goldgame.server.util;


public class TimeRecord{
	
	public static long nextTime(long lastTime,int minute){
		int mu=minute*60*1000;
		long nextTime=lastTime+mu;
		return nextTime;
		
	}
	
	
	
	
	
	

}
