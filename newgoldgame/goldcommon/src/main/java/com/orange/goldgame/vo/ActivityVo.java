package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;

public class ActivityVo implements IOutputMeasageVo{

	
	private int activityId;
	private String activityName;
	private String activityIntro;
	
	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityIntro() {
		return activityIntro;
	}

	public void setActivityIntro(String activityIntro) {
		this.activityIntro = activityIntro;
	}

	
	@Override
	public int getLength() {
		int length = 0;
		length = 4 + 2*2+activityName.getBytes().length+activityIntro.getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage message = new OutputMessage(getLength());
		message.putInt(activityId);
		message.putString(activityName);
		message.putString(activityIntro);
		return message;
	}

}
