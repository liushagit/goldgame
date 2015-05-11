package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.PayTypeConfigAction;
import com.orange.goldgame.domain.PayChannelConfig;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PayChannelConfigDAO;
import com.orange.goldgame.system.dao.PayChannelConfigDAOImpl;

public class PayTypeConfigActionImpl implements PayTypeConfigAction{

    private Logger log = LoggerFactory.getLogger(PayTypeConfigActionImpl.class);
    private PayChannelConfigDAO payChannelConfigDAO = (PayChannelConfigDAO) DBManager.getDataDao(PayChannelConfigDAOImpl.class);
    
    @Override
    public List<PayChannelConfig> getAll() {
        List<PayChannelConfig> list = new ArrayList<PayChannelConfig>();
        try {
            list = payChannelConfigDAO.selectByExample(null);
        } catch (SQLException e) {
            log.error("payChannel error", e);
        }
        return list;
    }

}
