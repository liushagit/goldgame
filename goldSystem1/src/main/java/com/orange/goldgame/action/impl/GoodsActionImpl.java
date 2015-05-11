package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.GoodsAction;
import com.orange.goldgame.domain.GoodsConfig;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.GoodsConfigDAO;
import com.orange.goldgame.system.dao.GoodsConfigDAOImpl;

public class GoodsActionImpl implements GoodsAction {

    private Logger log = LoggerFactory.getLogger(GoodsActionImpl.class);
    
    private GoodsConfigDAO goodsConfigdao = (GoodsConfigDAO)DBManager.getDataDao(GoodsConfigDAOImpl.class);
    
	@Override
	public List<GoodsConfig> findAllGoodsConfig() {
		return DBManager.loadGoodsConfigs();
	}

    @Override
    public GoodsConfig getGoodsById(int goodsId) {
        GoodsConfig config = null;
        try {
            config = goodsConfigdao.selectByPrimaryKey(goodsId);
        } catch (SQLException e) {
            log.error("获取兑换实物失败", e);
        }
        return config;
    }



}
