package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;


public class SystemMessageVo implements IOutputMeasageVo{

	private int playerId; 
	private String nickname; 
	private String msg; 
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public int getLength() {
		int length = 0;
		length = 4 + 2*2+nickname.getBytes().length+msg.getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage out = new OutputMessage(getLength());
		out.putInt(playerId);
		out.putString(nickname);
		out.putString(msg);
		return out;
	}

}
