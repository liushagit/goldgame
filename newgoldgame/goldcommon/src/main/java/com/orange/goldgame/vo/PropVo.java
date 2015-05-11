package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;

public class PropVo implements IOutputMeasageVo{
	private int propId;
	private String propName;
	private int aword;
	private int gstoneNumber;
	private int kaNumber;
	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public int getGstoneNumber() {
		return gstoneNumber;
	}

	public void setGstoneNumber(int gstoneNumber) {
		this.gstoneNumber = gstoneNumber;
	}
	public int getAword() {
		return aword;
	}

	public void setAword(int aword) {
		this.aword = aword;
	}

	public int getKaNumber() {
		return kaNumber;
	}

	public void setKaNumber(int kaNumber) {
		this.kaNumber = kaNumber;
	}

	@Override
	public int getLength() {
		int length = 0;
		length = 4*4 + 2 + propName.getBytes().length;
		return length;
	}
	@Override
	public OutputMessage parseMessage() {
		OutputMessage msg = new OutputMessage(getLength());
		msg.putInt(propId);
		msg.putString(propName);
		msg.putInt(aword);
		msg.putInt(gstoneNumber);
		msg.putInt(kaNumber);
		return msg;
	}

}
