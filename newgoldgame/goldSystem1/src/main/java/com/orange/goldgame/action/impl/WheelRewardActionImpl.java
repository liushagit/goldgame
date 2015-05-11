package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.orange.goldgame.action.WheelRewardAction;
import com.orange.goldgame.domain.WheelReward;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.WheelRewardDAO;
import com.orange.goldgame.system.dao.WheelRewardDAOImpl;

public class WheelRewardActionImpl implements WheelRewardAction {

	WheelRewardDAO dao = (WheelRewardDAO) DBManager.getDataDao(WheelRewardDAOImpl.class);
	@Override
	public List<WheelReward> quaryAll() {
		List<WheelReward> list = null;
		try {
			list = dao.selectByExample(null);
		} catch (SQLException e) {
			list = new ArrayList<WheelReward>();
			DBManager.log.error("WheelFortuneActionImpl", e);
		}
		return list;
	}

}
