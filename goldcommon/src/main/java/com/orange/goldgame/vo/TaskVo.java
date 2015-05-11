package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;


public class TaskVo implements IOutputMeasageVo{
	private int id;
	private String taskName;
	private String introduction;
	private int aword;
	private byte taskState;
	private byte taskType;

	@Override
	public int getLength() {
		int length = 0;
		length = 4*2+2*1+taskName.getBytes().length+introduction.getBytes().length;
		return length;
	}

	@Override
	public OutputMessage parseMessage() {
		OutputMessage message = new OutputMessage(getLength());
		message.putInt(id);
		message.putString(taskName);
		message.putString(introduction);
		message.putInt(aword);
		message.putByte(taskState);
		message.putByte(taskType);
		return message;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getAword() {
        return aword;
    }

    public void setAword(int aword) {
        this.aword = aword;
    }

    public byte getTaskState() {
        return taskState;
    }

    public void setTaskState(byte taskState) {
        this.taskState = taskState;
    }

    public byte getTaskType() {
        return taskType;
    }

    public void setTaskType(byte taskType) {
        this.taskType = taskType;
    }

}
