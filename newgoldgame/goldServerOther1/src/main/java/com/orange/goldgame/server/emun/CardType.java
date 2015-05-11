package com.orange.goldgame.server.emun;

/**
 * 牌型
 * @author wuruihuang 2013.6.4
 */
public enum CardType {
	/** 散牌：单张 1*/
	SINGLE,
	/** 成对: 对子 2 ---有特殊情况*/
	DOUBLE,
	/** 成顺子：顺子或拖拉机 3*/
	FREQUENCE,
	/** 成花色：金花 4*/
	SAME_COLOUR,
	/** 既成顺子又成花色：顺金 5*/
	FREQUENCE_SAME_COLOUR,
	/** 成三条：豹子或炸弹 6*/
	BOMB,
	/** 花色不同的235：特殊牌 7 ----有特殊情况*/
	SPECIAL;
}
