package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;


public class OrderGemstoneVo implements IOutputMeasageVo{
	/**订单号*/
	private String orderId;
	/**玩家的Id*/
	private int playerId;
	/**玩家购买宝石数量*/
	private int gemstone;
	
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getGemstone() {
		return gemstone;
	}

	public void setGemstone(int gemstone) {
		this.gemstone = gemstone;
	}
	@Override
	public int getLength() {
		int length = 0;
		length = 4*2+2+orderId.getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage(getLength());
		msg.putString(orderId);
		msg.putInt(playerId);
		msg.putInt(gemstone);
		return msg;
	}
}
