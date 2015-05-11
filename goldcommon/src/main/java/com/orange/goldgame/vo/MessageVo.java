package com.orange.goldgame.vo;

import java.io.Serializable;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.Message;
import com.orange.goldgame.util.IOutputMeasageVo;


public class MessageVo implements IOutputMeasageVo,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Message message;

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public int getLength() {
		int length = 0;
		length = 1 + 2*2 + message.getMessage().getBytes().length + message.getSendTime().getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage(getLength());
		msg.putString(message.getMessage());
		msg.putByte(message.getIsCheck());
		msg.putString(message.getSendTime());
		return msg;
	}
	
	
}
