package com.orange.goldgame.server.domain;

/**
 * @author wuruihuang 2013.5.20
 * 游戏场次
 */
public class Area {
    
    public static final int nomalId = 4;
    public static final int richId = 5;
    public static final int goldId = 6;
    
	private short areaId;
	private short areaClass;
	private short areaType;
	private String introdution;
	private int limitGolds;
	private int singleGolds;
	
	public void setAreaId(short areaId) {
		this.areaId = areaId;
	}
	public short getAreaId() {
		return areaId;
	}
	public short getAreaClass() {
		return areaClass;
	}
	public void setAreaClass(short areaClass) {
		this.areaClass = areaClass;
	}
	public short getAreaType() {
		return areaType;
	}
	public void setAreaType(short areaType) {
		this.areaType = areaType;
	}
	public String getIntrodution() {
		return introdution;
	}
	public void setIntrodution(String introdution) {
		this.introdution = introdution;
	}
	public int getLimitGolds() {
		return limitGolds;
	}
	public void setLimitGolds(int limitGolds) {
		this.limitGolds = limitGolds;
	}
	public int getSingleGolds() {
		return singleGolds;
	}
	public void setSingleGolds(int singleGolds) {
		this.singleGolds = singleGolds;
	}
}
