/**
 * 
 */
package com.orange.goldgame.server.acmatch;

import java.util.Map;

import org.jboss.netty.util.internal.ConcurrentHashMap;

import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.Player;

/**
 * @author guojiang
 *
 */
public class ActivityMatchCache {
	private static final ActivityMatchCache instance = new ActivityMatchCache();
	
//	private Map<Long, Map<Integer, Player>> activityTeams = new ConcurrentHashMap<Long, Map<Integer,Player>>();
	/**
	 * 类型-》teamId-》玩家id
	 */
	private Map<Integer , Map<Long, Map<Integer, Player>>> activityTeams = new ConcurrentHashMap<Integer, Map<Long,Map<Integer,Player>>>();
	
	private ActivityMatchCache(){
		Map<Integer, AreaConfig> areaConfig = ResourceCache.getInstance().getAreaConfigs();
		for (AreaConfig ac : areaConfig.values()) {
			if (ac.getAreaType().intValue() == 3) {
				activityTeams.put(ac.getId(), new ConcurrentHashMap<Long, Map<Integer,Player>>());
			}
		}
	}
	
	public static ActivityMatchCache getInstance() {
		return instance;
	}
	
	/**
	 * 加入一个玩家
	 * @param player
	 * @return
	 */
	synchronized public Map<Integer, Player> addOnePlayer(Player player,int teamType){
		Map<Integer, Player> team = getOneTeam(teamType);
		team.put(player.getId(), player);
		return team;
	}
	
	private Map<Integer, Player> createOneTeam(int teamType){
		Map<Long, Map<Integer, Player>> map = activityTeams.get(teamType);
		Map<Integer, Player> team  = new ConcurrentHashMap<Integer, Player>();
		long id = System.currentTimeMillis();
		map.put(id, team);
		return team;
	}
	
	private Map<Integer, Player> getOneTeam(int teamType){
		Map<Long, Map<Integer, Player>> map = activityTeams.get(teamType);
		AreaConfig ac = ResourceCache.getInstance().getAreaConfigs().get(teamType);
		for (Map<Integer, Player> team : map.values()) {
			if (team.size() < ac.getMaxNum()) {
				return team;
			}
		}
		return createOneTeam(teamType);
	}
}
