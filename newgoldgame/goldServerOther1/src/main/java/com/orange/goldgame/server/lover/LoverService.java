package com.orange.goldgame.server.lover;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.server.engine.BaseEngine;

public class LoverService {

	/**
	 * 检查是否能够添加情人
	 * @param player
	 * @param lover
	 * @return
	 */
	
	
	public static Map<Integer, PlayerFriend> loadPlayerLover(Player player){
		List<PlayerFriend> lovers = BaseEngine.getInstace().getFriendActionIpml().getFriendsList(player.getId());
		Map<Integer, PlayerFriend> playerlovers = new HashMap<Integer, PlayerFriend>();
		if (lovers != null) {
			for (PlayerFriend pl : lovers) {
				playerlovers.put(pl.getFriendId(), pl);
			}
		}
		player.setFriendMap(playerlovers);
		return playerlovers;
	}
	public static String getKey(PlayerFriend pf){
		return "lover_gift_" + pf.getPlayerId() + "_" + pf.getFriendId();
	}
	public static String getIntimacyKey(PlayerFriend pf){
		if (pf.getPlayerId() > pf.getFriendId()) {
			return "lover_intimacy_" + pf.getFriendId() + "_" + pf.getPlayerId();
		}else {
			return "lover_intimacy_" + pf.getPlayerId() + "_" + pf.getFriendId();
		}
	}
	
}
