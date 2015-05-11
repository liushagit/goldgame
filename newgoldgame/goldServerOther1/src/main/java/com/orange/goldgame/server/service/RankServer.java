package com.orange.goldgame.server.service;


import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.Ranking;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.manager.AppellationManager;

public class RankServer {	
	private static final byte WEALTH_RANK=0;
	private static final byte EXCHANGE_RANK=1;
	private static final byte CHARM_RANK=2;
	private static final byte MATCH_RANK=3;
	private static final byte LOVER_RANK=4;
	private static final int RANGE=500000000;
	private Random random=new Random();
	private Logger log = LoggerFactory.getLogger(RankServer.class);
	
	public void requestRanking(SocketRequest request,SocketResponse response) throws JuiceException{
		InputMessage im=request.getInputMessage();
		int playerId=im.getInt();
		log.debug("玩家查看排行榜，玩家ID:"+playerId);
		byte bangType=im.getByte();
		OutputMessage om=new OutputMessage();
		om.putByte(bangType);
		
		List<Ranking> ranks=RankService.getRankByTypeId(bangType);
		short wealthSize=0;
		if(ranks!=null){
			wealthSize=(short)ranks.size();
		}
		om.putShort(wealthSize);
		if(ranks.size()>0){
			switch (bangType) {
			case WEALTH_RANK:	
				for(Ranking r:ranks){
//					Player p=PlayerService.getSimplePlayerByPlayerId(r.getPlayerId());
					om.putString(r.getPlayerName());
					om.putString(AppellationManager.getInstance().getAppellation(r.getRankValue()));
					om.putInt(r.getRankValue());
				}
				break;
			case EXCHANGE_RANK:	
			case CHARM_RANK:	
			case MATCH_RANK:	
			case LOVER_RANK:	
				for(Ranking r:ranks){
//					Player p=PlayerService.getSimplePlayerByPlayerId(r.getPlayerId());
					int playerID=r.getPlayerId();
					Player player=PlayerService.getSimplePlayerByPlayerId(playerID);
					String name="";
					if(player!=null){
						name=AppellationManager.getInstance().getAppellation(player.getCopper());
					}else{
						name=AppellationManager.getInstance().getAppellation(random.nextInt(RANGE)+1);
					}
					om.putString(r.getPlayerName());
					om.putString(name);
					om.putInt(r.getRankValue());
				}
				break;
			default:
				BaseServer.sendErrorMsg(response, (short)1, "the rank type error");
				return;
			}
			
		}
//		short[] protocols={Protocol.RESPONSE_WEALTH_LIST,Protocol.RESPONSE_EXCHANGE_LIST,Protocol.RESPONSE_CHARM_LIST,Protocol.RESPONSE_RECE_LIST};
		response.sendMessage(Protocol.RESPONSE_WEALTH_LIST, om);	
	}
	
}
