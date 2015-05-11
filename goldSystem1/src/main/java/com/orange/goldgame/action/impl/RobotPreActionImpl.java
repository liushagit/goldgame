package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.orange.goldgame.action.RobotPreAction;
import com.orange.goldgame.domain.RobotPre;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.RobotPreDAO;
import com.orange.goldgame.system.dao.RobotPreDAOImpl;

public class RobotPreActionImpl implements RobotPreAction{

	@Override
	public List<RobotPre> quaryAllRobotPre() {
		RobotPreDAO dao = (RobotPreDAO)DBManager.getDataDao(RobotPreDAOImpl.class);
		List<RobotPre> list = new ArrayList<RobotPre>();
		 try {
			 list = dao.selectByExample(null);
		} catch (SQLException e) {
		}
		 return list;
	}

}
