package com.orange.goldgame.interfaces;

import com.orange.goldgame.business.entity.AbstractGamer;
import com.orange.goldgame.business.entity.ErrorCode;

/**
 * 服务类
 * @author guojiang
 *
 */
public interface GamerInGameContextBehaviourInterface {
    
    /**
     * 玩家加入
     * @param AbstractGamer
     * @return
     */
    ErrorCode gamerJoin(AbstractGamer gamer);
    
    /**
     * 玩家离开
     * @param AbstractGamer
     * @return
     */
    ErrorCode gamerLeave(AbstractGamer gamer);
    
    /**
     * 玩家准备
     * @param AbstractGamer
     * @return
     */
    ErrorCode gamerReady(AbstractGamer gamer);
    
    /**
     * 玩家取消准备
     * @param AbstractGamer
     * @return
     */
    ErrorCode gamerUnReady(AbstractGamer gamer);
    
    /**
     * 玩家看牌
     * @param AbstractGamer
     * @return
     */
    ErrorCode gamerLookCard(AbstractGamer gamer);
    
    /**
     * 玩家放弃
     * @param AbstractGamer
     * @return
     */
    ErrorCode gamerGiveUp(AbstractGamer gamer);
    
    /**
     * 玩家跟注
     * @param AbstractGamer
     * @return
     */
    ErrorCode gamerFollowStake(AbstractGamer gamer);
    
    /**
     * 玩家加注
     * @param AbstractGamer
     * @return
     */
    ErrorCode gamerAddStake(AbstractGamer gamer,int stakeId);

    
    /**
     * 玩家比牌
     * @param AbstractGamer
     * @return
     */
    ErrorCode gamerCompareCard(AbstractGamer src,AbstractGamer dsc);
}
