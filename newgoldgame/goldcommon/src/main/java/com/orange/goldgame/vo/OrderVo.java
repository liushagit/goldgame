package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;


public class OrderVo implements IOutputMeasageVo{
	/**道具的id*/
	private int playerId;
	/**订单的id*/
	private String orderId;
	/**道具的名称*/
	private String propName; 
	/**道具的价格*/
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

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
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
		length = 4*2 + 2+2 + orderId.getBytes().length + propName.getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage(getLength());
		msg.putString(orderId);
		msg.putInt(playerId);
		msg.putString(propName);
		msg.putInt(gemstone);
		return msg;
	}

}
