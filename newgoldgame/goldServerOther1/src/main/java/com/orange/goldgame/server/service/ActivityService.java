package com.orange.goldgame.server.service;

import java.util.HashMap;
import java.util.Map;

import com.orange.goldgame.vo.ActivityConfigVo;


public class ActivityService {

	public static final String beginTime = "20140430";
	public static final String endTime = "20140514";
	
	private static Map<Integer, String> guoqingMap = new HashMap<Integer, String>();
	private static Map<Integer ,ActivityConfigVo> activityVos = new HashMap<Integer, ActivityConfigVo>();
	public static Map<Integer, ActivityConfigVo> getActivityVos() {
		return activityVos;
	}
	public static void setActivityVos(Map<Integer, ActivityConfigVo> activityVos) {
		ActivityService.activityVos = activityVos;
	}
	static{
//		活动方式一:
//		首次充值且消费,获得换牌卡2张。
//		单次充值20元，再送50000金币、换牌卡*2
//		单次充值50元，再送1500000金币、换牌卡*5
//		单次充值100元，再送250000金币、换牌卡*10
//		单次充值500元，再送600000金币、换牌卡*20、4倍卡*10
//		累计充值每满100元，送换牌卡、4倍卡、8倍卡各五张
//		guoqingMap.put(-1, "0#5_2");
//		guoqingMap.put(-100, "0#5_5|6_5|7_5");
//		guoqingMap.put(20, "50000#5_2");
//		guoqingMap.put(50, "150000#5_5");
//		guoqingMap.put(100, "250000#5_10");
//		guoqingMap.put(500, "600000#5_20|6_10");
		
//		活动方式二:
//		单次充值20元，再送价值2元的宝石。
//		单次充值50元，再送价值6元的宝石。
//		单次充值100元，再送价值15元的宝石。
//		单次充值500元,再送价值100元的宝石。
//		累计充值每满500元，再送价值50元的宝石。
		guoqingMap.put(-500, "50");
		guoqingMap.put(20, "2");
		guoqingMap.put(50, "6");
		guoqingMap.put(100, "15");
		guoqingMap.put(500, "100");
		
		ActivityConfigVo vo = null;
		for (Integer key : guoqingMap.keySet()) {
			String s = guoqingMap.get(key);
			vo = new ActivityConfigVo();
			vo.setGolds(Integer.parseInt(s));
			activityVos.put(key, vo);
		}

		
		
//		活动方式一:
//		String tmp[] = null;
//		String props[] = null;
//		String pp[] = null;
//		ActivityConfigVo vo = null;
//		for (Integer key : guoqingMap.keySet()) {
//			String s = guoqingMap.get(key);
//			vo = new ActivityConfigVo();
//			tmp = s.split("#");
//			vo.setGolds(Integer.parseInt(tmp[0]));
//			if (tmp.length > 1) {
//				props = tmp[1].split("\\|");
//				for (int i = 0; i < props.length; i++) {
//					pp = props[i].split("_");
//					Map<Integer, Integer> map = vo.getProps();
//					if (map == null) {
//						map = new HashMap<Integer, Integer>();
//						vo.setProps(map);
//					}
//					map.put(Integer.parseInt(pp[0]), Integer.parseInt(pp[1]));
//				}
//			}
//			activityVos.put(key, vo);
//		}
		
	}
	
	
}
