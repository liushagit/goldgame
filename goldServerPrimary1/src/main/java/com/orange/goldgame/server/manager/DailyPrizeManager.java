package com.orange.goldgame.server.manager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.AppConfigAction;
import com.orange.goldgame.action.GetPrizeAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.GetPrize;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.service.GoldService;

/**
 * 每日领取管理器
 * 单例
 * @author yesheng
 *
 */
public class DailyPrizeManager {
    
    private Logger log = LoggerFactory.getLogger(DailyPrizeManager.class);
    
    private static final DailyPrizeManager dailyPrizeManager = new DailyPrizeManager();
    
    
    private GetPrizeAction getPrizeAction = BaseEngine.getInstace().getGetPrizeActionImpl();
    private AppConfigAction appConfigAction = BaseEngine.getInstace().getAppConfigActionImpl();
    
    
    private DailyPrizeManager(){}
    
    public static DailyPrizeManager getInstance(){
        return dailyPrizeManager;
    }
    
    /**
     * 是否已领取奖励
     * @param player
     * @return
     */
    public boolean isRecive(Player player){
        //领取时间
        Calendar prizeDateCalendar = getrizeDateCalendar();
        
        GetPrize getPrize = getPrizeAction.getPrizeByPlayerId(player.getId());
        if(getPrize == null) return false;
        if(getPrize.getGetTime().getTime()-prizeDateCalendar.getTimeInMillis()>0){
            return true;
        }
        return false;
    }
    
    /**
     * 获取玩家每日奖励
     * @param playerId
     */
    public int receivePrize(Player player){
        
        //领取时间
        Calendar prizeDateCalendar = getrizeDateCalendar();
        
        GetPrize getPrize = getPrizeAction.getPrizeByPlayerId(player.getId());
        //如果不存在信息，则初始化
        if(getPrize == null){
            log.debug("奖励信息不存在，初始化玩家奖励信息！");
            int id = initPlayerDailyPrize(player.getId());
            if(id == 0) return 0;
            getPrize = getPrizeAction.getPrizeById(id);
        }
        
        //如果玩家已经领取，不操作
        if(getPrize.getGetTime().getTime()-prizeDateCalendar.getTimeInMillis()>0){
            log.debug("奖励已领取！");
            return 0;
        }
        //如果玩家未连续领取，则重新计算
        if(prizeDateCalendar.getTimeInMillis()-getPrize.getGetTime().getTime()>=24*60*60*1000){
            log.debug("未连续领取！");
            getPrize.setFlage(0);
        }
        //超出7天，就设置为0
        if(getPrize.getFlage()>=7){
            log.debug("超7天重置！");
            getPrize.setFlage(6);
        }
        
        int flage = getPrize.getFlage();
        String key = getPrize.getPropConfigIds();
        String prizeStr = appConfigAction.findAppConfigByKey(key).getAppValue();
        String[] prizes = prizeStr.split("_");
        
        //获得的奖励
        int prize = Integer.parseInt(prizes[flage]);
        
        //增加玩家金币
        GoldService.addCopperAndUpdateGamer(player, prize);
        
        //修改玩家的每天领取表
        getPrize.setGetTime(new Date());
        getPrize.setFlage(flage+1);
        getPrize.setPrizeTime(prizeDateCalendar.getTime());
        getPrizeAction.update(getPrize);
        
        return prize ;
    }
    
    /**
     * 返回每日领取的列表
     * @return
     */
    public List<Integer> getDailyPrizeList(){
        String prizeStr = appConfigAction.findAppConfigByKey(Constants.DAILY_PRIZE).getAppValue();
        List<Integer> list = new ArrayList<Integer>();
        String[] prizes = prizeStr.split("_");
        for(String prize : prizes){
            list.add(Integer.parseInt(prize));
        }
        return list;
    }
    
    /**
     * 当前登录的天数
     * @param player
     * @return
     */
    public int getPlayerCurrentDaily(Player player){
        //领取时间
        Calendar prizeDateCalendar = getrizeDateCalendar();
        GetPrize getPrize = getPrizeAction.getPrizeByPlayerId(player.getId());
        if(getPrize == null){
            return 1;
        }
        if(getPrize.getFlage()<=0){
            return 1;
        }
        //如果玩家已经领取
        if(getPrize.getGetTime().getTime()-prizeDateCalendar.getTimeInMillis()>0){
            return getPrize.getFlage();
        }
        //如果玩家未连续领取，则重新计算
        if(prizeDateCalendar.getTimeInMillis()-getPrize.getGetTime().getTime()>=24*60*60*1000){
            return 1;
        }
        //超出7天，就设置为0
        if(getPrize.getFlage()>=7){
            return 7;
        }
      
        return getPrize.getFlage()+1;
    }
    
    /**
     * 初始化玩家的每日奖励信息
     * @param playerId
     */
    public int initPlayerDailyPrize(int playerId){
        //领取时间
        Calendar prizeDateCalendar = getrizeDateCalendar();
        
        //初始化getPrize
        GetPrize getPrize = new GetPrize();
        getPrize.setFlage(0);
        getPrize.setGetTime(prizeDateCalendar.getTime());
        String name = "";
        try {
            name = new String("每日领取".getBytes(),"utf8");
        } catch (UnsupportedEncodingException e) {
        }
        getPrize.setName(name);
        getPrize.setPlayerId(playerId);
        getPrize.setPrizeTime(prizeDateCalendar.getTime());
        getPrize.setPropConfigIds(appConfigAction.findAppConfigByKey(Constants.DAILY_PRIZE).getAppKey());
    
        //持久化getPrize
        return getPrizeAction.insert(getPrize);
    }
    
    private static Calendar getrizeDateCalendar(){
        Calendar prizeDateCalendar = Calendar.getInstance();
        prizeDateCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        prizeDateCalendar.set(Calendar.MINUTE, 0);  
        prizeDateCalendar.set(Calendar.SECOND, 0);  
        
        return prizeDateCalendar;
    }
}
