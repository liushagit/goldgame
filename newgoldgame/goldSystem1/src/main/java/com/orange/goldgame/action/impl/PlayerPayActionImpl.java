package com.orange.goldgame.action.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.orange.goldgame.action.PlayerPayAction;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.domain.PlayerPayExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PlayerPayDAO;
import com.orange.goldgame.system.dao.PlayerPayDAOImpl;

public class PlayerPayActionImpl implements PlayerPayAction{

	@Override
	public PlayerPay quaryPlayerPay(int player_id, String order_id) {
		return DBManager.quaryPlayerPay(player_id, order_id);
	}

	@Override
	public int insertPlayerPay(PlayerPay pay) {
		return DBManager.insertPlayerPay(pay);
	}

	@Override
	public List<PlayerPay> queryAllPayForPlayer(int playerId) {		
		return DBManager.queryAllPayForPlayer(playerId);
	}
	
	@Override
	public int getSumToday(int playerId){
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		Date today = null;
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		today = calendar.getTime();
		PlayerPayDAO dao= (PlayerPayDAO)DBManager.getDao(PlayerPayDAOImpl.class);
		PlayerPayExample example = new PlayerPayExample();
		example.createCriteria().andPlayerIdEqualTo(playerId).andPayTimeBetween(today, now);
		List<PlayerPay> list ;
		try {
			list = dao.selectByExample(example);
		} catch (Exception e) {
			DBManager.log.error("getSumToday", e);
			list = new ArrayList<PlayerPay>();
		}
		int sum = 0;
		for (PlayerPay pp : list) {
			sum += pp.getGold();
		}
		return sum;
	}
	@Override
	public List<PlayerPay> queryPayByRange(int start, int end) {	
		return DBManager.queryPayByRange(start, end);
	}

}
