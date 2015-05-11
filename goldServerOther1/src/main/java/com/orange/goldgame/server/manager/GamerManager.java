package com.orange.goldgame.server.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.emun.GamerState;


/**
 * @author wuruihuang 2013.5.29
 * 作用是管理玩家角色gamer
 */
public class GamerManager {
	private static GamerManager playerManager;
	
	/**
	 * 第一个参数指的是房间的id,第二个参数玩家的id
	 */
	private Map<String, Map<Integer, Gamer>> gamersMap;
	
	private GamerManager(){
		this.gamersMap = new HashMap<String, Map<Integer,Gamer>>();
	}
	
	public static GamerManager getInstance(){
		if(playerManager==null){
			playerManager = new GamerManager();
		}
		return playerManager;
	}
	
	public Map<String, Map<Integer, Gamer>> getGamersMap() {
		return gamersMap;
	}

	public void addGamer(String roomId,Gamer gamer) {
		Map<Integer, Gamer> gamers = this.gamersMap.get(roomId);
		gamers.put(gamer.getPlayerId(), gamer);
	}
	
	public Gamer getGamerById(String roomId, int gamerId) {
		return gamersMap.get(roomId).get(gamerId);
	}
	
	/**
	 * 获取当前下注的玩家
	 * @param roomId
	 * @return
	 */
//	public int getReturnGamer(String roomId) {
//		int returnPlayerId = 0;
//		byte times = GameRoomDataManager.getInstance().getTimes(roomId);
//		GameRoom room = GameRoomManager.getInstance().getRoomByRoomId(roomId);
//		GameTable table = (GameTable) room.getObject(roomId);
//		GameTableSeat[] seats = table.getSeats();
//		//第一轮游戏，默认是顺时针的第一个游戏状态为准备状态的玩家为第一个下注者
//		/*if(times==0){
//			for(int i=0;i<seats.length;i++){
//				Gamer gamer = GamerManager.getInstance().getGamerById(roomId, seats[i].getPlayerId());
//				if(gamer.getState()==GamerState.GAME_READY){
//					returnPlayerId = seats[i].getPlayerId();
//					break;
//				}
//			}
//		}else{
//			//不是第一轮游戏，以本轮游戏的庄家为第一个下注者
//			for(int i=0;i<seats.length;i++){
//				if(seats[i].isOwner()){
//					returnPlayerId = seats[i+1].getPlayerId();
//					break;
//				}
//			}
//		}*/
//		
//		GameRoomDataManager.getInstance().changeLastGamer(roomId);
//		return returnPlayerId;
//	}
	
	/**
	 * 获取准备游戏的玩家
	 * @param roomId
	 * @return
	 */
	public List<Integer> getReadyGamer(String roomId) {
		List<Integer> gamerIds = new ArrayList<Integer>();
		Map<Integer, Gamer> gamerMap = this.gamersMap.get(roomId);
		for(Entry<Integer, Gamer> entry :gamerMap.entrySet()){
			Gamer gamer = entry.getValue();
			if(gamer.getState()==GamerState.GAME_READY){
				gamerIds.add(gamer.getPlayerId());
			}
		}
		return	gamerIds;
	}
	
	
	public void clear(String roomId) {
		this.gamersMap.remove(roomId);
	}
}
