package com.orange.goldgame.server.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.container.Container;
import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.util.DateUtils;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.GameTableSeat;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.emun.TableSeatState;
import com.orange.goldgame.server.service.BaseServer;
import com.orange.goldgame.server.service.PlayerService;


/**
 * @author wuruihuang 2013.5.26
 * 管理房间的创建，回收，重置,管理房间的所在的场地  即是维护房间的生命周期，维护房间的使用状态
 */
public class GameRoomManager extends BaseServer{
	
	private static final  GameRoomManager instance =  new GameRoomManager();
	/**
	 * roomId room 存放所有房间信息
	 */
	private Map<String, GameRoom> gameRoomSet = new ConcurrentHashMap<String, GameRoom>();
	/**
	 * tableId table 存放所有桌子信息
	 */
	private AtomicInteger atomicInteger = new AtomicInteger();
	private GameRoomManager(){}
	public static GameRoomManager getInstance() {
		return instance;
	}
	
	/**
	 * 创建房间，默认将机器人放入
	 * @return
	 */
	private GameRoom createRoom(int area_id){

		//构造 房间
		String roomId = DateUtils.generateId();
		GameRoom gameRoom = Container.createGameRoom(roomId);
		int tableId = atomicInteger.getAndIncrement();
//		if (areaConfig == null) {
//			return null;
//		}
		
		//准备游戏桌
		GameTable gameTable = new GameTable(tableId,area_id);
		
		gameRoom.put(Constants.ROOM_TO_TABLE_KEY, gameTable);
		
		gameRoomSet.put(roomId, gameRoom);
		return gameRoom;
	}
	
	public void destoryRoom(GameRoom gameRoom){
		if (gameRoom != null ) {
			if (gameRoom.getRoomId() != null) {
				gameRoomSet.remove(gameRoom.getRoomId());
			}
			gameRoom.destory();
			gameRoom = null;
		}
	}
	
	
	public GameRoom enterRoom(Gamer gamer,int area_id){
	    return enterRoom(gamer,area_id,"");
	}
	
	//进入指定的房间
	public GameRoom enterRoom(Gamer gamer,String roomId,int type){
	    GameRoom groom = gameRoomSet.get(roomId);
	    if (groom != null) {
            Player player = PlayerService.getCachePlayerByGamer(gamer);
            GameTable table = (GameTable)groom.getObject(Constants.ROOM_TO_TABLE_KEY);
            int copper = player.getCopper();
            if (copper < table.getLimitGolds()) {
                GameSession session = SessionManger.getInstance().getSession(gamer.getPlayerId());
                sendErrorMsg(session, Constants.ENTER_ROOM_ERROR, "金币不足请充值");
                return null;
            }
            
            GameTableManager.cleanGamer(table);
            if(type == Constants.ENTER_SEAT || type == Constants.ENTER_WATCHTOSEAT){
                GameTableManager.addGamer(table, gamer);
            }else{
                GameTableManager.addWatchGamer(table, gamer);
            }
            if(type != Constants.ENTER_WATCHTOSEAT){
                gamer.setRoomId(groom.getRoomId());
                gameRoomSet.put(gamer.getRoomId(), groom);
            }
            
        }
        //2、返回房间
        return groom;
	}
	
	//进入房间，并且不能跟roomid的房间一样
	public GameRoom enterRoom(Gamer gamer,int area_id,String roomId){
		//1、找房间并且进入
		GameTable table = null;
		GameRoom groom = null;
		if (gamer.getRoomId() == null || "".equals(gamer.getRoomId())) {
			//先找现有的房间
			for (GameRoom room : gameRoomSet.values()) {
				table = (GameTable)room.getObject(Constants.ROOM_TO_TABLE_KEY);
				
				if (table != null && table.getAreaId() == area_id) {
					if (!table.isFull() && table.getInning()==0 && room.getRoomId() != null && !room.getRoomId().equals(roomId)) {
						groom = room;
						break;
					}
				}
			}
			//没有房间 重新创建
			if (groom == null) {
				groom = createRoom(area_id);
				table = (GameTable)groom.getObject(Constants.ROOM_TO_TABLE_KEY);
			}
		}else {
			//在房间内直接返回房间
			groom = gameRoomSet.get(gamer.getRoomId());
		}
		if (groom != null) {
		    Player player = PlayerService.getCachePlayerByGamer(gamer);
			int copper = player.getCopper();
			if (copper < table.getLimitGolds()) {
				GameSession session = SessionManger.getInstance().getSession(gamer.getPlayerId());
				sendErrorMsg(session, Constants.ENTER_ROOM_ERROR, "金币不足请充值");
				return null;
			}
			
			GameTableManager.cleanGamer(table);
			GameTableManager.addGamer(table, gamer);
			gamer.setRoomId(groom.getRoomId());
			gameRoomSet.put(gamer.getRoomId(), groom);
			
		}
		//2、返回房间
		return groom;
	}
	
	/**
	 * 更换房间
	 * @param playerId
	 * @param roomId
	 * @return
	 */
	public GameRoom exchangeRoom(Gamer gamer,String roomId,int area_id){
		//1、退出原房间
		exitRoom(gamer, roomId);
		gamer.setRoomId("");
		//2、重新进入房间
		return enterRoom(gamer, area_id);
	}
	
	/**
	 * 退出房间
	 * @param playerId
	 * @param roomId
	 * @return
	 */
	public boolean exitRoom(Gamer gamer,String roomId){
		//正在游戏不能退出
		GameRoom room = gameRoomSet.get(roomId);
		GameTable table = (GameTable)room.getObject(Constants.ROOM_TO_TABLE_KEY);
		if (table == null) {
			return false;
		}
		for(GameTableSeat seat:table.getSeats()) {
			if (seat.getGamer() != null && seat.getGamer().getPlayerId() == gamer.getPlayerId() && seat.getState() == TableSeatState.SEAT_BEGIN) {
				return false;
			}
		}
		GameTableManager.removeGamer(table, gamer);
		if (!gamer.isRobot()) {
			GameSession session = SessionManger.getInstance().getSession(gamer.getPlayerId());
			if (session != null) {
				room.removeSession(session.getSessionId());
			}
		}
		MemcachedResource.remove(Constants.PLAYER_LAST_TALBE + gamer.getPlayerId());
		gamer.setRoomId("");
		//房间退出需要加入机器人
		//玩家推出后如果没有其他玩家则将机器人归还
		boolean noPlayer = true;
		for (GameTableSeat seat : table.getSeats()) {
			if (seat.getGamer() != null && !seat.getGamer().isRobot()) {
				noPlayer = false;
				break;
			}
		}
		if (noPlayer) {
		    backOnePlayer(table);
		}
		return true;
	}
	
	public static void backOnePlayer(GameTable table){
	    Gamer g = null;
        for (GameTableSeat seat : table.getSeats()) {
            g = seat.getGamer();
            if (g != null && g.isRobot()) {
                GameTableManager.removeGamer(table, g);
                RobotManager.getInstance().backOnePlayer(g);
            }
        }
	}
	
	public GameRoom getRoomByPlayerId(int player_id){
		Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(player_id);
		if (gamer != null) {
			if (gamer.getRoomId() == null) {
				return null;
			}
			return gameRoomSet.get(gamer.getRoomId());
		}
		return null;
	}
	
	private GameRoom getRoomByRoomId(String roomId){
		return gameRoomSet.get(roomId);
	}
	
	public GameRoom getRoomByTable(GameTable table){
		GameRoom room = null;
		for (GameTableSeat seat : table.getSeats()) {
			if (seat.getGamer() != null) {
				room = getRoomByRoomId(seat.getGamer().getRoomId());
				if (room != null) {
					break;
				}
			}
		}
		
		return room;
	}
	
	public GameTable getTableByPlayerId(int player_id){
		try {
			GameRoom room = GameRoomManager.getInstance().getRoomByPlayerId(player_id);
			GameTable table = (GameTable) room.getObject(Constants.ROOM_TO_TABLE_KEY);
			return table;
		} catch (Exception e) {
		}
		return null;
	}
	
	public void scan(List<Integer> playerids){
		GameRoom room = null;
		GameTable table = null;
		GameSession session = null;
		for (Integer player_id : playerids) {
			room = getRoomByPlayerId(player_id);
			if (room != null) {
				table = (GameTable) room.getObject(Constants.ROOM_TO_TABLE_KEY);
				if (table != null) {
					GameTableManager.removeUnOnlineGamer(table);
					session = SessionManger.getInstance().getSession(player_id);
					if (session != null) {
						room.removeSession(session.getSessionId());
					}
				}
			}
		}
	}
    public Map<String, GameRoom> getGameRoomSet() {
        return gameRoomSet;
    }
}
