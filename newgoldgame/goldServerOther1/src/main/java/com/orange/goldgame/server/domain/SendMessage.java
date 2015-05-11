package com.orange.goldgame.server.domain;


public class SendMessage {
	
	private int playerId;
	private String msg;
	
	public SendMessage(){}
	
	public SendMessage(int playerId,String msg){
		this.playerId = playerId;
		this.msg = msg;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
