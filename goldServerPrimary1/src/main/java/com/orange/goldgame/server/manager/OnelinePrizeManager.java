package com.orange.goldgame.server.manager;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.GetOnce;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.server.domain.OnlieReward;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.util.DateUtil;

public class OnelinePrizeManager {
    
    private static Logger log = LoggerFactory.getLogger(OnelinePrizeManager.class);

//    public int time;
    
    private int firstTime=60;
    
    private static OnelinePrizeManager onlineManager=new OnelinePrizeManager();
    
    private Map<Integer, OnlieReward> mapReward=new HashMap<Integer, OnlieReward>();
    
    private OnelinePrizeManager(){}
    
    public static OnelinePrizeManager getInstance(){
    	return onlineManager;
    }
    
    /**
     * 获取每日在线奖励的金币数，并添加金币
     * @param player
     * @return 金币数
     */
	public OnlieReward getPlayerOnlinePrize(Player player){
	    
	    log.debug("玩家在线奖励..");
	    GetOnce getOnce = getPlayerGetOnce(player,Constants.DAILY_ONLINE_TYPE);
	    String dayNum = getOnce.getDayNum();
	    String[] dayNumConf = dayNum.split("_");
        int num = Integer.parseInt(dayNumConf[1]);
        String today = DateUtil.getDateDesc();
        //判断时间是否相同
        if(!today.equals(dayNumConf[0])){
            log.debug("更新每日在线奖励记录...");
            num = 1;
            getOnce.setDayNum(today+"_"+num);
            BaseEngine.getInstace().getGetOnceActionImpl().modify(getOnce);
        }
        
        if(mapReward.get(player.getId())==null&&today.equals(dayNumConf[0])){
        	num = 1;
            getOnce.setDayNum(today+"_"+num);
            BaseEngine.getInstace().getGetOnceActionImpl().modify(getOnce);
        }
        
        //获取在线奖励的次数
        AppConfig prizeConfig = BaseEngine.getInstace().getAppConfigActionImpl()
            .findAppConfigByKey(Constants.DAILY_ONLINE_PRIZE);
        
        
        String prizeSend = prizeConfig.getAppValue();
        
        String[] prizeS = prizeSend.split("_");
        
        OnlieReward or=new OnlieReward();
        or.setRewardTime(today);
        log.debug("在线奖励继续..");
        AppConfig timeConfig = BaseEngine.getInstace().getAppConfigActionImpl()
        		.findAppConfigByKey(Constants.DAILY_ONLINE_TIME);
        //间隔的时间
        String timeSend=timeConfig.getAppValue();
        String[] timeS=timeSend.split("_");
        
        //判断用户今天在线奖励是否奖励玩
        if(num<Integer.parseInt(prizeS[0])){
            int time=Integer.parseInt(timeS[num]);
        	int prize = Integer.parseInt(prizeS[num]);
//            	GoldService.addCopper(player, prize);
        	num = num+1;
        	getOnce.setDayNum(today+"_"+num);
        	BaseEngine.getInstace().getGetOnceActionImpl().modify(getOnce);
        	
        	or.setTime(time);
        	or.setPrize(prize);
        	
        	mapReward.put(player.getId(), or);
        	return mapReward.get(player.getId());          	                     
        }           
        if(num==Integer.parseInt(prizeS[0])){
        	int time=0;
        	int prize = Integer.parseInt(prizeS[num]);
        	num = num+1;
        	getOnce.setDayNum(today+"_"+num);
        	BaseEngine.getInstace().getGetOnceActionImpl().modify(getOnce);     	
        	or.setTime(time);
        	or.setPrize(prize);      	
        	mapReward.put(player.getId(), or);
        	return mapReward.get(player.getId()); 
        }      
        return null;
	}
	
	
    
    
	/**
	 * 根据类型获取玩家每日在线奖励记录
	 * @param player
	 * @param key
	 * @return
	 */
	private GetOnce getPlayerGetOnce(Player player,String key){
		//从数据中得到玩家获取每日赠送的记录
		GetOnce getOnce = GetOnceManager.getPlayerGetOnce(player, key);
			//缓存玩家每日赠送记录
		player.getGetoncs().put(key, getOnce);
		return getOnce;
	}

	public int getTime(Player player) {
		OnlieReward or=mapReward.get(player.getId());
		if(or!=null){
			String today = DateUtil.getDateDesc();
			if(today.equals(or.getRewardTime())){
			   return or.getTime();
			}else{
			   return firstTime;
			}
		}
		return firstTime;
	}

	
	/**
	 * 查询数据库
	 * @param getOnce
	 */
//	private static GetOnce getGetOnce(int playerId,String key){
//		return BaseEngine.getInstace().getGetOnceActionImpl().getGetOnce(playerId, key);
//	}
	/*
	*//**
	 * 根据类型获取玩家今天领取了次数
	 * @param player
	 * @param key
	 * @return
	 *//*
	private static int getTodayNum(GetOnce getOnce){
		String dateNumber = getOnce.getDayNum();
		String[] dateAndNumber = dateNumber.split("_");
		// TODO 判断当天
		int number = Integer.valueOf(dateAndNumber[1]);
		return number;
	}*/
	/*
	*//**
	 * 根据类型检查当天是否可以继续领取
	 * @param player
	 * @param key
	 * @param limit_time
	 * @return
	 *//*
	private static boolean checkToday(GetOnce getOnce,int limit_time){
		int todayNum = getTodayNum(getOnce);
		return (limit_time-todayNum > 0);
	}*/
	/**
	 * 增加一条新每日在线奖励记录
	 * @param playerId
	 * @param key
	 * @return 
	 */
//	private static GetOnce addGetOnce(Player player,String key){
//		return GetOnceManager.getPlayerGetOnce(player, key);
//	}
//	

	
}
