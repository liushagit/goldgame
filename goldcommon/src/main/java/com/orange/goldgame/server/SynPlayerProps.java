package com.orange.goldgame.server;

import com.juice.orange.game.cached.MemcachedResource;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PropsConfig;

public class SynPlayerProps {

	/**
	 * 获取玩家数据
	 * @param player
	 * @param config
	 * @return
	 */
	public static PlayerProps checkPlayerPropsNum(Player player,PlayerProps pp){
		if (pp != null) {
			Object object = MemcachedResource.get(Constants.PLAYER_PROPS_KEY + player.getId() + "_" + pp.getPropsConfigId());
			if (object != null) {
				pp.setNumber(Integer.parseInt(object.toString()));
			}
		}
		return pp;
	}
	
	/**
	 * 更新玩家数据
	 * @param player
	 * @param config
	 * @return
	 */
	public static void updatePlayerProps(Player player,PlayerProps pp){
		MemcachedResource.save(Constants.PLAYER_PROPS_KEY + player.getId() + "_" + pp.getPropsConfigId(), pp.getNumber(), 2 * 24 * 60 * 60);
		MemcachedResource.save(Constants.PLAYER_PROPS_KEY + player.getId() , pp.getNumber(), 2 * 24 * 60 * 60);
	}
	
	public static boolean isQuary(Player player){
		Object object = MemcachedResource.get(Constants.PLAYER_PROPS_KEY + player.getId());
		if (object != null) {
			int num = Integer.parseInt(object.toString());
			return num > 0 ? true : false;
		}
		return false;
	}
	
	/**
	 * 删除道具
	 * @param player
	 * @param pp
	 */
	public static void deletePlayerProps(Player player,PlayerProps pp){
		MemcachedResource.remove(Constants.PLAYER_PROPS_KEY + player.getId() + "_" + pp.getPropsConfigId());
	}
	
}
