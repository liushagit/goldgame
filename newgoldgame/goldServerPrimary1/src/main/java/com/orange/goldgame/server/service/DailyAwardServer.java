package com.orange.goldgame.server.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.manager.DailyPrizeManager;

public class DailyAwardServer {

    private Logger logger = LoggerFactory.getLogger(DailyAwardServer.class);
    
    /**
     * 返回每日奖励列表
     */
    public void responsePrizeList (SocketRequest request, SocketResponse response)
            throws GoldException {
        
        logger.debug("每日奖励列表!");
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        Player player = PlayerService.getPlayer(playerId, request.getSession());
        int day = DailyPrizeManager.getInstance().getPlayerCurrentDaily(player);
        List<Integer> prizeList = DailyPrizeManager.getInstance().getDailyPrizeList();
        boolean isget = DailyPrizeManager.getInstance().isRecive(player);
        
        OutputMessage om = new OutputMessage();
        om.putByte(isget?(byte)1:(byte)0);
        om.putInt(day);
        om.putShort((short)prizeList.size());
        for(int prize : prizeList){
            om.putInt(prize);
        }
        
        request.getSession().sendMessage(Protocol.RESPONSE_DAILYAWARTLIST, om);
    }
    
    /**
     * 玩家领取奖励
     */
    public void receivePrize(SocketRequest request, SocketResponse response)
            throws GoldException {
        logger.debug("玩家领取奖励!");
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        Player player = PlayerService.getPlayer(playerId, request.getSession());
        DailyPrizeManager.getInstance().receivePrize(player);
        
        List<Integer> prizeList = DailyPrizeManager.getInstance().getDailyPrizeList();
        int day = DailyPrizeManager.getInstance().getPlayerCurrentDaily(player);
        OutputMessage om = new OutputMessage();
        om.putInt(GoldService.getLeftCopper(player));
        int ss = prizeList.get(day-1);
        om.putInt(ss);
        
        request.getSession().sendMessage(Protocol.RESPONSE_TOTAL_GOLDS, om);
    }
    
}
