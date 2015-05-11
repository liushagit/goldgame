/**
 * 
 */
package com.orange.goldgame.server.acmatch;

import java.util.Map;

import com.juice.orange.game.cached.MemcachedResource;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.server.MemKeyServer;

/**
 * @author guojiang
 * 活动比赛模式管理
 *
 */
public class ActivityMatchService {

	/**
	 * 玩家加入比赛
	 * @param player
	 * @param teamType
	 */
	public static ErrorCode joinMathc(Player player,int teamType){
		
		//1、确认是否充值（memcached）
		String key = MemKeyServer.getActivityMatchKey(player.getId());
		Object object = MemcachedResource.get(MemKeyServer.getActivityMatchKey(player.getId()));
		if (object == null) {
			return new ErrorCode(-1,"进入比赛场错误！");
					
		}else {
			//2、加入比赛
			MemcachedResource.remove(key);
			Map<Integer, Player> team = ActivityMatchCache.getInstance().addOnePlayer(player, teamType);
			//3、是否可以正常开始游戏
			if (canBegin(teamType,team)) {
				//4、开始比赛
				begin(team);
				return new ErrorCode(1, team);
			}
			return new ErrorCode(ErrorCode.SUCC, team);
		}
	}
	
	/**
	 * 玩家推出比赛
	 * @param player
	 */
	public static ErrorCode signOutMatch(Player player){
		//1、游戏已经开始不能退出
		//2、确认是否已经给玩家加钱
		//3、正常退出游戏
		return null;
	}


	/**
	 * 能否开始比赛
	 * @param teamType
	 * @param team
	 * @return
	 */
	private static boolean canBegin(int teamType,Map<Integer, Player> team){
		//1、检查人数是否够
		AreaConfig ac = ResourceCache.getInstance().getAreaConfigs().get(teamType);
		if (team.size() >= ac.getMaxNum()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 开始比赛
	 * @param teamId
	 * @return
	 */
	public static ErrorCode begin(Map<Integer, Player> team){
		
		return null;
	}
	
	
}
