/**
 * CardGameServer
 * com.card.game.mt.domain
 * GameTable.java
 */
package com.orange.goldgame.server.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.AreaConfig;
import com.orange.goldgame.domain.BaseObject;
import com.orange.goldgame.domain.StakeConfig;
import com.orange.goldgame.server.emun.GameTableState;
import com.orange.goldgame.server.emun.GamerState;
import com.orange.goldgame.server.emun.TableSeatState;
import com.orange.goldgame.server.manager.GameTableManager;
import com.orange.goldgame.server.service.BaseServer;

/**
 * @author wuruihuang 2013.5.27
 */
public class GameTable extends BaseObject{
    
    public static final int MAX_TURN = 20;
    
//	private Lock lock = new ReentrantLock();// 锁
	private final transient ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	private final transient Lock r = rwLock.readLock(); // ????
	private final transient Lock w = rwLock.writeLock(); // ????
    
    
    public Lock getR() {
		return r;
	}

	public Lock getW() {
		return w;
	}

	/** 存放机器人比牌结果 主动，被动，结果**/
    private Gamer src;
	private Gamer dst;
    private boolean win;

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	
	public static final int DEFAULT_SEAT_SIZE = 5;
	/** GameTable ID */
	private int tableId;
	/** 当前房间人数 */
	private int currentNum;
	/**游戏桌的状态*/
	private GameTableState state;
	/**游戏椅子的数量**/
	private GameTableSeat[] seats;
	/**玩家最后操作时间*/
	private Long lastOperationTime;
	/**游戏的轮数*/
	private int turns;
	/**正在游戏的玩家,下标最小的先叫注，坐标为0的是庄家*/
	private LinkedBlockingQueue<Gamer> gamingList = new LinkedBlockingQueue<Gamer>();
	/** 参与过游戏的玩家列表 */
	private List<Gamer> joinedGameList = new Vector<Gamer>();
	/**当前需要操作的玩家序号*/
	private Gamer currentGamer;
	/**扑克牌*/
	private Poker poker ;
	/**总筹码*/
	private int totalStake = 0;
	/**赢家*/
	private Gamer winner ;
	/**当前单注*/
	private int singleStake = 0;
	/**初始单注*/
	private int initStake = 100;
	/**已发牌人数*/
	private int dealCardCount = 0;
	/** 注码类型 */
	private Map<Integer,StakeConfig> stakeTypeMap = new HashMap<Integer,StakeConfig>();
	/** 玩家初始金币(比赛场)*/
	private int gamerGolds ;
	/** 游戏场进入金币限制*/
	private int limitGolds ;
	/** 所在的游戏场ID*/
	private int areaId;
	/** 游戏局数*/
	private int inning = 0;
	/** 是否可以结束游戏*/
	private boolean isOverGamer =  true;
	/** 是否可以开始游戏*/
	private boolean isStartGame = true;
	/**庄家位置*/
	private Gamer banker = null;
	/**是否已经发牌*/
	private volatile boolean isDealCard = false;
	/**是否已比牌*/
	private boolean isCompareCard = false;
	/**旁观列表*/
	private LinkedBlockingQueue<Gamer> watchList = new LinkedBlockingQueue<Gamer>();
	
	private boolean sendMessage = false;
	
	public boolean isSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(boolean sendMessage) {
		this.sendMessage = sendMessage;
	}

	/**
	 * 创建游戏桌,底注默认为100;
	 */
	public GameTable(int id) {
	    initTable(id,5);
	}
	
	public GameTable(int id,int areaId){
	    //获得游戏场的信息
	    AreaConfig config = ResourceCache.getInstance().getAreaConfigs().get(areaId);
	    this.areaId = areaId;
	    int gold = config.getSingleGolds();
	    this.limitGolds = config.getLimitGolds();
	    //初始化桌子
	    initTable(id,gold);
	    
	    //获得注码类型，并初始化注码
	    for(StakeConfig sc : ResourceCache.getInstance().getStakeConfigs().values()){
	        if(sc.getAreaId() == areaId){
	            stakeTypeMap.put(sc.getId(), sc);
	        }
	    }
	    Integer gamerGoldss = ResourceCache.getInstance().getMatchStakeConfigs().get(areaId);
	    if(gamerGoldss != null){
	        this.gamerGolds = gamerGoldss;
	    }
	}
	
	/**
	 * 创建自定义底注房间
	 * @param id
	 * @param initStake
	 */
	private void initTable(int id,int initStake){
	    this.tableId = id;
        this.currentNum = 0;
        this.initStake = initStake;
        this.singleStake = initStake;
        this.state = GameTableState.TABLE_EMPTY;
        lastOperationTime = System.currentTimeMillis();
        seats = new GameTableSeat[DEFAULT_SEAT_SIZE];
        for (int i = 0; i < DEFAULT_SEAT_SIZE; i++) {
            /**
             *数组下标加1的和作为游戏椅子的id
             */
            seats[i] = new GameTableSeat(i);
        }
        
        //初始化扑克牌，并洗牌
        poker = new Poker();
        poker.initPoker();
        poker.shuffle();
	}
	
	/**
	 * 重置桌子状态
	 */
	public void reset(){
	    turns = 0;
	    gamingList.clear();
	    //joinedGameList.clear();
	    lastOperationTime = System.currentTimeMillis();
	    banker = null;
	    currentGamer = null;
	    totalStake = 0;
	    singleStake = 0;
	    dealCardCount = 0;
	    isOverGamer = true;
	    isStartGame = true;
	    isDealCard = false;
	    isCompareCard = false;
	    
        poker = new Poker();
        poker.initPoker();
        poker.shuffle();
	}
	
	
	//加入游戏者
	/**
	 * @param gamer
	 */
	public void addGamer(Gamer gamer){
	    if(gamer == null) return;
	    //加游戏者
	    for(GameTableSeat seat : seats){
	        if(seat.getState() == TableSeatState.SEAT_OPEN){
	            seat.setGamer(gamer);
	            seat.setState(TableSeatState.SEAT_CLOSE);
	            break;
	        }
	    }
	}

    //移除游戏者
    public void removeGamer(Gamer gamer){
        GameTableSeat seat = getGamerSeat(gamer);
        if(seat!=null){
            seat.setGamer(null);
            seat.setState(TableSeatState.SEAT_OPEN);
        }
    }
	
	//获取需要当前操作的玩家ID
	public int getCurrentPlayerId(){
	    Gamer gamer = getCurrentGamer();
	    if(gamer == null) return -1;
		return getCurrentGamer().getPlayerId();
		
	}
	
	
	//通过游戏者找到对应的座位，如果找不到，返回null
	public GameTableSeat getGamerSeat(Gamer gamer){
	    for(GameTableSeat seat : seats){
	        if(seat.getGamer() != null && seat.getGamer().getPlayerId() == gamer.getPlayerId()){
	            return seat;
	        }
	    }
	    return null;
	}
	
	//增加已发牌的人数
	public void increaseDealCardNum(){
	    this.dealCardCount++;
	}
	
	//获得已发牌的人数
	public int getDealCardCount(){
	    return dealCardCount;
	}
	
	public void resetDealCardNum(){
	    dealCardCount = 0;
	}
	
	
	//增加房间人数
    public void increaseCurrentNum(){
        if(this.currentNum < DEFAULT_SEAT_SIZE)
            this.currentNum ++;
    }
    
    //减少房间人数
    public void decreaseCunrrentNum(){
        if(this.currentNum > 0){
            this.currentNum --;
        }
    }
    //添加总注筹码
    public void addStake(int stake){
        this.totalStake += stake;
    }
    
    //获得当前玩家
    public synchronized Gamer getCurrentGamer(){
        if(gamingList == null || gamingList.size()<=0)
            return null;
        currentGamer = gamingList.peek();
        return currentGamer;
    }
    
    //移除正在玩的玩家
    public synchronized void removeGamingGamer(Gamer gamer){
        if(gamer == null || !gamingList.contains(gamer)){
            return;
        }
        gamingList.remove(gamer);
        if(banker == null || gamer.getPlayerId() == banker.getPlayerId()){
            banker = this.getCurrentGamer();
        }
    }
    
    //推进玩家顺序
    public void orderGamingList(int n){
        if(n>=gamingList.size() || n<0) return;
        for(int i=0;i<n;i++){
            Gamer gamer = gamingList.poll();
            gamingList.add(gamer);
        }
    }
    
    /**
     * 获取非机器人玩家
     * @return
     */
    public List<Gamer> getReallyGamerList(){
        List<Gamer> list = new ArrayList<Gamer>();
        synchronized (seats) {
            for(GameTableSeat seat : seats){
                Gamer gamer = seat.getGamer();
                if(gamer != null && !gamer.isRobot()){
                    list.add(gamer);
                }
            }
        }
        return list;
    }
    
    
    //切换下一个操作者
    public synchronized void nextOperator(){
        if(gamingList == null || gamingList.size()<=0) return;
        currentGamer = gamingList.poll();
        gamingList.add(currentGamer);
        if(this.getGamerSeat(banker).getId() == this.getGamerSeat(currentGamer).getId()){
            turns++;
            sendMessage = true;
            BaseServer.sendNextMessage(this);
        }
    }
    
    public synchronized Gamer getGamingGamerByIndex(int index){
        if(index<0 || index>=gamingList.size()){
            return null;
        }
        Gamer gamer = null;
        Gamer buffGamer = null;
        for(int i=0;i< gamingList.size();i++){
        	buffGamer = gamingList.poll();
        	if (index == i) {
        		gamer = buffGamer;
			}
            gamingList.add(buffGamer);
        }
//        
//        for(int i=0;i<gamingList.size()-index;i++){
//            buffGamer = gamingList.poll();
//            gamingList.add(buffGamer);
//        }
        
        return gamer;
    }

    
    //返回可比牌列表
    public List<Gamer> getFightCartList(){
        List<Gamer> list = new ArrayList<Gamer>();
        
        if(turns >= GameTableManager.maxTurn){
            for(Gamer gamer : gamingList){
                list.add(gamer);
            }
        }
        else{
            for(Gamer gamer : gamingList){
                if(gamer.getUseBanCard() <= 0){
                    list.add(gamer);
                }
            }
        }
        return list;
        
    }
    
    public int getSeatGamerNum(){
        int count = 0;
        synchronized (this) {
            for(GameTableSeat seat : seats){
                Gamer gamer = seat.getGamer();
                if(gamer != null){
                    count ++;
                }
            }
        }
        return count;
    }
    
    /**
     * 房间是否已满
     * @return
     */
    public boolean isFull(){
        int count = 0;
        synchronized (this) {
            for(GameTableSeat seat : seats){
                Gamer gamer = seat.getGamer();
                if(gamer != null){
                    count ++;
                }
            }
        }
        if(count>=DEFAULT_SEAT_SIZE){
            return true;
        }
        return false;
    }
    
	
	public int getTableId() {
		return tableId;
	}

	public void setTableId(int id) {
		this.tableId = id;
	}
	public GameTableSeat[] getSeats() {
		return seats;
	}

	public void setSeats(GameTableSeat[] seats) {
		this.seats = seats;
	}

	public int getCurrentNum() {
		return currentNum;
	}
	
	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}
	public GameTableState getState() {
		return state;
	}
	public void setState(GameTableState state) {
		this.state = state;
	}
	public int getGamingNum() {
		int num = 0;
		synchronized (seats) {
			for (GameTableSeat seat : seats) {
			    Gamer gamer = seat.getGamer();
				if (gamer != null && gamer.getState() == GamerState.GAME_READY) {
					num ++;
				}
			}
		}
		return num;
	}

    public LinkedBlockingQueue<Gamer> getGamingList() {
        return gamingList;
    }

    public int getTurns() {
        return turns;
    }


    public void setTurns(int turns) {
        this.turns = turns;
    }


    public Poker getPoker() {
        return poker;
    }


    public void setPoker(Poker poker) {
        this.poker = poker;
    }


    public Long getLastOperationTime() {
        return lastOperationTime;
    }


    public void setLastOperationTime(Long lastOperationTime) {
        this.lastOperationTime = lastOperationTime;
    }




    public int getTotalStake() {
        return totalStake;
    }


    public int getSingleStake() {
        return singleStake;
    }

    public synchronized void setSingleStake(int singleStake) {
        this.singleStake = singleStake;
    }

    public int getInitStake() {
        return initStake;
    }

    public void setInitStake(int initStake) {
        this.initStake = initStake;
    }

    public Map<Integer, StakeConfig> getStakeTypeMap() {
        return stakeTypeMap;
    }

    public void setStakeTypeMap(Map<Integer, StakeConfig> stakeTypeMap) {
        this.stakeTypeMap = stakeTypeMap;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public List<Gamer> getJoinedGameList() {
        return joinedGameList;
    }
    
    public boolean removeJoinedGamer(Gamer gamer){
        if(joinedGameList == null) return false;
        return joinedGameList.remove(gamer);
    }
    
    public void clearJoinedGamerList(){
        if(joinedGameList == null) ;
        joinedGameList.clear();
    }
    
    public int getJoinedGamerSize(){
        if(joinedGameList == null) return 0;
        return joinedGameList.size();
    }
    
    public void addJoinedGamer(Gamer gamer){
        joinedGameList.add(gamer);
    }
    
    public Gamer getSrc() {
		return src;
	}

	public void setSrc(Gamer src) {
		this.src = src;
	}

	public Gamer getDst() {
		return dst;
	}

	public void setDst(Gamer dst) {
		this.dst = dst;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

    public int getGamerGolds() {
        return gamerGolds;
    }

    public void setGamerGolds(int gamerGolds) {
        this.gamerGolds = gamerGolds;
    }

    public int getInning() {
        return inning;
    }

    public void setInning(int inning) {
        this.inning = inning;
    }

    public Gamer getWinner() {
        return winner;
    }

    public void setWinner(Gamer winner) {
        this.winner = winner;
    }

    public int getLimitGolds() {
        return limitGolds;
    }

    public void setLimitGolds(int limitGolds) {
        this.limitGolds = limitGolds;
    }

    public boolean isOverGamer() {
        return isOverGamer;
    }

    public void setOverGamer(boolean isOverGamer) {
        this.isOverGamer = isOverGamer;
    }

    public boolean isStartGame() {
        return isStartGame;
    }

    public void setStartGame(boolean isStartGame) {
        this.isStartGame = isStartGame;
    }


    public Gamer getBanker() {
        return banker;
    }

    public void setBanker(Gamer banker) {
        this.banker = banker;
    }

    public boolean isDealCard() {
        return isDealCard;
    }

    public void setDealCard(boolean isDealCard) {
        this.isDealCard = isDealCard;
    }

    public boolean isCompareCard() {
        return isCompareCard;
    }

    public void setCompareCard(boolean isCompareCard) {
        this.isCompareCard = isCompareCard;
    }

    public LinkedBlockingQueue<Gamer> getWatchList() {
        return watchList;
    }

    public void setWatchList(LinkedBlockingQueue<Gamer> watchList) {
        this.watchList = watchList;
    }

}
