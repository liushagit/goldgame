package com.orange.goldgame.business.entity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.StakeConfig;

/**
 * table属性
 * 
 * @author guojiang
 * 
 */
public abstract class GameContext {
	private final transient Lock r = new ReentrantLock(); 
	public Lock getR() {
		return r;
	}
    private int currentPlayerId;
    /**
     * 桌子状态
     */
    private GameTableState status ;
    /**
     * 桌子的位置
     */
    private GamerContextSeat[] seats;
    /**
     * 最后操作时间
     */
    private Long lastOperateTime;
    /**
     * 轮数
     */
    private int turns;
    /**
     * 参与过本局游戏玩家的列表
     */
    private List<AbstractGamer> joinedGameList;
    /**
     * 扑克牌
     */
    private Poker poker;
    /**
     * 总注
     */
    private int totalStake;
    /**
     * 单注
     */
    private int singeStake;
    /**
     * 庄家
     */
    private AbstractGamer banker;
    /**
     * 局数
     */
    private int inning;
    /**
     * 确认人数
     */
    private int confirmAmount;
    /**
     * 注码类型
     */
    private Map<Integer, StakeConfig> stakeMap;
    /**
     * 场地配置
     * @return
     */
    private AreaConfig areaConfig;
    /**
     * 能容纳的人数数量
     */
    private int maxNumber;
    
    public GameTableState getStatus() {
        return status;
    }
    public GamerContextSeat[] getSeats() {
        return seats;
    }
    public Long getLastOperateTime() {
        return lastOperateTime;
    }
    public int getTurns() {
        return turns;
    }

    public List<AbstractGamer> getJoinedGameList() {
        return joinedGameList;
    }

    public Poker getPoker() {
        return poker;
    }
    public int getTotalStake() {
        return totalStake;
    }
    public void changeTotalStake(int golds){
    	if (golds <= 0) {
			return;
		}
    	totalStake += golds;
    }
    public int getSingeStake() {
        return singeStake;
    }
    public AbstractGamer getBanker() {
        return banker;
    }
    public int getInning() {
        return inning;
    }
    public Map<Integer, StakeConfig> getStakeMap() {
        return stakeMap;
    }
    public AreaConfig getAreaConfig() {
        return areaConfig;
    }
    public int getMaxNumber() {
        return maxNumber;
    }
	public int getCurrentPlayerId() {
		return currentPlayerId;
	}
	public void setCurrentPlayerId(int currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
	}
    public void setLastOperateTime(Long lastOperateTime) {
        this.lastOperateTime = lastOperateTime;
    }
    public int getConfirmAmount() {
        return confirmAmount;
    }
    public void increaseConfirmAmount(){
        confirmAmount++;
    }
    public void setStatus(GameTableState status) {
        this.status = status;
    }
    public void setTurns(int turns) {
        this.turns = turns;
    }
    public void setTotalStake(int totalStake) {
        this.totalStake = totalStake;
    }
    public void setSingeStake(int singeStake) {
        this.singeStake = singeStake;
    }
    public void setConfirmAmount(int confirmAmount) {
        this.confirmAmount = confirmAmount;
    }
    public void addTotalStake(int addGold){
        this.singeStake += addGold;
    }
    public void setSeats(GamerContextSeat[] seats) {
        this.seats = seats;
    }
    public void setJoinedGameList(List<AbstractGamer> joinedGameList) {
        this.joinedGameList = joinedGameList;
    }
    public void setPoker(Poker poker) {
        this.poker = poker;
    }
    public void setBanker(AbstractGamer banker) {
        this.banker = banker;
    }
    public void setInning(int inning) {
        this.inning = inning;
    }
    public void setStakeMap(Map<Integer, StakeConfig> stakeMap) {
        this.stakeMap = stakeMap;
    }
    public void setAreaConfig(AreaConfig areaConfig) {
        this.areaConfig = areaConfig;
    }
    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }
    
}
