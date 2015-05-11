package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.action.PlayerPayAprilAction;
import com.orange.goldgame.domain.PlayerPayApril;
import com.orange.goldgame.domain.PlayerPayAprilExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PlayerPayAprilDAO;
import com.orange.goldgame.system.dao.PlayerPayAprilDAOImpl;

public class PlayerPayAprilActionImpl implements PlayerPayAprilAction {

	PlayerPayAprilDAO dao = (PlayerPayAprilDAO) DBManager.getDao(PlayerPayAprilDAOImpl.class);
	
	@Override
	public PlayerPayApril quaryPlayerPayApril(int playerId) {
		PlayerPayApril april = null;
		try {
			PlayerPayAprilExample example = new PlayerPayAprilExample();
			example.createCriteria().andPlayerIdEqualTo(playerId);
			List<PlayerPayApril> list = dao.selectByExample(example);
			if (list != null && list.size() > 0) {
				april = list.get(0);
			}
		} catch (SQLException e) {
			april = null;
			DBManager.log.error("quaryPlayerPayApril exception:", e);
		}
		return april;
	}

	@Override
	public void updatePlayerPayApril(PlayerPayApril april) {
		try {
			dao.updateByPrimaryKey(april);
		} catch (SQLException e) {
			april = null;
			DBManager.log.error("updatePlayerPayApril exception:", e);
		}
	}

	@Override
	public int insertPlayerPayApril(PlayerPayApril april) {
		int id = -1;
		try {
			id = dao.insert(april);
		} catch (SQLException e) {
			april = null;
			DBManager.log.error("insertPlayerPayApril exception:", e);
			id = -1;
		}
		return id;
	}

}
