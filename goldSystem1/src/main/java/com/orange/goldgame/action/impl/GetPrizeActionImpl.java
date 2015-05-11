package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.GetPrizeAction;
import com.orange.goldgame.domain.GetPrize;
import com.orange.goldgame.domain.GetPrizeExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.GetPrizeDAO;
import com.orange.goldgame.system.dao.GetPrizeDAOImpl;

public class GetPrizeActionImpl implements GetPrizeAction{

    private Logger log = LoggerFactory.getLogger(GetPrizeActionImpl.class);
    
    private GetPrizeDAO getPrizeDao = (GetPrizeDAO)DBManager.getDao(GetPrizeDAOImpl.class);
    
    @Override
    public GetPrize getPrizeByPlayerId(int playerId) {
        
        GetPrizeExample example = new GetPrizeExample();
        example.createCriteria().andPlayerIdEqualTo(playerId);
        List<GetPrize> list = null;
        try {
            list = getPrizeDao.selectByExample(example);
            if(list==null || list.size()<=0){
                return null;
            }
        } catch (SQLException e) {
            log.error("每日奖励获取失败！", e);
        }
        return list.get(0);
    }

    @Override
    public int insert(GetPrize getPrize) {
        if(getPrize == null) return 0;
        try {
            return getPrizeDao.insert(getPrize);
        } catch (SQLException e) {
            log.error("添加每日奖励失败！", e);
        }
        return 0;
    }

    @Override
    public GetPrize getPrizeById(int id) {
        try {
            return getPrizeDao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            log.error("获取每日奖励失败！", e);
        }
        return null;
    }

    @Override
    public void update(GetPrize getPrize) {
        try {
            getPrizeDao.updateByPrimaryKeySelective(getPrize);
        } catch (SQLException e) {
            log.error("更新每日奖励失败！", e);
        }
    }

}
