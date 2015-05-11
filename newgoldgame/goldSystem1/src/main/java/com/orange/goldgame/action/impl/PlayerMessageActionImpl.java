package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.orange.goldgame.action.PlayerMessageAction;
import com.orange.goldgame.domain.PlayerMessage;
import com.orange.goldgame.domain.PlayerMessageExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PlayerMessageDAO;
import com.orange.goldgame.system.dao.PlayerMessageDAOImpl;

public class PlayerMessageActionImpl implements PlayerMessageAction {

	PlayerMessageDAO dao = (PlayerMessageDAO) DBManager
			.getDao(PlayerMessageDAOImpl.class);

	@Override
	public int insert(PlayerMessage playerMessage) {
		int id = 0;
		try {
			id = dao.insert(playerMessage);
		} catch (Exception e) {
			DBManager.log.error("insert exception :", e);
			id = -1;
		}
		return id;
	}

	@Override
	public List<PlayerMessage> quaryAllMessage(int playerId, int loverId) {
		PlayerMessageExample example = new PlayerMessageExample();
		example.createCriteria().andPlayerIdEqualTo(playerId)
				.andOtherIdEqualTo(loverId);

		List<PlayerMessage> messages = null;
		try {
			messages = dao.selectByExample(example);
		} catch (SQLException e) {
			messages = new ArrayList<PlayerMessage>();
			DBManager.log.error("quaryAllMessage exception :", e);
		}
		return messages;
	}

	@Override
	public void update(PlayerMessage playerMessage) {
		try {
			dao.updateByPrimaryKey(playerMessage);
		} catch (SQLException e) {
			DBManager.log.error("update exception :", e);
		}
	}


	@Override
	public void updateDeleteAllPlayerMessage(List<PlayerMessage> updateMessage,
			List<PlayerMessage> deleteMessage) {
		try {
			for (PlayerMessage pm : updateMessage) {
				if (System.currentTimeMillis() - pm.getCreateTime().getTime() > 2 * 24 * 60 * 60 * 1000) {
					continue;
				}
				dao.updateByPrimaryKey(pm);
			}
		} catch (SQLException e) {
			DBManager.log.error("update exception :", e);
		}
		
		try {
			for (PlayerMessage pm : updateMessage) {
				if (System.currentTimeMillis() - pm.getCreateTime().getTime() > 2 * 24 * 60 * 60 * 1000) {
					dao.deleteByPrimaryKey(pm.getId());
				}
			}
		} catch (SQLException e) {
			DBManager.log.error("delete exception :", e);
		}
		
	}

}
