package com.orange.goldgame.server.service;

import org.apache.log4j.Logger;

import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.domain.OnlieReward;
import com.orange.goldgame.server.manager.OnelinePrizeManager;

public class OnlineRewardService {
	
    private static Logger log = LoggerFactory.getLogger(OnlineRewardService.class);
	
	public void requestReward(SocketRequest request,SocketResponse response){

		InputMessage im=request.getInputMessage();
		int playerId=im.getInt();
		log.debug("玩家请求奖励，玩家ID："+playerId);
		Player player=PlayerService.getPlayer(playerId, request.getSession());
		
		int time=0; //or.getTime();
		int prize=0; //or.getPrize();
		
		OnlieReward or=OnelinePrizeManager.getInstance().getPlayerOnlinePrize(player);
		if(or == null){
		    BaseServer.sendErrorMsg(request.getSession(), (short)1, "今天您已经领取完毕！");		    
		}else{
			time=or.getTime();
			prize=or.getPrize();
			GoldService.addCopperAndUpdateGamer(player, prize);
		}				
		int endPrize=GoldService.getLeftCopper(player);
		OutputMessage om=new OutputMessage();
		om.putInt(time);
		om.putInt(prize);
		om.putInt(endPrize);
		response.sendMessage(Protocol.RESPONSE_ONLINE_PRIZE, om);
		
	}

}
