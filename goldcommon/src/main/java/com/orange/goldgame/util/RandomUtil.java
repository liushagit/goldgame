package com.orange.goldgame.util;

import java.util.Map;
import java.util.Random;

/**
 * 概率工具类
 * @author small
 *
 */
public class RandomUtil {
	/**
	 * 根据key，value获取相应key
	 * key：需要获得的关键字，
	 * value：关键字所占的百分比
	 * @param keyChanceMap
	 * @return
	 */
	public static String getPreKey(Map<String, Integer> keyChanceMap) {
		if (keyChanceMap == null || keyChanceMap.size() == 0){
			return null;
		}
		Integer sum = 0;
		for (Integer value : keyChanceMap.values()) {
			sum += value;
		}
		// 从1开始
		Integer rand = new Random().nextInt(sum) + 1;

		for (Map.Entry<String, Integer> entry : keyChanceMap.entrySet()) {
			rand -= entry.getValue();
			// 选中
			if (rand <= 0) {
				return entry.getKey();
			}
		}
		return null;
	}
}
