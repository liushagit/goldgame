package com.orange.goldgame.server.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.orange.goldgame.server.domain.Gamer;



/**
 * @author wuruihuang 2013.5.28
 *管理游戏房间中的单注，总注，轮数，庄家
 */
public class GameRoomDataManager {
	private static GameRoomDataManager gameRoomStateManager;

	/**
	 * 第一个参数指的是房间的id,第二个参数指游戏的单注
	 */
	private Map<String, Integer> singleGoldsMap;
	/**
	 * 第一个参数指的是房间的id,第二个参数指游戏的总注
	 */
	private Map<String, Integer> totalGoldsMap;
	/**
	 * 第一个参数指的是房间的id,第二个参数指游戏的轮数
	 */
	private Map<String, Byte> timesMap;
	/**
	 * 第一个参数指的是房间的id,第二个参数指最后一个下注的人，用来判断一局游戏中是不是要改变轮数
	 */
	private Map<String, Integer> lastGamerMap;

	private GameRoomDataManager(){
		 this.singleGoldsMap = new HashMap<String, Integer>();
		 this.totalGoldsMap = new HashMap<String, Integer>();
		 this.timesMap = new HashMap<String, Byte>();
		 this.lastGamerMap = new HashMap<String, Integer>();
	}
	
	public static GameRoomDataManager getInstance(){
		if(gameRoomStateManager==null){
			gameRoomStateManager = new GameRoomDataManager();
		}
		return gameRoomStateManager;
	}
	
	
	public static GameRoomDataManager getGameRoomStateManager() {
		return gameRoomStateManager;
	}

	public Map<String, Integer> getSingleGoldsMap() {
		return singleGoldsMap;
	}

	public Map<String, Integer> getTotalGoldsMap() {
		return totalGoldsMap;
	}

	public Map<String, Byte> getTimesMap() {
		return timesMap;
	}
	
	public Map<String, Integer> getLastGamerMap() {
		return lastGamerMap;
	}
	/**
	 * 改变单注
	 * @param roomId
	 * @param singleGolds
	 */
	public void	changeSingleGolds(String roomId, int singleGolds) {
		for (Entry<String, Integer> entry : singleGoldsMap.entrySet()) {
			 if(entry.getKey()==roomId){
				entry.setValue(singleGolds);
				break;
			 }
		}
	}
	/**
	 * 改变总注
	 * @param roomId
	 * @param golds
	 */
	public void	changeTotalGolds(String roomId) {
		//计算总注
		int totalGolds = 0;
		Map<Integer, Gamer> gamersMap = GamerManager.getInstance().getGamersMap().get(roomId);
		for (Entry<Integer, Gamer> entry : gamersMap.entrySet()) {
			Gamer gamer = entry.getValue();
			totalGolds += gamer.getGolds();
		}
		//改变总注
		for(Entry<String, Integer> entryRoom : totalGoldsMap.entrySet()){
			if(entryRoom.getKey()==roomId){
				entryRoom.setValue(totalGolds);
			}
		}
	}
	
	/**
	 * 获取一轮游戏中最后下注的玩家
	 * @param roomId
	 * @return
	 */
	public int getLastGamer(String roomId) {
		return this.lastGamerMap.get(roomId);
	}
	
	/**
	 * 改变一轮游戏中最后下注的玩家
	 * @param roomId
	 * @param gameId
	 */
//	public void changeLastGamer(String roomId) {
//		int lastGamerId = GameRoomDataManager.getInstance().getLastGamer(roomId);
//		int changeLastGamerId = 0;
//		GameRoom gameRoom = GameRoomManager.getInstance().getRoomByRoomId(roomId);
//		GameTable table = (GameTable) gameRoom.getObject(gameRoom.getRoomId());
//		GameTableSeat[] seats = table.getSeats();
//		int index = 0;
//		//获取上一轮最后下注的玩家的位置
//		/*for(int i=0;i<seats.length;i++){
//			if(seats[i].getPlayerId()==lastGamerId){
//				index = i;
//				break;
//			}
//		}
//		//为本轮重新分配最后下注的玩家
//		for(int j=index-1;j>1&&j<seats.length;j--){
//			if(GamerManager.getInstance().isInGaming(seats[j].getPlayerId())){
//				changeLastGamerId = seats[j].getPlayerId();
//				break;
//			}
//		}*/
//		for(Entry<String, Integer> entryRoom : lastGamerMap.entrySet()){
//			if(entryRoom.getKey()==roomId){
//				entryRoom.setValue(changeLastGamerId);
//			}
//		}
//	}
	/**
	 * 改变游戏的下注轮数 逻辑未完成 
	 * @param roomId
	 * @param times
	 */
//	public void	changeTimes(String roomId) {
//		GameRoom room = GameRoomManager.getInstance().getRoomByRoomId(roomId);
//		for (Entry<String, Byte> entry : timesMap.entrySet()) {
//			if(entry.getKey()==roomId){
//				byte times = entry.getValue();
//				byte nextTimes = (byte)(times + 1);
//				entry.setValue(nextTimes);
//				//第三轮玩家可以进行比牌了 要增加协议 逻辑未完成
//				if(nextTimes==3){
////					room.sendMessage(Protocol., arg1)
//				}
//				break;
//			}
//		}
//	}
	
	/**
	 * 获取房间的庄家 逻辑为完成
	 * @param roomId
	 * @return
	 */
//	public int getGameRoomBanker(String roomId) {
//		int returnPlayerId = 0;
//		byte times = getTimes(roomId);
//		//每个房间的第一局游戏，随机产生庄家，以后每局游戏的庄家是上一局游戏的赢家，默认是顺时针的第一个游戏状态为准备状态的玩家为庄家
//		GameRoom room = GameRoomManager.getInstance().getRoomByRoomId(roomId);
//		GameTable table = (GameTable) room.getObject(roomId);
//		GameTableSeat[] seats = table.getSeats();
//		/*if(times==0){
//			returnPlayerId = seats[0].getPlayerId();
//			for(int i=0;i<seats.length;i++){
//				Gamer gamer = GamerManager.getInstance().getGamerById(roomId, seats[i].getPlayerId());
//				if(gamer.getState()==GamerState.GAME_READY){
//					returnPlayerId = seats[i].getPlayerId();
//					break;
//				}
//			}
//		}else{
//			for(int i=0;i<seats.length;i++){
//				if(seats[i].isOwner()){
//					returnPlayerId = seats[i+1].getPlayerId();
//					break;
//				}
//			}
//		}*/
//		return returnPlayerId;
//	}
	
	/**
	 * 改变游戏房间的庄家
	 * @param roomId
	 * @param playerId
	 */
//	public void changeGameRoomBanker(String roomId,int playerId) {
//		GameRoom room = GameRoomManager.getInstance().getRoomByRoomId(roomId);
//		GameTable table = (GameTable) room.getObject(roomId);
//		GameTableSeat[] seats = table.getSeats();
//		/*for(int i=0;i<seats.length;i++){
//			if(seats[i].getPlayerId()==playerId){
//				seats[i].setOwner(true);
//				break;
//			}
//		}*/
//	}
	
	/**
	 * 重置房间的数据 逻辑未完成
	 * @param roomId
	 */
	public void resetState(String roomId) {
		for (Entry<String, Integer> entry : this.singleGoldsMap.entrySet()) {
			if(entry.getKey()==roomId){
				//房间单注的重置要依据房间所在的场次，场次不同，房间的单注不同 逻辑未完成
				entry.setValue(100);
				break;
			}
		}
		for (Entry<String, Integer> entry : this.totalGoldsMap.entrySet()) {
			if(entry.getKey()==roomId){
				entry.setValue(0);
				break;
			}
		}
		for (Entry<String, Byte> entry : this.timesMap.entrySet()) {
			if(entry.getKey()==roomId){
				entry.setValue((byte)0);
				break;
			}
		}
	}
	
	/**
	 * 销毁房间的数据
	 * @param roomId
	 */
	public void clear(String roomId) {
		this.singleGoldsMap.remove(roomId);
		this.totalGoldsMap.remove(roomId);
		this.timesMap.remove(roomId);
	}
	
	public int getSingleGolds(String roomId) {
		return singleGoldsMap.get(roomId);
	}
	
	public int getTotalGolds(String roomId) {
		return totalGoldsMap.get(roomId);
	}
	
	public byte getTimes(String roomId) {
		return timesMap.get(roomId);
	}
}
