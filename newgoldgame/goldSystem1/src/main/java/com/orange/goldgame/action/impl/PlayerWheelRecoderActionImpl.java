package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.orange.goldgame.action.PlayerWheelRecoderAction;
import com.orange.goldgame.domain.PlayerWheelRecoder;
import com.orange.goldgame.domain.PlayerWheelRecoderExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PlayerWheelRecoderDAO;
import com.orange.goldgame.system.dao.PlayerWheelRecoderDAOImpl;
import com.orange.goldgame.util.DateUtil;

public class PlayerWheelRecoderActionImpl implements PlayerWheelRecoderAction {

	PlayerWheelRecoderDAO dao = (PlayerWheelRecoderDAO) DBManager.getDao(PlayerWheelRecoderDAOImpl.class);
	@Override
	public int insertOneRecoder(PlayerWheelRecoder recoder) {
		try {
			return dao.insert(recoder);
		} catch (SQLException e) {
			DBManager.log.error("insert player wheelrecoder exception:", e);
		}
		return -1;
	}

	@Override
	public void updateRecoder(PlayerWheelRecoder recoder) {
		try {
			dao.updateByPrimaryKey(recoder);
		} catch (SQLException e) {
			DBManager.log.error("update player wheelrecoder exception:", e);
		}
	}

	@Override
	public PlayerWheelRecoder quaryById(int id) {
		PlayerWheelRecoder recoder = null;
		try {
			recoder = dao.selectByPrimaryKey(id);
		} catch (SQLException e) {
			DBManager.log.error("quary player wheelrecoder by id exception:", e);
			recoder = null;
		}
		return recoder;
	}

	/* (non-Javadoc)
	 * @see com.orange.goldgame.action.PlayerWheelRecoderAction#quaryByTime(java.lang.String, java.lang.String)
	 */
	@Override
	public List<PlayerWheelRecoder> quaryByTime(String beginTime, String endTime,int status) {
		
		List<PlayerWheelRecoder> list = null;
		PlayerWheelRecoderExample example = new PlayerWheelRecoderExample();
		if (status >= 0) {
			example.createCriteria().
			andCreateTimeBetween(DateUtil.getDate(beginTime), DateUtil.getDate(endTime)).
			andChangeStatusEqualTo(status);
		}else {
			example.createCriteria().
			andCreateTimeBetween(DateUtil.getDate(beginTime), DateUtil.getDate(endTime));
		}
		
		try {
			list = dao.selectByExample(example);
		} catch (SQLException e) {
			DBManager.log.error("quaryByTime player wheelrecoder by id exception:", e);
			list = new ArrayList<PlayerWheelRecoder>();
		}
		return list;
	}

}
