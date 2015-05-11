package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.action.PlayerWheelRewardAction;
import com.orange.goldgame.domain.PlayerWheelReward;
import com.orange.goldgame.domain.PlayerWheelRewardExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PlayerWheelRewardDAO;
import com.orange.goldgame.system.dao.PlayerWheelRewardDAOImpl;

public class PlayerWheelRewardActionImpl implements PlayerWheelRewardAction{

	PlayerWheelRewardDAO dao = (PlayerWheelRewardDAO) DBManager.getDao(PlayerWheelRewardDAOImpl.class);
	@Override
	public PlayerWheelReward quaryPlayerWheelReward(int playerId) {
		PlayerWheelRewardExample example = new PlayerWheelRewardExample();
		example.createCriteria().andPlayerIdEqualTo(playerId);
		List<PlayerWheelReward> list = null;
		PlayerWheelReward pwr = null;
		try {
			list = dao.selectByExample(example);
			if (list != null && list.size() > 0) {
				pwr = list.get(0);
			}
		} catch (SQLException e) {
			DBManager.log.error("quaryPlayerWheelReward exception", e);
			pwr = null;
		}
		
		return pwr;
	}

	@Override
	public void updatePlayerWheelReward(PlayerWheelReward pwr) {
		if (pwr == null) {
			return;
		}
		try {
			dao.updateByPrimaryKey(pwr);
		} catch (SQLException e) {
			DBManager.log.error("updatePlayerWheelReward exception", e);
		}
	}

	@Override
	public int insertPlayerWheelReward(PlayerWheelReward pwr) {
		int id = -1;
		try {
			id = dao.insert(pwr);
		} catch (Exception e) {
			id = -1;
			DBManager.log.error("insertPlayerWheelReward exception", e);
		}
		return id;
	}

}
