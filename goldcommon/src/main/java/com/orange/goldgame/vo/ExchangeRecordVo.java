package com.orange.goldgame.vo;

import java.io.Serializable;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.ExchangeRecord;
import com.orange.goldgame.util.IOutputMeasageVo;


public class ExchangeRecordVo implements IOutputMeasageVo,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExchangeRecord recordMessage;
	private String goodsIntr;
	public void setGoodsIntr(String goodsIntr) {
		this.goodsIntr = goodsIntr;
	}

	public void setRecordMessage(ExchangeRecord recordMessage) {
		this.recordMessage = recordMessage;
	}

	@Override
	public int getLength() {
		int length = 0;
		int goodsLength = goodsIntr.getBytes().length;
		/*int recordLength = recordMessage.getExchaneTime().getBytes().length;
		length = 4*2 + 2*2 + goodsLength + recordLength;*/
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage(getLength());
		/*msg.putInt(recordMessage.getGoodsId());
		msg.putString(goodsIntr);
		msg.putString(recordMessage.getExchaneTime());
		msg.putInt(recordMessage.getIsExchane());*/
		return msg;
	}
	
}
