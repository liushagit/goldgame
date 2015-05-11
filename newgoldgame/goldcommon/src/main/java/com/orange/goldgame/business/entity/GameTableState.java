package com.orange.goldgame.business.entity;

/**
 * 游戏桌的状态
 * @author wuruihuang 2013.5.28
 *
 */
public enum GameTableState {
	/**空闲*/
	TABLE_EMPTY,
	/**满人*/
	TABLE_FULL,
	/**已比牌*/
	TABLE_FIRECARD,
	/**已发牌*/
	TABLE_DEALCARD,
	/**已结束*/
	TABLE_OVERGAMER
}
