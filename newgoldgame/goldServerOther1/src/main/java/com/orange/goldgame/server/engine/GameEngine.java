package com.orange.goldgame.server.engine;



/**
 * @author wuruihuang 2013.6.14
 * 此类的作用是维护游戏角色gamer的数据,是通过维护GamerManager、
 * CardManager、GameRoomDataManager、GameRoomManager、SessionManger、AreaManager来达到目的的
 */
public class GameEngine{
    

	/**
	 * 游戏开始
	 * @param roomId
	 */
//	public static void noticeGameBegin(String roomId) {
//		GameRoom gameRoom = GameRoomManager.getInstance().getRoomByRoomId(roomId);
//		List<Object> cardList = new ArrayList<Object>();
//		//获取当前进行的游戏的庄家
//		int gamerZhuangjia = GameRoomDataManager.getInstance().getGameRoomBanker(gameRoom.getRoomId());
//		//获取当前下注的玩家
//		int gamerXiazhu = GamerManager.getInstance().getReturnGamer(gameRoom.getRoomId());
//		//获取扑克
//		Poker poker = CardManager.getInstance().getPokersMap().get(gameRoom.getRoomId());
//		List<Integer> gamerIds =  GamerManager.getInstance().getReadyGamer(roomId);
//		for(int i=0;i<gamerIds.size();i++){
//			CardVo card = new CardVo();
//			//给玩家发牌
//			Gamer g = GamerManager.getInstance().getGamerById(gameRoom.getRoomId(),gamerIds.get(i));
//			g.setHandCards(poker.getHandCards());
//			
//			card.setPlayerId(g.getPlayerId());
//			card.setCardId1(g.getHandCards().getCard1().getCardId());
//			card.setCardId2(g.getHandCards().getCard2().getCardId());
//			card.setCardId3(g.getHandCards().getCard3().getCardId());
//			cardList.add(card);
//		}
//		short cardSize = (short)cardList.size();
//		int length = 4*2 + 2 + cardSize *((IOutputMeasageVo) cardList.get(0)).getLength();
//		//只有游戏状态是准备状态的玩家才能游戏开始
//		gameRoom.sendMessage(Protocol.RESPONSE_GAME_START, OutPutMessageUtils.parseOutputMessage(length,
//				OutPutMessageUtils.parseInt(gamerZhuangjia),
//				OutPutMessageUtils.parseInt(gamerXiazhu),
//				OutPutMessageUtils.parseInt(cardSize),
//				OutPutMessageUtils.parseObjectList(cardList)));		
//	}
	
	/**
	 * 有玩家离开房间
	 * @param gamerId
	 */
	public static void hasGamerLeaveRoom(int gamerId) {
		
	}
	
	/**
	 * 广播每局游戏有玩家比牌的情况
	 * @param activeId
	 * @param passiveId
	 */
//	public static void hasGamerCompareCard(int activeId, int passiveId) {
//		
//		int length = 4+4;
//		GameRoom gameRoom  = GameRoomManager.getInstance().getRoomByPlayerId(activeId);
//		Gamer gamer = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(activeId);
//		//是否改变轮数  逻辑未完成，如何设置每局游戏的最后下注者 在什么地方确定每局游戏的下注者 此处逻辑复杂些
//		int lastGamerId = GameRoomDataManager.getInstance().getLastGamer(gameRoom.getRoomId());
//		if(lastGamerId==activeId){
//			GameRoomDataManager.getInstance().changeTimes(gameRoom.getRoomId());
//		}
//		
//		/*for(Gamer g: gamer.getGamerList()){
//			SessionManger.getInstance().getSession(g.getPlayerId()).sendMessage(Protocol.RESPONSE_PLAYER_BETED, 
//					OutPutMessageUtils.parseOutputMessage(length,
//							OutPutMessageUtils.parseInt(activeId),
//							OutPutMessageUtils.parseInt(passiveId)));
//		}*/
//	}
	
	/**
	 * 广播每局游戏结束
	 * @param roomId
	 * @param winGamerId
	 */
//	public static void broadcastGameResult(String roomId, int winGamerId) {
//		GameRoom gameRoom = GameRoomManager.getInstance().getRoomByRoomId(roomId);
//		//改变轮数
//		GameRoomDataManager.getInstance().changeTimes(roomId);
//		//改变庄家 ：上一轮赢的玩家是下一轮的庄家，第一局或者赢家离开的情况随机选一个玩家为庄家。
//		GameRoomDataManager.getInstance().changeGameRoomBanker(roomId, winGamerId);
//		//在游戏房间内广播本局游戏结果
//		Map<Integer,Gamer> gamerMap = GamerManager.getInstance().getGamersMap().get(roomId);
//		List<Integer> gamerIds = new ArrayList<Integer>();;
//		for(Entry<Integer,Gamer> entry : gamerMap.entrySet()){
//			gamerIds.add(entry.getKey());
//		}
//		List<Object> resultList = new ArrayList<Object>();
//		for(int i=0;i<gamerIds.size();i++){
//			ResultVo result = new ResultVo();
//			int gamerId = gamerIds.get(i);
//			result.setPlayerId(gamerId);
//			Player player = (Player) BaseEngine.getInstace().getPlayerActionIpml().getPlayerMsg(gamerId);
//			result.setNickname(player.getNickname());
//			result.setScore(1000);
//			//是否可以被别的玩家看牌
//			Gamer gamer = GamerManager.getInstance().getGamersMap().get(roomId).get(gamerId);
//			result.setIsLookableCards(gamer.getIsLookable());
//			HandCards handCards = GamerManager.getInstance().getGamersMap().get(roomId).get(gamerId).getHandCards();
//			result.setCardId1(handCards.getCard1().getCardId());
//			result.setCardId2(handCards.getCard2().getCardId());
//			result.setCardId3(handCards.getCard3().getCardId());
//			//可以看牌的玩家
//			result.setPlayerOtherId(gamer.getLookCardGamerId());
//			
//			resultList.add(result);
//		}
//		short resultSize = (short)resultList.size();
//		int length = resultSize*((IOutputMeasageVo) resultList.get(0)).getLength();
//		gameRoom.sendMessage(Protocol.RESPONSE_FIGHT_RESULT, OutPutMessageUtils.parseOutputMessage(length,
//				OutPutMessageUtils.parseShort(resultSize),
//				OutPutMessageUtils.parseObjectList(resultList)));
//		//通知下一局游戏开始
//		
//	}
}
