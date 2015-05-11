package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.PlayerInfoCenterAction;
import com.orange.goldgame.domain.PlayerInfoCenter;
import com.orange.goldgame.domain.PlayerInfoCenterExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PlayerInfoCenterDAO;
import com.orange.goldgame.system.dao.PlayerInfoCenterDAOImpl;
import com.orange.goldgame.system.dbutil.DBService;
import com.orange.goldgame.system.dbutil.GlobalGenerator;

public class PlayerInfoCenterActionImpl implements PlayerInfoCenterAction{

    private Logger log = LoggerFactory.getLogger(PlayerInfoCenterActionImpl.class);
    private PlayerInfoCenterDAO playerInfoCenterDAO = (PlayerInfoCenterDAO)DBManager.getDao(PlayerInfoCenterDAOImpl.class);
    
    @Override
    public List<PlayerInfoCenter> getPlayerInfoCeterListByPlayerId(int playerId) {
        PlayerInfoCenterExample example = new PlayerInfoCenterExample();
        example.createCriteria().andPlayerIdEqualTo(playerId);
        example.setOrderByClause("create_time desc limit 0,20");
        List<PlayerInfoCenter> list = null;
        List<PlayerInfoCenter> piclist = null;
        try {
            list = playerInfoCenterDAO.selectByExample(example);
            piclist = new ArrayList<PlayerInfoCenter>();
            for(PlayerInfoCenter pic : list){
                if(System.currentTimeMillis()-pic.getCreateTime().getTime()>=60*1000*60*24*3){
                    playerInfoCenterDAO.deleteByPrimaryKey(pic.getId());
                }else {
                	piclist.add(pic);
				}
            }
        } catch (SQLException e) {
            log.error("playerInfocenter error", e);
        }
        if (piclist == null) {
        	piclist = new ArrayList<PlayerInfoCenter>();
		}
        return piclist;
    }

    @Override
    public void addPlayerInfoCenter(PlayerInfoCenter playerInfoCenter) {
        try {
        	int id = GlobalGenerator.getInstance().getReusedIdForNewObj(PlayerInfoCenter.TABLENAME);
        	playerInfoCenter.setId(id);
        	DBService.commit(playerInfoCenter);
//            playerInfoCenterDAO.insert(playerInfoCenter);
        } catch (Exception e) {
            log.error("playerInfocenter error", e);
        }
    }

}
