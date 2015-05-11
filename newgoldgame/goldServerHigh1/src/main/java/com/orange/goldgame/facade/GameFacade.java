package com.orange.goldgame.facade;


/**
 * @author wuruihuang 2013.5.28
 * 此类是业务逻辑总入口
 */
public class GameFacade {
//	/**
//	 * 分配房间
//	 * @param gamer
//	 * @param areaId
//	 */
//	public static int dispacherRoom(int gamerId, byte areaId) {
//		GameRoom room = GameRoomManager.getInstance().getGameRoom();
//		GameRoomManager.getInstance().putGameRoom(gamerId, areaId, room);
//		AreaManager.getInstance().getGameRoomToArea().put(room.getRoomId(), areaId);
//		//获取tableId，将tableId作为roomId呈现给客户端
//		GameTable gametable = (GameTable) room.getObject(room.getRoomId());
//		int roomId = gametable.getTableId();
//		return roomId;
//	}
//	
//	/**
//	 * 玩家进入房间
//	 * @param gamer
//	 * @param gameRoom
//	 */
//	public static void enterGameRoom(Gamer gamer, GameRoom gameRoom) {
//		//增加玩家和游戏房间的关联信息
//		GameRoomManager.getInstance().registerToRoom(gameRoom, gamer);
//		gameRoom.addSession(SessionManger.getInstance().getSession(gamer.getPlayerId()));
//		GamerManager.getInstance().addGamer(gameRoom.getRoomId(), gamer);
//		GameTable gameTable = (GameTable) gameRoom.getObject(gameRoom.getRoomId());
//		gameTable.setCurrentNum(gameTable.getCurrentNum()+1);
//		
//		//
//		/*List<Integer> playerIds = GameRoomManager.getInstance().getOtherPlayerIds(gamer.getPlayerId(), gameRoom);
//		for(Integer playId:playerIds){
//			Gamer g = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(playId);
//			gamer.register(g);
//			g.register(gamer);
//		}*/
//	}
//	
//	/**
//	 * 玩家离开游戏
//	 * @param gamerId
//	 */
//	public static void leaveGaming(int gamerId) {
//		//清除玩家游戏痕迹
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		Gamer gamer = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(gamerId);
//		GamerManager.getInstance().clearGamer(gameRoom,gamer);
//		GameRoomManager.getInstance().clear(gamerId);
//	}
//	
//	/**
//	 * 离开游戏房间
//	 * @param playerId
//	 */
//	public static void leaveGameRoom(int gamerId) {
//		//清除玩家进入房间的痕迹
//		GameRoom room = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		GameRoomManager.getInstance().clear(gamerId);
//		Gamer g = GamerManager.getInstance().getGamerById(room.getRoomId(), gamerId);
//		GameRoomManager.getInstance().unregisterToRoom(room, g);
//	}
//	
//	/**
//	 * 玩家换桌 逻辑未完成
//	 * @param gamerId 
//	 * @param gameRoom 
//	 * @param playerId
//	 * @param id
//	 * @return 
//	 */
//	public static String exchangeGameTable(int gamerId) {
//		String roomId = null;
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		//玩家换桌前所处的场次
//		byte areaId = AreaManager.getInstance().getGameRoomToArea().get(gameRoom.getRoomId());
//	    //根据玩家的游戏币来匹配玩家适合在那个场次游戏
//		
//		//根据场次areaId来获取其他的房间
//		GameRoom room = GameRoomManager.getInstance().getGameRoom();
//		GameRoomManager.getInstance().putGameRoom(gamerId, areaId, room);
//		AreaManager.getInstance().getGameRoomToArea().put(room.getRoomId(), areaId);
//		return roomId;
//	}
}
