package com.orange.goldgame.server;

import com.juice.orange.game.cached.MemcachedResource;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.SynMoney;

public class ShareGoldCache {
	
	private static final int CACHE_SAVE_TIME=24*60*60;
	
	public static void setCacheGold(int playerId,SynMoney money){
		MemcachedResource.save(Constants.PLAYER_FOR_COPPER+playerId, money,CACHE_SAVE_TIME);
	}
	
	public static SynMoney getCacheGold(int playerId){
		Object object = MemcachedResource.get(Constants.PLAYER_FOR_COPPER+playerId);
		if (object != null) {
			return (SynMoney)object;
		}
		return null;
	}
	
	public static void deleteCacheGold(int playerId){
		MemcachedResource.remove(Constants.PLAYER_FOR_COPPER+playerId);
	}

}
