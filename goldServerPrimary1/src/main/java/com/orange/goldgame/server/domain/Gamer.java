package com.orange.goldgame.server.domain;

import java.util.ArrayList;
import java.util.List;

import com.juice.orange.game.container.GameRoom;
import com.orange.goldgame.domain.BaseObject;
import com.orange.goldgame.server.emun.GamerState;

/**
 * @author wuruihuang 2013.5.31
 * 表示玩家
 */
public class Gamer extends BaseObject implements Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2643252229314218995L;
	/**玩家*/
	private int playerId;
	/**玩家手牌，即是玩家游戏时手中的三张牌*/
	private HandCards handCards;
	/**由于玩家动作（看牌、弃牌、比牌输赢）而使玩家的具有的游戏状态，默认是无游戏状态*/
	private GamerState state = GamerState.GAME_NOREADY;
	/**玩家花费金币总数，默认是0*/
	private int golds = 0;
	/**玩家当前下的金额*/
	private int currentState = 0;
	/**玩家剩余金币*/
	private int leftGolds = 0;
	/**玩家使否使用禁比卡，默认是没有使用*/
	private int useBanCard = 0;
	/**玩家使否使用n倍卡，默认是没有使用*/
	private int useNcard = 1;
	/**玩家主动比牌的次数，默认是0*/
	private byte compareCardTimes = (byte)0;
	/**是否可以明牌，默认是不能明牌*/
	private byte isLookable = (byte)0;
	/**明牌给别的玩家，默认是无*/
	private int lookCardGamerId = 0;
	/** 是否是机器人 */
	private boolean isRobot = false;
	/** 机器人是否胜利 默认为0 胜利为1 失败为-1**/
	private int win = 0;
	/** 是否是庄家 */
	private boolean isBaner = false;
	/** 是否已看牌 */
	private boolean isLookCard = false;
	/** 跟谁比过牌*/
	private List<Gamer> fireGamer = new ArrayList<Gamer>();
	/** 无操作的局数*/
	private int noplayAmount = 0;
	/** 是否已操作*/
	private volatile boolean isOperate = false;
	private int friendId = 0;
	private Long lastAddGame = 0L;
	/**当前房间的ID**/
	private String roomId = "";
	/**换房成功的前房间ID**/
	private String lastRoomId="";
	

	public void init(){
		reset();
	}
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getLastRoomId() {
		return lastRoomId;
	}

	public void setLastRoomId(String lastRoomId) {
		this.lastRoomId = lastRoomId;
	}

	/**
	 * 根据玩家player的id来构造玩家角色gamer
	 */
	public Gamer(int playerId) {
		this.playerId = playerId;
	}
	
	//Gamer状态重置
	public void reset(){
	    handCards = null;
	    state = GamerState.GAME_NOREADY;
	    golds = 0;
	    useBanCard = 0;
	    compareCardTimes = (byte)0;
	    isLookable = (byte)0;
	    lookCardGamerId = 0;
	    isBaner = false;
	    isLookCard = false;
	    win = 0;
	    useNcard = 1;
	    isOperate = false;
	    fireGamer.clear();
	}
	
	/**增加玩家花费金币数
     * @param golds 
     * @param gameRoom 
     */
    public void consumeGolds(int golds) {
        this.golds +=golds; 
        this.currentState = golds;
        this.leftGolds -= golds;
        if(leftGolds <0){
            leftGolds = 0;
        }
    }
    
    /**
     * 增加玩家花费金币数，允许负数
     * @param golds
     */
    public void consumeGoldsNegative(int golds){
        this.golds +=golds; 
        this.currentState = golds;
        this.leftGolds -= golds;
    }
    
	
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public HandCards getHandCards() {
		return handCards;
	}
	public void setHandCards(HandCards handCards) {
		this.handCards = handCards;
	}
	
	public GamerState getState() {
		return state;
	}
	public void setState(GamerState state) {
		this.state = state;
	}
	public int getGolds() {
		return golds;
	}
	public void setGolds(int golds) {
		this.golds = golds;
	}
	public int getUseBanCard() {
		return useBanCard;
	}
	public void setUseBanCard(int useBanCard) {
		this.useBanCard = useBanCard;
	}
	public void coumUseBanCard(){
		if (useBanCard > 0) {
			useBanCard --;
		}
	}
	public byte getCompareCardTimes() {
		return compareCardTimes;
	}
	public void setCompareCardTimes(byte compareCardTimes) {
		this.compareCardTimes = compareCardTimes;
	}
	public byte getIsLookable() {
		return isLookable;
	}
	public void setIsLookable(byte isLookable) {
		this.isLookable = isLookable;
	}
	public int getLookCardGamerId() {
		return lookCardGamerId;
	}
	public void setLookCardGamerId(int lookCardGamerId) {
		this.lookCardGamerId = lookCardGamerId;
	}
	
	
	public boolean isRobot() {
		return isRobot;
	}
	public void setRobot(boolean isRobot) {
		this.isRobot = isRobot;
	}

	/**
	 * 玩家准备
	 */
	public synchronized void ready(){
	    state = GamerState.GAME_READY;
	}
	
	
	/**
	 * 玩家取消准备
	 */
	public void unReady(){
	    state = GamerState.GAME_NOREADY;
	}
	
	
	/**
	 * 玩家换牌
	 * @param gamer
	 * @param cardId
	 * @param exchangeCard 
	 */
	public void exchangeCard(Gamer gamer, byte cardId, Card exchangeCard) {
		//进行换牌
		HandCards handCards = gamer.getHandCards();
		if(handCards.getCard1().getCardId()!=cardId){
			if(handCards.getCard2().getCardId()!=cardId){
				if(handCards.getCard3().getCardId()==cardId){
					handCards.setCard3(exchangeCard);
				}
			}else{
				handCards.setCard2(exchangeCard);
			}
		}else{
			handCards.setCard1(exchangeCard);
		}
	}
	
	/**玩家弃牌 */
	public void abandonCards(GameRoom gameRoom,GamerState state) {
		//改变玩家游戏状态
		this.state = state;
	}
	
	/**玩家看牌*/
	public void lookCards(GameRoom gameRoom,GamerState state) {
		this.state = state;
	}
	
	
    public boolean isBaner() {
        return isBaner;
    }
    public void setBaner(boolean isBaner) {
        this.isBaner = isBaner;
    }
    public boolean isLookCard() {
        return isLookCard;
    }
    public void setLookCard(boolean isLookCard) {
        this.isLookCard = isLookCard;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public int getLeftGolds() {
        return leftGolds;
    }

    public void setLeftGolds(int leftGolds) {
        this.leftGolds = leftGolds;
    }

    public List<Gamer> getFireGamer() {
        return fireGamer;
    }

    public void setFireGamer(List<Gamer> fireGamer) {
        this.fireGamer = fireGamer;
    }
    
    public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}
	public int getUseNcard() {
		return useNcard;
	}

	public void setUseNcard(int useNcard) {
		this.useNcard = useNcard;
	}
	
	public Gamer clone(){
	    try {
            return (Gamer)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
	    return null;
	}

	/**
	 * 添加玩家剩余金币
	 * @param totalStake
	 */
    public void addLeftGolds(int totalStake) {
        this.leftGolds += totalStake;
    }

    public int getNoplayAmount() {
        return noplayAmount;
    }

    public void setNoplayAmount(int noplayAmount) {
        this.noplayAmount = noplayAmount;
    }

    public boolean isOperate() {
        return isOperate;
    }

    public void setOperate(boolean isOperate) {
        this.isOperate = isOperate;
    }

    public Long getLastAddGame() {
        return lastAddGame;
    }

    public void setLastAddGame(Long lastAddGame) {
        this.lastAddGame = lastAddGame;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}
