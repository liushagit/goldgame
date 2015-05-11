package com.orange.goldgame.interfaces;

import com.orange.goldgame.business.entity.ErrorCode;
import com.orange.goldgame.business.entity.GameContext;


/**
 * 游戏上下文的行为
 * @author yesheng
 *
 */
public interface GameContextBehaviour {
    
    /**
     * 开始游戏
     * @param gameContext
     * @return
     */
    ErrorCode startGame(GameContext gameContext);
    
    /**
     * 暂停游戏
     * @param gameContext
     * @return
     */
    ErrorCode stopGame(GameContext gameContext);
    
    /**
     * 继续游戏
     * @param gameContext
     * @return
     */
    ErrorCode continueGame(GameContext gameContext);
    
    /**
     * 结束游戏
     * @param gameContext
     * @return
     */
    ErrorCode endGame(GameContext gameContext);
    
    /**
     * 销毁游戏上下文
     * @param gameContext
     * @return
     */
    ErrorCode destroyGameContext(GameContext gameContext);
    
    
}
