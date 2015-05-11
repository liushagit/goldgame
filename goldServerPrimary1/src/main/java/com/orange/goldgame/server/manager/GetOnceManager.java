package com.orange.goldgame.server.manager;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.GetOnce;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.service.BaseServer;
import com.orange.goldgame.server.service.GoldService;
import com.orange.goldgame.util.DateUtil;

public class GetOnceManager {
    
    private static Logger log = LoggerFactory.getLogger(GetOnceManager.class);
    
    /**
     * 获取每日赠送的金币数，并添加金币
     * @param player
     * @return 金币数
     */
	public static int getPlayerGetOnce(Player player){
	    
	    log.debug("准备领取每日赠送");
	    GetOnce getOnce = getPlayerGetOnce(player,Constants.DAILY_GIVE_TYPE);
	    String dayNum = getOnce.getDayNum();
	    String[] dayNumConf = dayNum.split("_");
        int num = Integer.parseInt(dayNumConf[1]);
        String today = DateUtil.getDateDesc();
        //如果不是今天
        if(!today.equals(dayNumConf[0])){
            log.debug("日期不是今天");
            num = 1;
            getOnce.setDayNum(today+"_"+num);
//            BaseEngine.getInstace().getGetOnceActionImpl().modify(getOnce);
        }
        
        //获取当次
        AppConfig config = ResourceCache.getInstance().getAppConfigs().get(Constants.DATE_SEND);
        String dateSend = config.getAppValue();
        String[] dateS = dateSend.split("_");
        
        //如果超出领取的次数，返回0
        if(num>Integer.parseInt(dateS[0])){
            log.debug("超出领取次数");
            return 0;
        }
        int prize = Integer.parseInt(dateS[num]);
        
        //修改DB
        GoldService.addCopperAndUpdateGamer(player, prize);
        num = num+1;
        getOnce.setDayNum(today+"_"+num);
        BaseEngine.getInstace().getGetOnceActionImpl().modify(getOnce);
        
        //返回结果
        GameSession session = SessionManger.getInstance().getSession(player);
        OutputMessage om = new OutputMessage();
        om.putInt(player.getCopper());
        session.sendMessage(Protocol.RESPONSE_LEFT_GOLD, om);
        String mess = "您已领取了"+(num-1)+"次，剩余次数为："+(Integer.parseInt(dateS[0])-num+1)+"次";
        BaseServer.sendErrorMsg(session, (short)1, mess);
	    return prize;
	}
    
    
	/**
	 * 
	 * 根据类型获取玩家领取记录
	 * @param player
	 * @param key
	 * @return
	 */
	public static GetOnce getPlayerGetOnce(Player player,String key){
		//从数据中得到玩家获取每日赠送的记录
		GetOnce getOnce = player.getGetoncs().get(key);
		if(getOnce==null){
		    getOnce = getGetOnce(player.getId(), key);
		}
		if(getOnce==null){
			//生成新的玩家每日赠送记录
			getOnce = GetOnceManager.addGetOnce(player.getId(), key);
			//缓存玩家每日赠送记录
			player.getGetoncs().put(key, getOnce);
		}
		return getOnce;
	}
	
	/**
	 * 查询数据库
	 * @param getOnce
	 */
	private static GetOnce getGetOnce(int playerId,String key){
		return BaseEngine.getInstace().getGetOnceActionImpl().getGetOnce(playerId, key);
	}
	/*
	*//**
	 * 根据类型获取玩家今天领取了次数
	 * @param player
	 * @param key
	 * @return
	 */
	public static int getTodayNum(GetOnce getOnce){
		String dateNumber = getOnce.getDayNum();
		String[] dateAndNumber = dateNumber.split("_");
		String time2 = dateAndNumber[0];
		boolean issameDay = DateUtil.isSameDay(System.currentTimeMillis(), DateUtil.getDate(time2).getTime());
		if(issameDay){
		    int number = Integer.valueOf(dateAndNumber[1]);
	        return number;
		}
		return 0;
	}
	/**
	 * 根据类型检查当天是否可以继续领取
	 * @param player
	 * @param key
	 * @param limit_time
	 * @return
	 */
	public static boolean checkToday(GetOnce getOnce,int limit_time){
		int todayNum = getTodayNum(getOnce);
		return (limit_time-todayNum > 0);
	}
	/**
	 * 增加一条新领取记录
	 * @param playerId
	 * @param key
	 * @return 
	 */
	public static GetOnce addGetOnce(int playerId,String key){
		GetOnce getOnce = new GetOnce();
		getOnce.setDayKey(key);
		getOnce.setPlayerId(playerId);
		StringBuilder sb = new StringBuilder();
		String time = DateUtil.getDateDesc();
		sb.append(time);
		sb.append("_");
		sb.append("1");
		getOnce.setDayNum(sb.toString());
		BaseEngine.getInstace().getGetOnceActionImpl().insert(getOnce);
		return BaseEngine.getInstace().getGetOnceActionImpl().getGetOnce(playerId, key);
	}
	
	/**
	 * 更新领取记录
	 * @param getOnce
	 */
	public static void addOnceTime(GetOnce getOnce){
		int time = getTodayNum(getOnce);
		time ++;
		getOnce.setDayNum(DateUtil.getDateDesc()+"_"+time);
		BaseEngine.getInstace().getGetOnceActionImpl().modify(getOnce);
	}
	
	public static void addOnceTime(GetOnce getOnce,int addTime){
        int time = getTodayNum(getOnce);
        time += addTime;
        getOnce.setDayNum(DateUtil.getDateDesc()+"_"+time);
        BaseEngine.getInstace().getGetOnceActionImpl().modify(getOnce);
    }
	
}
