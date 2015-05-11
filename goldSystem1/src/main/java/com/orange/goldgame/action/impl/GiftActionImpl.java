package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.GiftAction;
import com.orange.goldgame.domain.Gift;
import com.orange.goldgame.domain.GiftExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.GiftDAO;
import com.orange.goldgame.system.dao.GiftDAOImpl;
import com.orange.goldgame.system.dao.PropsConfigDAO;
import com.orange.goldgame.system.dao.PropsConfigDAOImpl;

public class GiftActionImpl implements GiftAction {

    private Logger log = LoggerFactory.getLogger(GiftActionImpl.class);
    private GiftDAO giftDao = (GiftDAO) DBManager.getDao(GiftDAOImpl.class);
    private PropsConfigDAO propsConfigDao = (PropsConfigDAO)DBManager.getDataDao(PropsConfigDAOImpl.class);
    
	@Override
	public List<Gift> findGiftByPlayerId(int playerId) {
	    GiftExample example = new GiftExample();
	    example.createCriteria().andPlayerIdEqualTo(playerId);
	    
	    try {
            List<Gift> propsList = giftDao.selectByExample(example);
            return propsList;
        } catch (SQLException e) {
            log.error("更新礼物失败", e);
        }
	    
	    return null;
	}

    @Override
    public void update(Gift gift) {
        try {
            giftDao.updateByPrimaryKey(gift);
        } catch (SQLException e) {
            log.error("更新礼物失败", e);
        }
    }
    

    @Override
    public Gift findGiftByPlayerIdAndPropId(int playerId, int propId) {
        GiftExample example = new GiftExample();
        example.createCriteria().andPlayerIdEqualTo(playerId).andPropIdEqualTo(propId);
        try {
            List<Gift> giftList = giftDao.selectByExample(example);
            if(giftList == null || giftList.size()<=0){
                return initGift(playerId,propId);
            }
            return giftList.get(0);
        } catch (SQLException e) {
            log.error("处理礼物数据失败", e);
        }
        return null;
    }

    public Gift initGift(int playerId, int propId) {
        Gift gift = new Gift();
        gift.setAmont(0);
        gift.setPlayerId(playerId);
        gift.setPropId(propId);
        try {
            int id = giftDao.insert(gift);
            gift.setId(id);
            return gift;
        } catch (SQLException e) {
            log.error("处理礼物数据失败", e);
        }
        return null;
    }
}
