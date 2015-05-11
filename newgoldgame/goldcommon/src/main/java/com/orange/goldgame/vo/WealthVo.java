package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;


public class WealthVo implements IOutputMeasageVo{

	private String nickname;
	private String appellation;
	private int golds;
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAppellation() {
		return appellation;
	}

	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}

	public int getGolds() {
		return golds;
	}

	public void setGolds(int golds) {
		this.golds = golds;
	}

	@Override
	public int getLength() {
		int length = 0;
		length = 4 + 2*2 +nickname.getBytes().length + appellation.getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage(getLength());
		msg.putString(nickname);
		msg.putString(appellation);
		msg.putInt(golds);
		return msg;
	}
}
