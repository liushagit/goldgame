/**
 * 
 */
package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.orange.goldgame.action.PlayerActivityCpaAction;
import com.orange.goldgame.domain.PlayerActivityCpa;
import com.orange.goldgame.domain.PlayerActivityCpaExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PlayerActivityCpaDAO;
import com.orange.goldgame.system.dao.PlayerActivityCpaDAOImpl;

/**
 * @author guojiang
 *
 */
public class PlayerActivityCpaActionImpl implements PlayerActivityCpaAction {

	PlayerActivityCpaDAO dao = (PlayerActivityCpaDAO) DBManager.getDao(PlayerActivityCpaDAOImpl.class);
	/* (non-Javadoc)
	 * @see com.orange.goldgame.action.PlayerActivityCpaAction#insert(com.orange.goldgame.domain.PlayerActivityCpa)
	 */
	@Override
	public int insert(PlayerActivityCpa pac) {
		int id = -1;
		try {
			id = dao.insert(pac);
		} catch (SQLException e) {
			id = -1;
			DBManager.log.error("insert PlayerActivityCpa exception", e);
		}
		return id;
	}

	/* (non-Javadoc)
	 * @see com.orange.goldgame.action.PlayerActivityCpaAction#update(com.orange.goldgame.domain.PlayerActivityCpa)
	 */
	@Override
	public void update(PlayerActivityCpa pac) {
		try {
			dao.updateByPrimaryKey(pac);
		} catch (SQLException e) {
			DBManager.log.error("update PlayerActivityCpa exception", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.orange.goldgame.action.PlayerActivityCpaAction#quaryByPlayerId(int)
	 */
	@Override
	public List<PlayerActivityCpa> quaryByPlayerId(int playerId) {
		PlayerActivityCpaExample example = new PlayerActivityCpaExample();
		example.createCriteria().andPlayerIdEqualTo(playerId);
		List<PlayerActivityCpa> list;
		try {
			list = dao.selectByExample(example);
		} catch (SQLException e) {
			DBManager.log.error("quaryByPlayerId PlayerActivityCpa exception", e);
			list = new ArrayList<PlayerActivityCpa>();
		}
		return list;
	}

}
