package com.orange.goldgame.server.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.juice.orange.game.container.Container;
import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.handler.OtherSessionFrameDecoder;
import com.orange.goldgame.core.GameSessionVo;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.service.PlayerService;
/**
 * 
 * @author wuruihuang 2013.5.22
 * 管理gameSession
 */
public class SessionManger {
	private static final SessionManger sessionManger = new SessionManger();
	private static final int SCANTIME = 3 * 30 * 1000;
	public static final int MAX_NUM = 2000;
	/**
	 * Integer类型的参数指的是玩家的id
	 */
	private  Map<Integer, GameSessionVo> gameSessions = new ConcurrentHashMap<Integer, GameSessionVo>();
	public Map<Integer, GameSessionVo> getGameSessions() {
		return gameSessions;
	}
	private SessionManger(){}
	public static SessionManger getInstance(){
		return sessionManger;
	}
	
	private final int TOOL_ID = -211;
	/**
	 * 保存session
	 * @param playerId
	 * @param session
	 */
	public void putSession(int playerId,GameSession session){
		if(TOOL_ID == playerId){
			GameSessionVo vo = new GameSessionVo();
			vo.setSession(session);
			vo.setLastUpdateTime(System.currentTimeMillis());
			gameSessions.put(playerId, vo);
			return;
		}
		GameSessionVo vo = gameSessions.get(playerId);
		if (vo != null) {
			vo.setLastUpdateTime(System.currentTimeMillis());
			vo.setSession(session);
		}else {
			vo = new GameSessionVo();
			vo.setSession(session);
			vo.setLastUpdateTime(System.currentTimeMillis());
			gameSessions.put(playerId, vo);
		}
	}
	
	/**
	 * 获取session
	 * @param playerId
	 * @return
	 */
	public GameSession getSession(int playerId){
		GameSessionVo vo =  gameSessions.get(playerId);
		if (vo != null ) {
			return vo.getSession();
		}
		return null;
	}
	public GameSession getSession(Player player){
		GameSessionVo vo =  gameSessions.get(player.getId());
		if (vo != null ) {
			return vo.getSession();
		}
		return null;
	}
	
	/**获取gameSession的个数**/
	public int size(){
		return gameSessions.size();
	}
	
	public void remove(int player_id) {
		GameSessionVo sessionVo = gameSessions.get(player_id);
		if (sessionVo != null) {
			GameSession gs = sessionVo.getSession();
			try {
				String session_id = gs.getObject(OtherSessionFrameDecoder.OTHERSESSIONID).toString();
				Container.removeSession(session_id);
			} catch (Exception e) {
			}
		}
		gameSessions.remove(player_id);
		Player player = PlayerService.getSimplePlayerByPlayerId(player_id);
		if(player!=null){
		    player.setIslogin(0);
		    if (player.getOnlineTime() < 0) {
				player.setOnlineTime(0);
			}
		    int online = (int)((System.currentTimeMillis()-player.getLastLoginTime().getTime())/(1000*60));
		    player.setOnlineTime(player.getOnlineTime() + online);
		    BaseEngine.getInstace().getPlayerActionIpml().modifyPlayer(player);
		}
	}
	
	public List<Integer> getScanIds(){
		List<Integer> scanList = new ArrayList<Integer>();
		long now = System.currentTimeMillis();
		for (Integer key : gameSessions.keySet()) {
			if (now - gameSessions.get(key).getLastUpdateTime() >= SCANTIME) {
				scanList.add(key);
			}
		}
		return scanList;
	}
	
	public void scan(List<Integer> player_ids){
		for (Integer player_id : player_ids) {
			remove(player_id);
//			ShareGoldCache.deleteCacheGold(player_id);
		}
	}
}
