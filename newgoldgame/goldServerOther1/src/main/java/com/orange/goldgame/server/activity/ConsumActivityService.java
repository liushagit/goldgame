package com.orange.goldgame.server.activity;

import java.util.Map;

import com.orange.goldgame.business.entity.ErrorCode;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.util.DateUtil;

/**
 * 
 * @author guojiang
 * 消费送活动服务类
 *
 */
public class ConsumActivityService {

	public static final String beginTime = "20140430";
	public static final String endTime = "20140514";
	
	private static ErrorCode isInActivityTime(PackageProps pp){
		
		if(!DateUtil.isInDateRange(beginTime, endTime)){
			return new ErrorCode(-1,"活动已结束");
		}
		if(pp.getActivityType() <= 0){
			return new ErrorCode(-1,"本次购买不参与活动");
		}
		return new ErrorCode(ErrorCode.SUCC);
	}
	
	/**
	 * 购买道具送相应道具
	 * @param player
	 * @param proId
	 * @return
	 */
	public static ErrorCode buyProActivity(Player player,PackageProps pp){
		ErrorCode res = isInActivityTime(pp);
		if( res.getStatus() != ErrorCode.SUCC){
			return res;
		}
		Map<Integer,Integer> rewards = pp.getActivityRewards();
		StringBuffer sb = new StringBuffer();
		PropsConfig pc = null;
		if(rewards != null && rewards.size() > 0){
			
			for(Integer id : rewards.keySet()){
				pc = ResourceCache.getInstance().getPropsConfigs().get(id);
				if(pc != null){
					PropCacheAction.addProp(player, pc, rewards.get(id));
					sb.append(rewards.get(id)).append("张").append(pc.getName());
				}
			}
		}
		if(sb.length() > 0){
			return new ErrorCode(ErrorCode.SUCC,sb.toString());
		}
		return new ErrorCode(-1,"本次购买什么都没获得");
	}
	
	
	public static ErrorCode isConsum(PackageProps pp){
		ErrorCode res = isInActivityTime(pp);
		if( res.getStatus() != ErrorCode.SUCC){
			return res;
		}
		Map<Integer,Integer> rewards = pp.getActivityRewards();
		
		PropsConfig pc = null;
		if(rewards != null && rewards.size() > 0){
			
			for(Integer id : rewards.keySet()){
				pc = ResourceCache.getInstance().getPropsConfigs().get(id);
				if(pc != null){
					return new ErrorCode(ErrorCode.SUCC,"" + id);
				}
			}
		}
		return new ErrorCode(-1,"没有活动");
		
	}
}
