package com.orange.goldgame.server.service;

import com.orange.goldgame.action.PlayerAction;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.SynMoney;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.server.ShareGoldCache;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.AppellationManager;
import com.orange.goldgame.server.manager.GameTableManager;
import com.orange.goldgame.server.manager.GamerSet;

/**
 * 玩家消费各种钱
 * @author guojiang
 *
 */
public class GoldService {
	private static final PlayerAction action = BaseEngine.getInstace().getPlayerActionIpml();
	public static boolean consumeInCopper(Player player,int amount){
		if (amount < 0) {
			throw new GoldException("您的金币不足！");
		}
		SynMoney money=ShareGoldCache.getCacheGold(player.getId());
		if(money!=null){
			if(money.getCopper()>=amount){
				money.setCopper(money.getCopper() - amount);
				ShareGoldCache.setCacheGold(player.getId(), money);
				player.setCopper(money.getCopper());
				AppellationManager.getInstance().changeAppellation(player, player.getCopper());
				action.modifyPlayer(player);
				return true;
			}else {			
				throw new GoldException("您的金币不足！");
			}
		}else{
			if (player.getCopper() >= amount) {
				player.setCopper(player.getCopper() - amount);
				//修改称谓
				AppellationManager.getInstance().changeAppellation(player, player.getCopper());
				action.modifyPlayer(player);
				return true;
			}else {			
				throw new GoldException("您的金币不足！");
			}
		}
	}
	
	/**
	 * 扣除玩家金币，允许负数
	 * @param player
	 * @param amount
	 * @return
	 */
	public static boolean consumeInCopperNegative(Player player,int amount){
        if (amount < 0) {
            throw new GoldException("您的金币不足！");
        }
        player.setCopper(player.getCopper() - amount);
        //修改称谓
        AppellationManager.getInstance().changeAppellation(player, player.getCopper());
        action.modifyPlayer(player);
        return true;
    }
	
	//消费并更新Gamer
	public static boolean consumeCopperAndUpdateGamer(Player player,int amount){
        if(player == null) return false;
        consumeInCopper(player, amount);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(player.getId());
        if(gamer != null && !GameTableManager.isInMatch(gamer)){
            gamer.setLeftGolds(gamer.getLeftGolds()-amount);
        }
        return true;
    }
	
	public static boolean addCopper(Player player,int amount){
		if (amount < 0) {
			throw new GoldException("add copper error");
		}
		/********/
		long copper=0;
		SynMoney money=ShareGoldCache.getCacheGold(player.getId());
		if(money!=null){
			copper=money.getCopper()+amount;
		}else{
			copper = player.getCopper() + amount;					
		}
		if (copper > Integer.MAX_VALUE) {
			copper = Integer.MAX_VALUE;
		}
		player.setCopper((int)copper);
		if(money!=null){
			money.setCopper(player.getCopper());
			ShareGoldCache.setCacheGold(player.getId(),money);			
		}
		
		//修改称谓
        AppellationManager.getInstance().changeAppellation(player, player.getCopper());
		action.modifyPlayer(player);
		return true;
	}
	//添加金币并更新Gamer
    public static boolean addCopperAndUpdateGamer(Player player,int amount){
        if(player == null) return false;
        addCopper(player, amount);
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(player.getId());
        if(gamer != null && !GameTableManager.isInMatch(gamer)){
            gamer.setLeftGolds(gamer.getLeftGolds()+amount);
        }
        return true;
    }
	
	public static boolean consumeInGold(Player player,int amount){
		if (amount < 0) {
			throw new GoldException("consum gold error");
		}		
		SynMoney money=ShareGoldCache.getCacheGold(player.getId());
		if(money!=null){
			if(money.getGold()>=amount){
				money.setGold(money.getGold() - amount);
				player.setGolds(money.getGold());
				ShareGoldCache.setCacheGold(player.getId(),money);
				action.modifyPlayer(player);
//				//首次充值且消费
//				prizePayAndSume(player);
				return true;			
			}else{
				throw new GoldException("您的宝石不足！");
			}
		}else{
			if (player.getGolds() >= amount) {
				player.setGolds(player.getGolds() - amount);
				action.modifyPlayer(player);
//				//首次充值且消费
//				prizePayAndSume(player);
				return true;
			}else {
				throw new GoldException("您的宝石不足！");
			}			
		}
	}
	

	public static boolean addGold(Player player,int amount){
		if (amount < 0) {
			throw new GoldException("add gold error");
		}
		long gold = 0;
		SynMoney money=ShareGoldCache.getCacheGold(player.getId());
		if(money!=null){
			gold=money.getGold()+amount;
		}else{
			gold=player.getGolds() + amount;
		}
		if (gold > Integer.MAX_VALUE) {
			gold = Integer.MAX_VALUE;
		}
		player.setGolds((int)gold);
		if(money!=null){
			money.setGold(player.getGolds());
			ShareGoldCache.setCacheGold(player.getId(), money);
		}
		action.modifyPlayer(player);
		return true;
	}
	
	//根据场景拿到符合要求的Gold，如果在比赛场，就拿Gamer的金币
	public static int getLeftCopper(Player player){
	    Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(player.getId());
	    if(gamer !=null && GameTableManager.isInMatch(gamer)){
	        return gamer.getLeftGolds();
	    }
	    return player.getCopper();
	}
	
//	private static void prizePayAndSume(Player player) {
//		if(player.getFirstPayRecord()==null) return;
//		if (DateUtil.isInDateRange(ActivityService.beginTime, ActivityService.endTime)) {
//			ActivityPayRecord record=player.getFirstPayRecord();
//			if(record!=null&&record.getState()!=Constants.COMMON_TYPE_ONE){
//				PayService.activityPayPrize(player, record, 0, Constants.ACTIVITY_FIRST_PAY);
//			}
//		}
//	}
}
