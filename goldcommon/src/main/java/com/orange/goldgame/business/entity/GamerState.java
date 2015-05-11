package com.orange.goldgame.business.entity;

/**
 * 玩家的游戏状态
 * @author wuruihuang 2013.5.28
 */
public enum GamerState {
	/**玩家没有准备 值为0*/
	GAME_NOREADY,
	/**玩家准备 值为1*/
	GAME_READY,
	/**玩家游戏中值为2*/
	GAME_PLAYING,
	/**玩家弃牌 值为3*/ 
	GAME_GIVEUP,
	/**玩家看牌 值为4*/
	GAME_LOOK,
	
	/**玩家输牌 值为5*/
	GAME_LOSE,
	/**玩家赢牌 值为6*/
	GAME_WIN, 
}
