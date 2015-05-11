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
import com.orange.goldgame.business.entity.ErrorCode;
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
import com.orange.goldgame.server.activity.ConsumActivityService;
import com.orange.goldgame.server.domain.GameTable;
import com.orange.goldgame.server.domain.GameTableSeat;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.manager.GamerSet;
import com.orange.goldgame.server.manager.OnelinePrizeManager;
import com.orange.goldgame.server.manager.RobotManager;
import com.orange.goldgame.server.manager.SessionManger;
import com.orange.goldgame.server.pay.PayActivityService;
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
			if (PayActivityService.isOff50(pp.getId())) {
				om.putByte((byte)1);
				om.putInt(pp.getPropsMoney() / 2);
			}else {
				om.putByte((byte)0);
				om.putInt(pp.getPropsMoney());
			}
			ErrorCode res = ConsumActivityService.isConsum(pp);
			if(res.getStatus() == ErrorCode.SUCC){
				om.putInt(Integer.parseInt(res.getMsg()));
			}else{
				om.putInt(0);
			}
			
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

	/**
	 * 玩家请求个人道具信息
	 * @param response
	 * @param personalItemList
	 * @param gameStone
	 */
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

}
