/**
 * SuperStarCommon
 * com.orange.superstar.login.domain
 * User.java
 */
package com.orange.goldgame.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shaojieque 
 * 2013-5-4
 */
public class User implements Serializable {
	/** 男 */
	public static final int SEX_MALE = 0;
	/** 女 */
	public static final int SEX_FEMALE = 1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private int id;
	private String name;
	private String niceName;
	private String logo;
	private String headImg;
	private String clothesImg;
	private String trousersImg;
	private int sex;
	private Date birthday;
	private int area;
	private int level;
	private int exp; // 声望
	private int coin;
	private int charm; // 魅力
	private int fans;
	private int winCount;
	private int loseCount;
	private Date loginTime;
	private int loginCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNiceName() {
		return niceName;
	}

	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getClothesImg() {
		return clothesImg;
	}

	public void setClothesImg(String clothesImg) {
		this.clothesImg = clothesImg;
	}

	public String getTrousersImg() {
		return trousersImg;
	}

	public void setTrousersImg(String trousersImg) {
		this.trousersImg = trousersImg;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getCharm() {
		return charm;
	}

	public void setCharm(int charm) {
		this.charm = charm;
	}

	public int getFans() {
		return fans;
	}

	public void setFans(int fans) {
		this.fans = fans;
	}

	public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	public int getLoseCount() {
		return loseCount;
	}

	public void setLoseCount(int loseCount) {
		this.loseCount = loseCount;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", niceName=" + niceName
				+ ", logo=" + logo + ", headImg=" + headImg + ", clothesImg="
				+ clothesImg + ", trousersImg=" + trousersImg + ", sex=" + sex
				+ ", birthday=" + birthday + ", area=" + area + ", level="
				+ level + ", exp=" + exp + ", coin=" + coin + ", charm="
				+ charm + ", fans=" + fans + ", winCount=" + winCount
				+ ", loseCount=" + loseCount + ", loginTime=" + loginTime
				+ ", loginCount=" + loginCount + "]";
	}
}
