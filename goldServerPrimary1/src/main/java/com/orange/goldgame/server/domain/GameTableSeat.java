/**
 * CardGameServer
 * com.card.game.mt.domain
 * GameTableSeat.java
 */
package com.orange.goldgame.server.domain;

import com.orange.goldgame.server.emun.TableSeatState;

/**
 * @author wuruihuang 2013.5.25
 */
public class GameTableSeat{
	/**
	 * 通过数组下标加1的和来创建游戏椅子
	 */
	public GameTableSeat(int id) {
		this.seatId = id;
		this.state = TableSeatState.SEAT_OPEN;
	}
	/** GameTableSeat ID */
	private int seatId;
	/** 游戏椅子的状态，是个枚举类型 */
	private TableSeatState state;
	/** 玩家 */
	private Gamer gamer;



	public int getId() {
		return seatId;
	}

	public void setId(int id) {
		this.seatId = id;
	}

	public TableSeatState getState() {
		return state;
	}

	public void setState(TableSeatState state) {
		this.state = state;
	}
    public Gamer getGamer() {
        return gamer;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
    }

}
