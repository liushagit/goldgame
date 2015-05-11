package com.orange.goldgame.action;

import com.orange.goldgame.domain.GetPrize;

public interface GetPrizeAction {
    
    /**
     * 通过玩家ID查找每日奖励
     * @param playerId
     * @return
     */
    public GetPrize getPrizeByPlayerId(int playerId);
    
    /**
     * 加入记录
     */
    public int insert(GetPrize getPrize);
    
    /**
     * 通过ID查找
     */
    public GetPrize getPrizeById(int id);
    
    /**
     * 更新
     * @param getPrize
     */
    public void update(GetPrize getPrize);
    
}
