package com.orange.goldgame.server.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

import com.juice.orange.game.container.GameRoom;
import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.GiftVo;
import com.orange.goldgame.core.PropertyVo;
import com.orange.goldgame.domain.GoodsConfig;
import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.domain.StakeConfig;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.GameTableSeat;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.manager.GamerSet;
import com.orange.goldgame.server.manager.OnelinePrizeManager;
import com.orange.goldgame.server.manager.RobotManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.service.PlayerService;
import com.orange.goldgame.util.OutPutMessageUtils;
import com.orange.goldgame.vo.OrderVo;
import com.orange.goldgame.vo.PlayerSelfVo;

/**
 * @author wuruihuang 2013.6.17
 * 此类的作用是服务器做反馈处理信息到客户端
 */
public class ResponseEngine{
    
    private static final Logger log = LoggerFactory.getLogger(ResponseEngine.class);
	/**
	 * 响应玩家请求进入场次
	 * @param gamerId
	 * @param roomId
	 */
    public static void gamerEnterAreaResponse(GameSession session ,GameRoom room,Player enterPlayer) {
        gamerEnterAreaResponse(session,room,enterPlayer,Constants.ENTER_SEAT,0);
    }
    
    
	public static void gamerEnterAreaResponse(GameSession session ,GameRoom room,Player enterPlayer,int type,int friendId) {
		GameTable table = (GameTable) room.getObject(Constants.ROOM_TO_TABLE_KEY);
		OutputMessage om = new OutputMessage();
		om.putInt(enterPlayer.getGolds());
        om.putInt(OnelinePrizeManager.getInstance().getTime(enterPlayer));
		om.putInt(table.getSingleStake());
		om.putInt(table.getTotalStake());
		om.putByte((byte)table.getTurns());
		om.putInt(table.getCurrentPlayerId());
		om.putByte(table.getGamingList().size()>0?(byte)1:(byte)0);
		Lock lock = table.getR();
		try{
		    lock.lock();
		    int currentNum = table.getSeatGamerNum();
	        om.putShort((short)currentNum);
	        Gamer gamer = null;
	        Player player = null;
	        for (GameTableSeat seat : table.getSeats()) {
	            if (seat.getGamer() != null ) {
	                gamer = seat.getGamer();
	                if (gamer.isRobot()) {
	                    player = RobotManager.getInstance().getRobotPlayer(gamer.getPlayerId());
	                }else {
	                    player = PlayerService.getSimplePlayerByPlayerId(gamer.getPlayerId());
	                }
	                if (player != null) {
	                    getPlayerInfo(table,gamer, player, om);
	                }
	            }
	        }
		} catch(Exception e){
		    log.error("Lock error", e);
		} finally{
		    lock.unlock();
		}
		
		Map<Integer, StakeConfig> map = table.getStakeTypeMap();
		om.putShort((short)map.size());
		List<StakeConfig> valueList = new ArrayList<StakeConfig>();
		valueList.addAll(map.values());
		Collections.sort(valueList, new Comparator<StakeConfig>() {
            @Override
            public int compare(StakeConfig o1, StakeConfig o2) {
                return o1.getStakeNumber() - o2.getStakeNumber();
            }
        });
		for(StakeConfig sc : valueList){
            om.putByte((byte)sc.getId().intValue());
            om.putInt(sc.getStakeNumber());
		}
		
		if(type == Constants.ENTER_WATCH){
		    om.putInt(friendId);
		    session.sendMessage(Protocol.RESPONSE_WATCH_INFO, om);
		}else{
		    session.sendMessage(Protocol.RESPONSE_NOTICE_INTO_ROOM, om);
		    sendHasEnterRoom(enterPlayer,room,table);
		}
		
	}
	
	//广播有其他玩家进入房间
	public static void sendHasEnterRoom(Player enterPlayer,GameRoom room,GameTable table){
	    OutputMessage othreMsg = new OutputMessage();
        Gamer gamer = GamerSet.getInstance().getGamerByPlayerId(enterPlayer.getId());
        if (gamer == null) {
            GamerSet.getInstance().removeGamerByPlayer(enterPlayer);
            return;
        }
        getPlayerInfo(table,gamer, enterPlayer, othreMsg);
        GameSession session = SessionManger.getInstance().getSession(enterPlayer);
        if(session !=null)
            room.sendMessage(Protocol.RESPONSE_OTHER_ENTER_ROOM, othreMsg,session);
        else{
            room.sendMessage(Protocol.RESPONSE_OTHER_ENTER_ROOM, othreMsg);
        }
	}
	
	private static void getPlayerInfo(GameTable table , Gamer gamer,Player player ,OutputMessage om){
		om.putInt(player.getId());
		om.putByte(getGamerState(gamer));
		om.putByte(getGamerState(gamer));
		om.putInt(gamer.getCurrentState());
		om.putString(player.getNickname());
		om.putString(player.getAppellation());
		om.putString(player.getHeadImage());
		om.putByte(Byte.parseByte(player.getSex()+""));
		om.putInt(gamer.getLeftGolds());
		om.putInt(player.getGolds());
		log.debug("玩家座位ID:"+table.getGamerSeat(gamer));
		om.putByte(table.getGamerSeat(gamer)==null ? 0 : (byte)table.getGamerSeat(gamer).getId());
	}
	private static byte getGamerState(Gamer gamer){
		byte status = 0;
		switch (gamer.getState()) {
		case GAME_NOREADY:
			status = 0;
			break;
		case GAME_READY:
			status = 1;
			break;
		case GAME_PLAYING:
			status = 2;
			break;
		case GAME_GIVEUP:
			status = 3;
			break;
		case GAME_LOOK:
			status = 4;
			break;
		case GAME_LOSE:
			status = 5;
			break;
		case GAME_WIN:
			status = 6;
			break;

		default:
			break;
		}
		return status;
	}
//	
//	/**
//	 * 响应玩家请求比牌结果
//	 * @param activeId
//	 * @param passiveId
//	 * @param winGamerId
//	 */
//	public static void gamerCompareCardResponse(int activeId, int passiveId, int winGamerId) {
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(activeId);
//		
//		int length = 4 + 4;
//		int loseId = 0;
//		if(activeId==winGamerId){
//			loseId = passiveId;
//		}else{
//			loseId = activeId;
//		}
//		
//		//在游戏房间内广播比牌结果
//		gameRoom.sendMessage(Protocol.RESPONSE_PLAYER_FIGHT_RESULT, 
//				OutPutMessageUtils.parseOutputMessage(length,
//						OutPutMessageUtils.parseInt(winGamerId),
//						OutPutMessageUtils.parseInt(loseId)));
//	}
//	
//	/**
//	 * 响应玩家离开房间的请求
//	 * @param gamerId
//	 */
//	public static void gamerLeaveResponse(int gamerId) {
//		GameRoom gameRoom = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		GameTable gameTable = (GameTable) gameRoom.getObject(gameRoom.getRoomId());
//		GameTableSeat[] seats = gameTable.getSeats();
//		for(GameTableSeat seat:seats){
//			/*int playId = seat.getPlayerId();
//			if(playId!=gamerId){
//				GameSession session = SessionManger.getInstance().getSession(playId);
//				session.sendMessage(Protocol.RESPONSE_HAS_PLAYER_LEAVE_ROOM, OutPutMessageUtils.parseInt(gamerId));
//			}*/
//		}
//	}
//
//	public static void gamerSendGiftResponse(int activeId, int passiveId, byte giftId) {
//		int length = 4 + 4 + 1;
//		GameRoom room = GameRoomManager.getInstance().getGameRoomByPlayerId(activeId);
//		List<Integer> playerIds = GameRoomManager.getInstance().getOtherPlayerIds(activeId, room);
//		for(int id: playerIds){
//			SessionManger.getInstance().getSession(id).sendMessage(Protocol.RESPONSE_FOBIDDEN, OutPutMessageUtils.parseOutputMessage(length,
//					OutPutMessageUtils.parseInt(activeId),
//					OutPutMessageUtils.parseInt(passiveId),
//					OutPutMessageUtils.parseByte(giftId)));
//		}
//	}
//
//	public static void gamerRecevieAwordResponse(int gamerId, int golds) {
//		short when = (short)2;
//		int length = 4 + 4 + 2;
//		SessionManger.getInstance().getSession(gamerId).sendMessage(Protocol.RESPONSE_FOBIDDEN, OutPutMessageUtils.parseOutputMessage(length,
//				OutPutMessageUtils.parseInt(gamerId),
//				OutPutMessageUtils.parseInt(golds),
//				OutPutMessageUtils.parseShort(when)));
//	}
//
//	public static void gamerExchangeTableResponse(SocketRequest request,int gamerId, int roomId) {
//		gamerEnterAreaResponse(request,gamerId, roomId);
//	}
//
//	public static void gamerUseForbidenCardResponse(int gamerId) {
//		//服务器反馈玩家禁比信息
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		Gamer gamer = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(gamerId);
//		/*List<Gamer> gamers = gamer.getGamerList();
//		for(Gamer g: gamers){
//			SessionManger.getInstance().getSession(g.getPlayerId()).sendMessage(Protocol.RESPONSE_FOBIDDEN,
//					OutPutMessageUtils.parseInt(gamerId));
//		}*/
//	}
//
//	public static void gamerUseDoubleCardResponse(int gamerId, byte doubleId) {
//		//服务器反馈玩家使用翻倍卡信息
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		Gamer gamer = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(gamerId);
//		/*List<Gamer> gamers = gamer.getGamerList();
//		int length = 4 + 4;
//		for(Gamer g: gamers){
//			SessionManger.getInstance().getSession(g.getPlayerId()).sendMessage(Protocol.RESPONSE_FIGHT_DOUBLE_GOLDEN, 
//					OutPutMessageUtils.parseOutputMessage(length, 
//							OutPutMessageUtils.parseInt(gamerId),
//							OutPutMessageUtils.parseByte(doubleId)));
//		}*/
//	}
//
//	public static void gamerUseExchaneCardResponse(int gamerId, byte exchangePokerId) {
//		GameSession session = SessionManger.getInstance().getSession(gamerId);
//		session.sendMessage(Protocol.RESPONSE_EXCHANGE_CARD, OutPutMessageUtils.parseInt(exchangePokerId));
//	}
//
//	public static void gamerAbandonPokerResponse(int gamerId,
//			GamerState gamerState) {
//		//服务器反馈玩家放弃信息
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		Gamer gamer = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(gamerId);
//		/*for(Gamer g: gamer.getGamerList()){
//			SessionManger.getInstance().getSession(g.getPlayerId()).sendMessage(Protocol.RESPONSE_ABANDON,
//					OutPutMessageUtils.parseInt(gamerId));
//		}*/
//	}
//
//	public static void gamerAddGoldsResponse(int gamerId,int golds) {
//
//		//服务器反馈玩家加注信息
//		int length = 4 + 4;
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		Gamer gamer = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(gamerId);
//		/*for(Gamer g: gamer.getGamerList()){
//			SessionManger.getInstance().getSession(g.getPlayerId()).sendMessage(Protocol.RESPONSE_ADD_GOLDEN, 
//					OutPutMessageUtils.parseOutputMessage(length,
//							OutPutMessageUtils.parseInt(gamerId),
//							OutPutMessageUtils.parseInt(golds)));
//		}	*/	
//	}
//
//	public static void sendRoomSingleGolds(String roomId) {
//		//服务器返回单注更改信息
//		GameRoom gameRoom = GameRoomManager.getInstance().getRoomMap().get(roomId);
//		int singleGoldsNumber  = GameRoomDataManager.getInstance().getSingleGolds(roomId);
//		gameRoom.sendMessage(Protocol.RESPONSE_CHANGE_SINGLEGOLDS, 
//				OutPutMessageUtils.parseInt(singleGoldsNumber));		
//	}
//
//	public static void sendRoomGoldsPool(String roomId) {
//		//服务器返回奖池更改信息
//		GameRoom gameRoom = GameRoomManager.getInstance().getRoomMap().get(roomId);
//		int goldsPoolNumber = GameRoomDataManager.getInstance().getTotalGolds(roomId);
//		gameRoom.sendMessage(Protocol.RESPONSE_CHANGE_SINGLEGOLDS,
//				OutPutMessageUtils.parseInt(goldsPoolNumber));		
//	}
//
//	public static void gamerFollowCardResponse(int gamerId) {
//		//服务器反馈玩家跟注信息
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		Gamer gamer = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(gamerId);
//		/*for(Gamer g: gamer.getGamerList()){
//			SessionManger.getInstance().getSession(g.getPlayerId()).sendMessage(Protocol.RESPONSE_FOLLOW_GOLDEN,
//					OutPutMessageUtils.parseInt(gamerId));
//		}	*/	
//	}
//
//	public static void gamerLookCardResponse(int gamerId) {
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		Gamer gamer = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(gamerId);
//		/*for(Gamer g: gamer.getGamerList()){
//			SessionManger.getInstance().getSession(g.getPlayerId()).sendMessage(Protocol.RESPONSE_NONEED_GOLDEN,
//					OutPutMessageUtils.parseInt(gamerId));
//		}*/
//	}
//
//	public static void gamerGameReadyResponse(int gamerId) {
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		Gamer gamer = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(gamerId);
//		/*for(Gamer g: gamer.getGamerList()){
//			SessionManger.getInstance().getSession(g.getPlayerId()).sendMessage(Protocol.RESPONSE_PLAYER_READY,
//					OutPutMessageUtils.parseInt(gamerId));
//		}*/		
//	}
//
//	public static void gamerEnterRoomResponse(int gamerId, int tableId) {
//		//向客户端回馈信息
//		GameRoom gameRoom = GameRoomManager.getInstance().getRoomByTableId(tableId);
//		int singleGolds = GameRoomDataManager.getInstance().getSingleGolds(gameRoom.getRoomId());
//		int totalGolds = GameRoomDataManager.getInstance().getTotalGolds(gameRoom.getRoomId());
//		byte times = GameRoomDataManager.getInstance().getTimes(gameRoom.getRoomId());
//		int returnPlayerId = GamerManager.getInstance().getReturnGamer(gameRoom.getRoomId());
//				
//		List<Object> playerList = new ArrayList<Object>();
//		List<Integer> playerIds = GameRoomManager.getInstance().getOtherPlayerIds(gamerId, gameRoom);
//		
//		for(int i=0;i<playerIds.size();i++){
//			Estate estate = estateActionIpml.getEstate(playerIds.get(i));
//			List<Player> players = playerActionIpml.getPlayerMsg(playerIds.get(i));
//			Player player = players.get(0);
//			PlayerIntoRoomVo playerIntoRoomVo = new PlayerIntoRoomVo();
//			playerIntoRoomVo.setPlayer(player);
//			
//			//下面的两个状态合并为一个状态？？？？
//			Gamer g = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(playerIds.get(i));
//			playerIntoRoomVo.setPlayerCardState(GamerStateConvertor.converterToByte(g.getState()));
//			playerIntoRoomVo.setPlayerCardResultState(GamerStateConvertor.converterToByte(g.getState()));
//			int golds = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(playerIds.get(i)).getGolds();
//			playerIntoRoomVo.setJeton(golds);
//			playerIntoRoomVo.setGolds(estate.getGolds());
//			playerIntoRoomVo.setGemstone(estate.getGemstone());
//			HandCards hangCard = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(playerIds.get(i)).getHandCards();
//			playerIntoRoomVo.setCardId1(hangCard.getCard1().getCardId());
//			playerIntoRoomVo.setCardId2(hangCard.getCard2().getCardId());
//			playerIntoRoomVo.setCardId3(hangCard.getCard3().getCardId());
//			playerList.add(playerIntoRoomVo);
//		}
//		
//		short playerSize = (short) playerList.size();
//		int length = 4 * 3 + 1 + 2 + OutPutMessageUtils.getObjectListLenth(playerList);
//		
//		//服务端通知玩家进入房间		
//		GameSession session = SessionManger.getInstance().getSession(gamerId);
//		session.sendMessage(Protocol.RESPONSE_NOTICE_INTO_ROOM, OutPutMessageUtils.parseOutputMessage(length,
//				OutPutMessageUtils.parseInt(singleGolds),
//				OutPutMessageUtils.parseInt(totalGolds),
//				OutPutMessageUtils.parseByte(times),
//				OutPutMessageUtils.parseInt(returnPlayerId),
//				OutPutMessageUtils.parseShort(playerSize),
//				OutPutMessageUtils.parseObjectList(playerList)));
//	}
//
//	public static void hasGamerIntoRoom(int gamerId) {
//		Estate estate = estateActionIpml.getEstate(gamerId);
//		List<Player> players = playerActionIpml.getPlayerMsg(gamerId);
//		Player player = players.get(0);
//		GameRoom gameRoom = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		byte sex = (byte)0;
//		if(player.getSex()=="男"){
//			sex = (byte)0;
//		}else if(player.getSex()=="女"){
//			sex = (byte)1;
//		}
//		String nickname = player.getNickname();
//		int golds = estate.getGolds();
//	    int length = 4 + 1 + 2 + nickname.getBytes().length;
//	    List<Integer> playerIds = GameRoomManager.getInstance().getOtherPlayerIds(gamerId, gameRoom);
//	    for(int playId:playerIds){
//			SessionManger.getInstance().getSession(playId).sendMessage(Protocol.RESPONSE_HAS_PLAYER_INTO_ROOM, 
//					OutPutMessageUtils.parseOutputMessage(length,
//							OutPutMessageUtils.parseByte(sex),
//							OutPutMessageUtils.parseString(nickname),
//							OutPutMessageUtils.parseInt(golds)));
//		}
//	}
//
//	/**
//	 * 响应玩家聊天请求
//	 * @param playerId
//	 * @param msg
//	 */
//	public static void chatWithResponse(int gamerId, String msg) {
//		int length = 4 + 2 + msg.getBytes().length;
//		GameRoom gameRoom  = GameRoomManager.getInstance().getGameRoomByPlayerId(gamerId);
//		Gamer gamer = GamerManager.getInstance().getGamersMap().get(gameRoom.getRoomId()).get(gamerId);
//		/*for(Gamer g: gamer.getGamerList()){
//			GameSession session = SessionManger.getInstance().getSession(g.getPlayerId());
//			session.sendMessage(Protocol.RESPONSE_PLAYER_CHATMESSAGE, 
//					OutPutMessageUtils.parseOutputMessage(length,
//							OutPutMessageUtils.parseInt(gamerId),
//							OutPutMessageUtils.parseString(msg)));
//		}*/	
//	}
//
	public static void propListResponse(SocketResponse response, List<PackageProps> propsList, int gameStone) {

		OutputMessage om = new OutputMessage();
		om.putInt(gameStone);
		om.putShort((short)propsList.size());
		for(PackageProps pp:propsList){
			int propsId = pp.getPropsId();
			PropsConfig propsConfig = BaseEngine.getInstace().getPropActionIpml().queryPropsById(propsId);
			om.putInt(propsId);
			om.putString(propsConfig.getName());
			om.putInt(propsConfig.getPropsType());
			om.putInt(pp.getPropsAward());
			om.putInt(pp.getPropsMoney());
			om.putInt(pp.getPropsNumber());
		}
		response.sendMessage(Protocol.RESPONSE_PROP_LIST, om);
	}

	public static void goodsListResponse(SocketResponse response,
			List<GoodsConfig> goodsList,int exchangeNum) {
		short goodsSize  = 0;
		if(goodsList!=null&&goodsList.size()>0){
			goodsSize = (short)goodsList.size();
		}
		OutputMessage om = new OutputMessage();
		om.putInt(exchangeNum);
		om.putShort(goodsSize);
		if(goodsList!=null&&goodsList.size()>0){
			for(GoodsConfig gc : goodsList){
				om.putInt(gc.getId());
				om.putString(gc.getGoodsName());
				om.putInt(gc.getExchangVoucher());
				om.putString(gc.getGoodsIntro());
				om.putString(gc.getGoldUrl());
			}
		}
		response.sendMessage(Protocol.RESPONSE_EXCHANGE_GOODS_LIST,om);
	}


	/**响应玩家购买道具的请求*/
	public static void buyPropResponse(SocketRequest request, OrderVo orderInfo) {
		int length = orderInfo.getLength();
		request.getSession().sendMessage(Protocol.RESPONSE_PROP_ORDER, 
				OutPutMessageUtils.parseOutputMessage(length,
						OutPutMessageUtils.parseVo(orderInfo)));		
	}

    public static void personalItemListResponse(SocketResponse response,
            List<PlayerProps> personalItemList,int gameStone) {
    	short goodsSize = 0;
    	goodsSize = (short)personalItemList.size();
        OutputMessage msg = new OutputMessage();
        msg.putInt(gameStone);
        msg.putShort(goodsSize);
        if (personalItemList != null&&personalItemList.size()>0) {
        	for(PlayerProps playerProps :personalItemList){
        		PropsConfig propsConfig = BaseEngine.getInstace().getPropActionIpml().queryPropsById(playerProps.getPropsConfigId());
        		msg.putInt(propsConfig.getId());
        		msg.putInt(propsConfig.getPropsType());
        		msg.putString(propsConfig.getName());
        		msg.putInt(playerProps.getNumber());
        		//TODO test
        	}
		}
        response.sendMessage(Protocol.RESPONSE_PLAYER_GOODS_LIST,msg);
        
    }

    public static void playerInfoResponse(SocketResponse response,
            PlayerSelfVo playerInfo, PropertyVo wealthInfo, GiftVo giftInfo) {
        
        
        response.sendMessage(Protocol.RESPONSE_MYSELF_INFO, 
                OutPutMessageUtils.parseOutputMessage(
                        OutPutMessageUtils.parseVo(playerInfo),
                        OutPutMessageUtils.parseVo(wealthInfo),
                        OutPutMessageUtils.parseVo(giftInfo)));
    }
//
//    public static void otherPlayerResponse(SocketRequest request,
//            OtherPlayerVo playerInfo, GiftVo giftInfo) {
//        int length = 2 + playerInfo.getLength() + giftInfo.getLength() ;
//        GameSession session = request.getSession();
//        session.sendMessage(Protocol.RESPONSE_OTHER_INFO, 
//                OutPutMessageUtils.parseOutputMessage(length,
//                        OutPutMessageUtils.parseVo(playerInfo),
//                        OutPutMessageUtils.parseVo(giftInfo)));
//    }
//
//    public static void activityListResponse(SocketRequest request,
//            List<Object> activityList) {
//        short goodsSize = (short)activityList.size();
//        
//        int length = 2 + OutPutMessageUtils.getObjectListLenth(activityList);
//        GameSession session = request.getSession();
//        session.sendMessage(Protocol.RESPONSE_PLAYER_GOODS_LIST, 
//                OutPutMessageUtils.parseOutputMessage(length,
//                        OutPutMessageUtils.parseShort(goodsSize),
//                        OutPutMessageUtils.parseObjectList(activityList)));
//    }
//    
//    
//
//	public static void messageListResponse(SocketRequest request,
//			List<Object> messageList) {
//		short msgSize = (short)messageList.size();
//        int length = 2;
//        if(messageList.get(0) instanceof String){
//        	List<String> mess = new ArrayList<String>();
//        	for(Object obj :messageList){
//        		mess.add((String) obj);
//        	}
//        	for(String obj :mess){
//        		length += obj.getBytes().length;
//        	}
//        	request.getSession().sendMessage(Protocol.RESPONSE_MESSAGEINTO_LIST, 
//        			OutPutMessageUtils.parseOutputMessage(length,
//        					OutPutMessageUtils.parseShort(msgSize),
//        					OutPutMessageUtils.parseStringList(mess)));
//        }else{
//        	 for(Object obj :messageList){
//              	length += ((IOutputMeasageVo) obj).getLength();
//        	 }
//        	 request.getSession().sendMessage(Protocol.RESPONSE_MESSAGEINTO_LIST, 
//        			 OutPutMessageUtils.parseOutputMessage(length,
//        					 OutPutMessageUtils.parseShort(msgSize),
//        					 OutPutMessageUtils.parseObjectList(messageList)));	
//        }
//	}
//
//	/**
//	 * 响应客户端是否可进行兑换商品实物
//	 * @param request
//	 * @param isExchangeable
//	 */
//	public static void goodsExchangeResponse(SocketRequest request,
//			byte isExchangeable) {
//		 request.getSession().sendMessage(Protocol.RESPONSE_IS_EXCHANGE_ABLE, 
//				 OutPutMessageUtils.parseByte(isExchangeable));	
//	}
//	
//	/**
//	 * 响应客户端成功兑换商品实物
//	 * @param request
//	 * @param exchangeVoucher
//	 * @param exchangeVoucher2 
//	 */
//	public static void comfirmExchangeGoodsResponse(SocketRequest request,byte isOk,
//			int exchangeVoucher) {
//		int length = 1 + 4;
//		request.getSession().sendMessage(Protocol.RESPONSE__EXCHANGE_GOODS_SUCCESS, 
//				 OutPutMessageUtils.parseOutputMessage(length, 
//						 OutPutMessageUtils.parseByte(isOk),
//						 OutPutMessageUtils.parseInt(exchangeVoucher)));	
//	}

}
