package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.ActivityConfigAction;
import com.orange.goldgame.domain.ActivityConfig;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.ActivityConfigDAO;
import com.orange.goldgame.system.dao.ActivityConfigDAOImpl;


public class ActivityConfigActionImpl implements ActivityConfigAction{
    
    private Logger log = LoggerFactory.getLogger(TaskConfigActionImpl.class);
    
    private ActivityConfigDAO activityConfigDao = (ActivityConfigDAO)DBManager.getDataDao(ActivityConfigDAOImpl.class);
    
    @Override
    public List<ActivityConfig> getAll() {
        try {
            return activityConfigDao.selectByExample(null);
        } catch (SQLException e) {
            log.error("activity error", e);
        }
        return null;
    }

}
