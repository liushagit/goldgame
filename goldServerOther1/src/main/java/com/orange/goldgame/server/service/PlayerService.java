package com.orange.goldgame.server.service;

import org.apache.log4j.Logger;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.SynMoney;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.server.ShareGoldCache;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.RobotManager;
import com.orange.goldgame.server.manager.SessionManger;

public class PlayerService {

	private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);
	/**
	 * 获取玩家，不管玩家是否在线
	 * @param player_id
	 * @param session
	 * @return
	 * @throws GoldException
	 */
	public static Player getPlayer(int player_id,GameSession session)throws GoldException{
		Player  player = getOnLinePlayer(session);
		if (player == null) {
			player = quaryPlayerByid(player_id);
			SynMoney money=ShareGoldCache.getCacheGold(player.getId());
			if(money == null){
				money=new SynMoney();
				money.setCopper(player.getCopper());
				money.setGold(player.getGolds());
				ShareGoldCache.setCacheGold(player.getId(), money);
			}else{
				money.setCopper(player.getCopper());
				money.setGold(player.getGolds());
				ShareGoldCache.setCacheGold(player.getId(), money);
			}
		}else{
			SynMoney money = ShareGoldCache.getCacheGold(player.getId());
			if(money != null){
				player.setGolds(money.getGold());
				player.setCopper(money.getCopper());
			}else{
				money=new SynMoney();
				money.setCopper(player.getCopper());
				money.setGold(player.getGolds());
				ShareGoldCache.setCacheGold(player.getId(), money);
			}
		}
		if (player == null) {
		    logger.error("玩家ID："+player_id);
			throw new GoldException("玩家不存在");
		}
		if (player != null) {
			if (session != null) {
				session.put(Constants.PLAYER_KEY, player);
				
			}
		}
        //如果被封号，则提示玩家
        if(player.getStatus()==Constants.PLAYER_STATUS_SEAL){
            return null;
        }
		
		SessionManger.getInstance().putSession(player_id, session);
		return player;
	}
	
	/**
	 * 获取在线玩家
	 * @param player_id
	 * @param session
	 * @return
	 */
	public static Player getOnLinePlayer(GameSession session){
		Player  player = null;
		try {
			player = (Player) session.getObject(Constants.PLAYER_KEY);
		} catch (Exception e) {
			logger.error("session exption:", e);
		}
		return player;
	}
	
	public static Player getSimplePlayerByPlayerId(int player_id){
	    if(player_id<=0){
	        return RobotManager.getInstance().getRobotPlayer(player_id);
	    }
		GameSession session = SessionManger.getInstance().getSession(player_id);
		if(session != null){
		    return getPlayer(player_id, session);
		}else{
		    return quaryPlayerByid(player_id);
		}
	}
	
	public static Player quaryPlayerByid(int player_id){
	    return BaseEngine.getInstace().getPlayerActionIpml().loadPlayerById(player_id);
	}
	
	public static Player getCachePlayerByGamer(Gamer gamer){
		Player player  = null;
		if (gamer.isRobot() ) {
			player = RobotManager.getInstance().getRobotPlayer(gamer.getPlayerId());
		}else {
			player = getSimplePlayerByPlayerId(gamer.getPlayerId());
		}
		return player;
	}
}
