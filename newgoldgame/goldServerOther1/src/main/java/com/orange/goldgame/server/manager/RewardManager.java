package com.orange.goldgame.server.manager;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.AppConfigAction;
import com.orange.goldgame.action.AreaConfigAction;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AppConfig;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.GetOnce;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.service.GoldService;


/**
 * 奖励系统
 * @author yesheng
 *
 */
public class RewardManager {
    
    private static final RewardManager instance = new RewardManager();
    public static RewardManager getInstance(){
        return instance;
    }
    
    private RewardManager(){}

    private Logger log = LoggerFactory.getLogger(RewardManager.class);
    
    private AppConfigAction appConfigAction = BaseEngine.getInstace().getAppConfigActionImpl();
    private AreaConfigAction areaConfigAction = BaseEngine.getInstace().getAreaConfigAction();
    
    public int gainRewardByAreaIdAndGolds(Gamer gamer,int areaId,int golds,Player player){
    	if (gamer.isRobot()) {
			return 0;
		}
    	return gainRewardByAreaIdAndGolds(areaId, golds, player);
    }
    /**
     * 根据所在的普通游戏场和所赢的金币获取对应奖励
     * @param areaId 游戏场ID
     * @param golds 本局赢的金币
     * @param player 对应的玩家
     * @return
     */
    private int gainRewardByAreaIdAndGolds(int areaId,int golds,Player player){
        //获取奖励规则
        AppConfig config = appConfigAction.findAppConfigByKey(Constants.REWARD_RULE);
        String configValue = config.getAppValue();
        String[] rewardRules = configValue.split("\\|");
        GetOnce once = GetOnceManager.getPlayerGetOnce(player, Constants.DAILY_EXCHANGE);
        int limitAmount = Integer.parseInt(ResourceCache.getInstance()
                .getAppConfigs().get(Constants.EXCHANGLE_LIMIT).getAppValue());
        boolean isLimit = false;
        if(GetOnceManager.checkToday(once, limitAmount)){
            isLimit = false;
        }else{
            isLimit = true;
        }
        
        //遍历每个奖励规则
        for(String rewardRule : rewardRules){
            log.debug("奖励规则Str:"+rewardRule);
            String[] rewardStr = rewardRule.split("_");
            //游戏场ID
            String rewardAreaIdStr = rewardStr[0];
            log.debug("游戏场ID："+rewardAreaIdStr);
            //奖励需求的金币
            String rewardNeedGoldsStr = rewardStr[1];
            log.debug("需求金币："+rewardNeedGoldsStr);
            //奖励道具
            String rewardGainStr = rewardStr[2];
            log.debug("奖励道具："+rewardGainStr);
            
            //如果满足奖励条件，则进行奖励，并返回true
            if(!isLimit && rewardAreaIdStr.equals(areaId+"") 
                    && golds>=Integer.parseInt(rewardNeedGoldsStr)){
                String[] rewards = rewardGainStr.split("-");
                //道具类型 1金币   2兑换券
                String rewardType = rewards[0];
                //道具数量
                String rewardNum = rewards[1];
                
                if(rewardType.equals("1")){
                    GoldService.addCopperAndUpdateGamer(player, Integer.parseInt(rewardNum));
                }else if(rewardType.equals("2")){
                    PropCacheAction.addProp(player, PropCacheAction.exchange_prop,Integer.parseInt(rewardNum));
                    GetOnceManager.addOnceTime(once,Integer.parseInt(rewardNum));
                }
                return Integer.parseInt(rewardNum);
            }
        }
        return 0;
    }
    
    /**
     * 根据比赛场获得对应的奖励
     * @param areadId
     * @param player
     * @return
     */
    public boolean gainRewardByAreaId(int areadId,Player player){
        AreaConfig areaConfig = areaConfigAction.quaryAreaConfigById(areadId);
        String awardStr = areaConfig.getAword();
        String[] awards = awardStr.split("\\|");
        for(String award : awards){
            String[] awardStrs = award.split("_");
            //道具类型 1金币   2兑换券
            String awardType = awardStrs[0];
            //道具数量
            String awardNum = awardStrs[1];
            if(awardType.equals("1")){
                GoldService.addCopperAndUpdateGamer(player, Integer.parseInt(awardNum));
            }
            else if(awardType.equals("2")){
                PropCacheAction.addProp(player, PropCacheAction.exchange_prop);
            }
        }
        //持久化
        return true;
    }
    
}
