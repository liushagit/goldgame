package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.SealInfoAction;
import com.orange.goldgame.domain.SealInfo;
import com.orange.goldgame.domain.SealInfoExample;
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

	@SuppressWarnings("unchecked")
	@Override
	public SealInfo getSealInfo(int playerId) {
		List<SealInfo> list = new ArrayList<SealInfo>();
		SealInfo info = null;
		SealInfoExample example = new SealInfoExample();
		example.createCriteria().andPlayerIdEqualTo(playerId);
		try {
			list = sealInfoDAO.selectByExample(example);
			if(list.size() > 0){
				info = list.get(0);
			}
		} catch (Exception e) {
			log.error("查找封号信息出错", e);
		}
		return info;
	}

	@Override
	public void updateSealInfo(SealInfo info) {
		try {
			sealInfoDAO.updateByPrimaryKey(info);
		} catch (SQLException e) {
			log.error("更新封号信息出错", e);
		}
	}

}
