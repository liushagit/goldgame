package com.orange.goldgame.business.entity;

/**
 * @author wuruihuang 2013.5.28
 * 玩家游戏状态的枚举类
 */
public enum TableSeatState {
	/** 座位关闭 */
	SEAT_CLOSE,
	/** 已开始 */
	SEAT_BEGIN,
	
	/** 座位默认状态 */
	SEAT_OPEN,
	
	
	/**玩家没有准备 值为0*/
	SEAT_TONKEN,
	/**玩家准备 值为1*/
	SEAT_READY,
	/**玩家游戏中值为2*/
	SEAT_PLAYING,
	/**玩家弃牌 值为3*/ 
	SEAT_GIVEUP,
	/**玩家看牌 值为4*/
	SEAT_LOOK,
	/**玩家输牌 值为5*/
	SEAT_LOSE,
	/**玩家赢牌 值为6*/
	SEAT_WIN
	
}
