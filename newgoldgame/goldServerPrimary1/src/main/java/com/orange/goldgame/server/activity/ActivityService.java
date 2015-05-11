package com.orange.goldgame.server.activity;

import java.util.Arrays;
import java.util.Calendar;

import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.manager.NoticeManager;
import com.orange.goldgame.util.DateUtil;

public class ActivityService {

	
	//////////////////////////双倍活动
	private static final String sb_begin = "20140430";
	private static final String sb_end = "20140514";
	private static final int inHours[] = {20,21};
//	private static final int inHours[] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
	//公告时间
	private static final int noticeHours[] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19};
	private static String MSG_IN_HOURS = "双倍金币活动现在进行中，赢得多，送的多，逾时不侯！";
	private static String MSG_NOTICE_HOURS = "今天的20:00至22:00，双倍金币奖励活动开始，快邀请小伙伴们来大赚一笔吧！";
	
	public static final int multiple = 2;
	
	/**
	 * 是否是多倍奖励
	 * @return
	 */
	public static boolean isDouble(){
		boolean in = DateUtil.isInDateRange(sb_begin, sb_end);
		if( !in ){
			return in;
		}
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		return Arrays.binarySearch(inHours, hour) >= 0;
	}
	
	/**
	 * 获取当前奖励倍数
	 * @return
	 */
	public static int getDouble(){
		if(!isDouble()){
			return 1;
		}
		return multiple;
	}
	
	/**
	 * 获取奖励金额
	 * @param winSource
	 * @param gamer
	 * @return
	 */
	public static float getDouble(int winSource,Gamer gamer){
		float db = getDouble();
		int other = winSource - gamer.getGolds();
		if(db > 1){
			return other;
		}else{
			return 0;
		}
		
	}
	/**
	 * 获取除掉自己押注奖励
	 * @param winSource
	 * @param gamer
	 * @return
	 */
	public static float getWinDouble(int winSource,Gamer gamer){
		return winSource - gamer.getGolds();
		
	}
	
	private static long last_send_time = 0;
	private static final int ONEMINUTE = 60 * 1000;
	
	/**
	 * 定时发送系统公告
	 * 活动时间内，每10分钟发送一次
	 * 活动时间外，整点发送一次
	 */
	public static void notice(){
		
		if(DateUtil.isInDateRange(sb_begin, sb_end)){
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			int minute = Calendar.getInstance().get(Calendar.MINUTE);
			long now = System.currentTimeMillis();
			if(Arrays.binarySearch(inHours, hour) >= 0){
				
				if(minute % 10 == 0 && now - last_send_time > ONEMINUTE){
					NoticeManager.getInstance().sendPublicMessage(null, MSG_IN_HOURS);
					last_send_time = System.currentTimeMillis();
				}
			}
			if(Arrays.binarySearch(noticeHours, hour) >= 0){
				if(minute == 0 && now - last_send_time > 30 * ONEMINUTE){
					NoticeManager.getInstance().sendPublicMessage(null, MSG_NOTICE_HOURS);
					last_send_time = System.currentTimeMillis();
				}
			}
		}
	}
}
