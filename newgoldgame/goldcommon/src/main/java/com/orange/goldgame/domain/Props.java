package com.orange.goldgame.domain;

import java.io.Serializable;

public class Props implements Serializable{
	/**道具的Id*/
	private int propId;
	/**道具的名称*/
	private String propName;
	/**购买道具的奖励*/
	private int aword;
	/**道具的价格*/
	private int gstoneNumber;
	/**道具是几倍卡*/
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
	
	/**道具的价格*/
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

}
