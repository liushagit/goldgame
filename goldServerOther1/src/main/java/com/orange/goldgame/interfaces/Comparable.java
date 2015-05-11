package com.orange.goldgame.interfaces;

import com.orange.goldgame.server.domain.HandCards;


/**
 * 玩家手牌比较大小
 * @author wuruihuang 2013.6.4
 */
public interface Comparable {
	HandCards Comparetor(HandCards handCards);
}
