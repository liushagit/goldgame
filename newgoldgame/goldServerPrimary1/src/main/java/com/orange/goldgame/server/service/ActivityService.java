package com.orange.goldgame.server.service;

import java.util.HashMap;
import java.util.Map;

import com.orange.goldgame.vo.ActivityConfigVo;


public class ActivityService {

	public static final String beginTime = "20130916";
	public static final String endTime = "20131015";
	
	private static Map<Integer, String> guoqingMap = new HashMap<Integer, String>();
	private static Map<Integer ,ActivityConfigVo> activityVos = new HashMap<Integer, ActivityConfigVo>();
	public static Map<Integer, ActivityConfigVo> getActivityVos() {
		return activityVos;
	}
	public static void setActivityVos(Map<Integer, ActivityConfigVo> activityVos) {
		ActivityService.activityVos = activityVos;
	}
	static{
		guoqingMap.put(20, "50000");
		guoqingMap.put(50, "150000");
		guoqingMap.put(100, "250000#5_10");
		guoqingMap.put(200, "600000#5_25|6_10");
		guoqingMap.put(500, "1500000#5_60|7_25|8_10");
		
		String tmp[] = null;
		String props[] = null;
		String pp[] = null;
		ActivityConfigVo vo = null;
		for (Integer key : guoqingMap.keySet()) {
			String s = guoqingMap.get(key);
			vo = new ActivityConfigVo();
			tmp = s.split("#");
			vo.setGolds(Integer.parseInt(tmp[0]));
			if (tmp.length > 1) {
				props = tmp[1].split("\\|");
				for (int i = 0; i < props.length; i++) {
					pp = props[i].split("_");
					Map<Integer, Integer> map = vo.getProps();
					if (map == null) {
						map = new HashMap<Integer, Integer>();
						vo.setProps(map);
					}
					map.put(Integer.parseInt(pp[0]), Integer.parseInt(pp[1]));
				}
			}
			activityVos.put(key, vo);
		}
		
	}
}
