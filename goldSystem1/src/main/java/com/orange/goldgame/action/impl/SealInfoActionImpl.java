package com.orange.goldgame.action.impl;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.SealInfoAction;
import com.orange.goldgame.domain.SealInfo;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.SealInfoDAO;
import com.orange.goldgame.system.dao.SealInfoDAOImpl;

public class SealInfoActionImpl implements SealInfoAction{

    private SealInfoDAO sealInfoDAO = (SealInfoDAO)DBManager.getDao(SealInfoDAOImpl.class);
    private Logger log = LoggerFactory.getLogger(SealInfoActionImpl.class);
    
    @Override
    public void insert(SealInfo info) {
        try {
            sealInfoDAO.insert(info);
        } catch (SQLException e) {
            log.error("添加封号信息出错", e);
        }
    }

}
