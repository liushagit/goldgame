package com.orange.goldgame.server.domain;

public class OnlieReward {
	
	private int time;
	private int prize;
	private String rewardTime;
	
	public OnlieReward(){}
	
	public OnlieReward(int time,int prize){
		this.time=time;
		this.prize=prize;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getPrize() {
		return prize;
	}

	public void setPrize(int prize) {
		this.prize = prize;
	}

	public String getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(String rewardTime) {
		this.rewardTime = rewardTime;
	}
	
	

}
