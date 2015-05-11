package com.orange.goldgame.server.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.orange.goldgame.domain.Player;
import com.orange.goldgame.server.domain.Gamer;

/**
 * 玩家对应gamer
 * @author guojiang
 *
 */
public class GamerSet {
	private static final GamerSet instance = new GamerSet();
	private Map<Integer, Gamer> gamerset = new ConcurrentHashMap<Integer, Gamer>();
	
	public static GamerSet getInstance() {
		return instance;
	}
	private GamerSet(){}
	
	public Gamer getGamerByPlayerId(int player_id){
		return gamerset.get(player_id);
	}
	public void putGamer(Gamer gamer){
		gamerset.put(gamer.getPlayerId(), gamer);
	}
	
	public void removeGamerByGamer(Gamer gamer){
		if (gamer != null) {
			removeByPlayerId(gamer.getPlayerId());
		}
	}
	public void removeGamerByPlayer(Player player){
		if (player != null) {
			removeByPlayerId(player.getId());
		}
	}
	public void removeByPlayerId(int player_id){
		gamerset.remove(player_id);
	}
	
	public void scan(List<Integer> player_ids){
		for (Integer id : player_ids) {
			removeByPlayerId(id);
		}
	}
}
