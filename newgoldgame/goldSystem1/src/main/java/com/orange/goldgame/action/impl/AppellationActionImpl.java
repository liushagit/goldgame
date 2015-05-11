package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.AppellationAction;
import com.orange.goldgame.domain.AppellationConfig;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.AppellationConfigDAO;
import com.orange.goldgame.system.dao.AppellationConfigDAOImpl;

public class AppellationActionImpl implements AppellationAction{
    
    private AppellationConfigDAO appellationConfigdao = (AppellationConfigDAO)DBManager.getDataDao(AppellationConfigDAOImpl.class);
    private Logger log = LoggerFactory.getLogger(AppellationActionImpl.class);
    
    @Override
    public List<AppellationConfig> getAllAppellation() {
        List<AppellationConfig> configList = null;
        try {
            configList = appellationConfigdao.selectByExample(null);
            return configList;
        } catch (SQLException e) {
            log.error("error", e);
        }
        return null;
    }

}
