package com.orange.goldgame.server.manager;

import java.util.Date;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.log.LoggerName;
import com.orange.goldgame.action.ExchangeRecordAction;
import com.orange.goldgame.action.GoodsAction;
import com.orange.goldgame.action.PropsAction;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.ExchangeRecord;
import com.orange.goldgame.domain.GoodsConfig;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerConsume;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.server.RoomTalkService;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.pay.PayActivityService;
import com.orange.goldgame.server.service.GoldService;

/**
 * 商城管理
 * @author yesheng
 *
 */
public class ShopManager {
    
    private Logger log = LoggerFactory.getLogger(ShopManager.class);
    private Logger log_gold = LoggerFactory.getLogger(LoggerName.GOLD);
    
    private static final ShopManager instance = new ShopManager();
    
    private PropsAction propsAction = BaseEngine.getInstace().getPropActionIpml();
    private GoodsAction goodsAction = BaseEngine.getInstace().getGoodsActionIpml();
    private ExchangeRecordAction exchangeRecordAction = BaseEngine.getInstace().getExchangeRecordAction();
    
    //获取实例的对外接口
    public static ShopManager getInstance(){
        
        return instance;
    }
    
    private ShopManager(){
        
    }
    
    /**
     * 兑换实物逻辑
     * @param player
     * @param goodsId
     */
    public int changeGoods(Player player,int goodsId,String cellphone) throws GoldException{
        GoodsConfig config = goodsAction.getGoodsById(goodsId);
        int num = config.getExchangVoucher();
        PlayerProps pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.exchange_prop);
        int currNum = (pp==null?0:pp.getNumber());
        if(currNum<num){
            throw new GoldException("兑换券数量不足！");
        }
        
        //修改DB
        PropCacheAction.releasePlayerProp(player, pp,num);
        
        ExchangeRecord record = new ExchangeRecord();
        record.setCellphone(cellphone);
        record.setExchangetime(new Date());
        record.setGoodsid(goodsId);
        record.setIsexchange(1);
        record.setPlayerid(player.getId());
        exchangeRecordAction.insert(record);
      //保存玩家兑换券消耗记录
        PlayerConsume consume = RoomTalkService.
        		addPlayerConsumeRecord(player.getId(),currNum,goodsId,num,currNum-num,Constants.COMMON_TYPE_TWO);
        BaseEngine.getInstace().getPlayerConsumeAction().addPlayerConsume(consume);
        return num;
    }
    
    /**
     * 是否可以兑换实物
     * @return
     */
    public boolean isAbleChangeGoods(Player player,int goodsId){
        GoodsConfig config = goodsAction.getGoodsById(goodsId);
        int num = config.getExchangVoucher();
        PlayerProps pp = PropCacheAction.getplayerPropByPropid(player, PropCacheAction.exchange_prop);
        int currNum = (pp==null?0:pp.getNumber());
        if(currNum<num){
            return false;
        }
        return true;
    }
    
    /**
     * 购买道具
     * @param player
     * @param packagePropsId
     */
    public PackageProps buyGoods(Player player , int packagePropsId) throws GoldException{
        log.debug("玩家"+player.getNickname()+"购买道具");
        PackageProps prop = ResourceCache.getInstance().getPackageProps().get(packagePropsId);
        int propMoney = prop.getPropsMoney();
        ErrorCode res = PayActivityService.off50(player, packagePropsId,propMoney);
        if (res.getStatus() == ErrorCode.SUCC) {
        	propMoney = Integer.parseInt(res.getMsg());
		}
        int hasMoney = player.getGolds();
        
        //如果玩家不够宝石，则抛异常
        if(hasMoney < propMoney){
            throw new GoldException("您的宝石不足，请及时充值！");
        }
        
        //如果够宝石，则减少宝石，并且对道具进行添加
        GoldService.consumeInGold(player, propMoney);
        //1~4是金币
        if(isBuyGold(packagePropsId)){
            GoldService.addCopperAndUpdateGamer(player, prop.getPropsNumber());
        }else {
			PropCacheAction.addProp(player, packagePropsId,prop.getPropsNumber());
		}
        //保存玩家宝石消耗记录
        PlayerConsume record = RoomTalkService.addPlayerConsumeRecord(player.getId(), hasMoney, packagePropsId, propMoney, player.getGolds(), Constants.COMMON_TYPE_ZERO);
        BaseEngine.getInstace().getPlayerConsumeAction().addPlayerConsume(record);
        //玩家ID|玩家消费之后的宝石数|道具ID|道具价格
        log_gold.info("buy_goods|" + player.getId()+"|"+hasMoney+"|"+packagePropsId+"|"+propMoney);
        return prop;
    }
    
    

    
    private boolean isBuyGold(int packagePropsId){
        if(packagePropsId == 1 || packagePropsId == 2 
                || packagePropsId == 3 || packagePropsId==4){
            return true;
        }
        return false;
    }
}
