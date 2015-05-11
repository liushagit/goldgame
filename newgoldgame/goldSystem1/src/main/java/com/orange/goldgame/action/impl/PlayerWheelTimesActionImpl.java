/**
 * 
 */
package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.action.PlayerWheelTimesAction;
import com.orange.goldgame.domain.PlayerWheelTimes;
import com.orange.goldgame.domain.PlayerWheelTimesExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PlayerWheelTimesDAO;
import com.orange.goldgame.system.dao.PlayerWheelTimesDAOImpl;

/**
 * @author guojiang
 *
 */
public class PlayerWheelTimesActionImpl implements PlayerWheelTimesAction {

	PlayerWheelTimesDAO dao = (PlayerWheelTimesDAO) DBManager.getDao(PlayerWheelTimesDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.orange.goldgame.action.PlayerWheelTimesAction#quaryPlayerWheelTimes(int)
	 */
	@Override
	public PlayerWheelTimes quaryPlayerWheelTimes(int playerId) {
		PlayerWheelTimesExample example = new PlayerWheelTimesExample();
		example.createCriteria().andPlayerIdEqualTo(playerId);
		List<PlayerWheelTimes> pwtsList = null;
		try {
			pwtsList = dao.selectByExample(example);
			if (pwtsList != null && pwtsList.size() > 0) {
				return pwtsList.get(0);
			}
		} catch (SQLException e) {
			DBManager.log.error("quaryPlayerWheelTimes exception", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.orange.goldgame.action.PlayerWheelTimesAction#insert(com.orange.goldgame.domain.PlayerWheelTimes)
	 */
	@Override
	public int insert(PlayerWheelTimes pwt) {
		int id = -1;
		try {
			id = dao.insert(pwt);
		} catch (SQLException e) {
			id = -1;
			DBManager.log.error("insert PlayerWheelTimes exception", e);
		}
		return id;
	}

	/* (non-Javadoc)
	 * @see com.orange.goldgame.action.PlayerWheelTimesAction#update(com.orange.goldgame.domain.PlayerWheelTimes)
	 */
	@Override
	public void update(PlayerWheelTimes pwt) {
		try {
			dao.updateByPrimaryKey(pwt);
		} catch (SQLException e) {
			DBManager.log.error("update PlayerWheelTimes exception", e);
		}
	}

}
