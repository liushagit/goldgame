package com.orange.goldgame.domain;

import java.io.Serializable;

public class SynMoney implements Serializable {

	private int copper;//玩家游戏币
	private int gold; //玩家宝石
	public int getCopper() {
		return copper;
	}
	public void setCopper(int copper) {
		this.copper = copper;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	
}
