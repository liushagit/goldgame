package com.orange.goldgame.vo;

import java.io.Serializable;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;

public class GoodsVo implements IOutputMeasageVo,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**兑换实物id*/
	private int goodsId;
	/**兑换实物的名称*/
	private String goodsName;
	/**要求兑换的兑换券数量*/
	private int exchangeVoucher;
	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getExchangeVoucher() {
		return exchangeVoucher;
	}

	public void setExchangeVoucher(int exchangeVoucher) {
		this.exchangeVoucher = exchangeVoucher;
	}

	@Override
	public int getLength() {
		int length = 0;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage();
		msg.putInt(goodsId);
		msg.putString(goodsName);
		msg.putInt(exchangeVoucher);
		return msg;
	}

}
