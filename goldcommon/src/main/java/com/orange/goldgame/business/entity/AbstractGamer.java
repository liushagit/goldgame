package com.orange.goldgame.business.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGamer {
	private int playerId;

	/**
	 * 玩家手牌，即是玩家游戏时手中的三张牌
	 */
	private HandCards handCards;

	/**
	 * 玩家当前下的总金额
	 */
	private int currentTotalState;
	
	/**
	 * 跟谁比过牌
	 */
	private List<AbstractGamer> fireGamers;

	/**
	 * 房间id
	 * 
	 */
	private String roomId;
	
	private AbstractGamerProp prop;

    public AbstractGamerProp getProp() {
		return prop;
	}

	public void setProp(AbstractGamerProp prop) {
		this.prop = prop;
	}

	public int getPlayerId() {
        return playerId;
    }

    public HandCards getHandCards() {
        return handCards;
    }

    public List<AbstractGamer> getFireGamers() {
        return fireGamers;
    }

    public String getRoomId() {
        return roomId;
    }

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

    public void setHandCards(HandCards handCards) {
        this.handCards = handCards;
    }

    public int getCurrentTotalState() {
        return currentTotalState;
    }

    public void setCurrentTotalState(int currentTotalState) {
        this.currentTotalState = currentTotalState;
    }

    public void reset(){
        handCards = null;
        currentTotalState = 0;
        fireGamers.clear();
        prop.reset();
    }
    
    public void init(){
        handCards = null;
        currentTotalState = 0;
        fireGamers = new ArrayList<AbstractGamer>();
        prop = new AbstractGamerProp();
    }
	

}
