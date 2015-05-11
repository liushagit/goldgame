package com.orange.goldgame.business.entity;


/**
 * 
 * @author guojiang
 *
 */
public class GamerContextSeat{
	/**
	 * 通过数组下标加1的和来创建游戏椅子
	 */
	public GamerContextSeat(int id) {
		this.seatId = id;
		this.state = TableSeatState.SEAT_OPEN;
	}
	/** GameTableSeat ID */
	private int seatId;
	/** 游戏椅子的状态，是个枚举类型 */
	private TableSeatState state;
	/** 玩家 */
	private AbstractGamer gamer;


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
	public AbstractGamer getGamer() {
		return gamer;
	}

	public void setGamer(AbstractGamer gamer) {
		this.gamer = gamer;
	}

}
