package com.orange.goldgame.dbutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orange.goldgame.core.dao.ConsumDAO;
import com.orange.goldgame.core.dao.ConsumDAOImpl;
import com.orange.goldgame.core.model.Consum;
import com.orange.goldgame.core.model.ConsumExample;
import com.orange.goldgame.util.DateUtil;
import com.orange.goldgame.util.SqlMapConfig;

public class DBManager {
	/******************************************************************************************
	 * DAO 缓存
	 * ****************************************************************************************/
	private static Map<String, Object> daoMap = new HashMap<String, Object>();

	
	public static Object getDao(Class clazz) {
		Object o = daoMap.get(clazz.getName());
		if (o != null)
			return o;
		try {
			o = clazz.getConstructor(SqlMapClient.class).newInstance(SqlMapConfig.getSqlMapInstance());
			daoMap.put(clazz.getName(), o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Consum> quaryConsum(String beginTime,String endTime){
		ConsumDAO dao = (ConsumDAO) getDao(ConsumDAOImpl.class);
		ConsumExample example = new ConsumExample();
		List<Consum> list;
		example.createCriteria().andConsumTimeBetween(DateUtil.getDate(beginTime), DateUtil.getDate(endTime));
		try {
			list = dao.selectByExample(example);
		} catch (Exception e) {
			list = new ArrayList<Consum>();
			e.printStackTrace();
		}
		return list;
	}
}
