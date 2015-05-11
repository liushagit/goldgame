package com.orange.goldgame.action.impl;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.ExchangeRecordAction;
import com.orange.goldgame.domain.ExchangeRecord;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.ExchangeRecordDAO;
import com.orange.goldgame.system.dao.ExchangeRecordDAOImpl;

public class ExchangeRecordActionImpl implements ExchangeRecordAction{

    private Logger log = LoggerFactory.getLogger(ExchangeRecordActionImpl.class);
    
    private ExchangeRecordDAO exchangeRecordDAO = (ExchangeRecordDAO)DBManager.getDao(ExchangeRecordDAOImpl.class);
    
    @Override
    public void insert(ExchangeRecord record) {
        try {
            exchangeRecordDAO.insert(record);
        } catch (SQLException e) {
            log.error("添加兑换记录失败！", e);
        }
    }

}
