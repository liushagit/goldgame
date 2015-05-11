package com.orange.goldgame.action.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.AreaRobotPreAction;
import com.orange.goldgame.domain.ArearobotPre;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.ArearobotPreDAO;
import com.orange.goldgame.system.dao.ArearobotPreDAOImpl;

public class AreaRobotPreActionImpl implements AreaRobotPreAction{
	 private Logger log = LoggerFactory.getLogger(AreaRobotPreActionImpl.class);
	    
	private ArearobotPreDAO dao = (ArearobotPreDAO)DBManager.getDataDao(ArearobotPreDAOImpl.class);
	@Override
	public List<ArearobotPre> getAll() {
		List<ArearobotPre> list = null;
		try {
			list = dao.selectByExample(null);
		} catch (Exception e) {
			list = new ArrayList<ArearobotPre>();
			log.error("AreaRobotPre",e);
		}
		return list;
	}

}
