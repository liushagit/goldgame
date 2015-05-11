package com.orange.goldgame.server.service;

import org.apache.log4j.Logger;

import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.InputMessage;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.exception.GoldException;
import com.orange.goldgame.server.manager.GetOnceManager;
import com.orange.goldgame.server.manager.RewardManager;
import com.orange.goldgame.server.manager.TaskManager;

public class TestServer extends BaseServer{
    
    private Logger log = LoggerFactory.getLogger(TestServer.class);
    
    public void testDailyPrizeManager(SocketRequest request, SocketResponse response)
            throws GoldException{
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        Player player = PlayerService.getPlayer(playerId, request.getSession());
        TaskManager.getInstance().driveTaskRate(player, 1);
    }
    
    public void testRewardManager (SocketRequest request, SocketResponse response)
            throws GoldException{
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        Player player = PlayerService.getPlayer(playerId, request.getSession());
        RewardManager.getInstance().gainRewardByAreaId(5, player);
    }
    
    public void testGetOnceManager (SocketRequest request, SocketResponse response)
            throws GoldException{
        InputMessage msg = request.getInputMessage();
        int playerId = msg.getInt();
        Player player = PlayerService.getPlayer(playerId, request.getSession());
        int gold = GetOnceManager.getPlayerGetOnce(player);
        log.debug(gold);
    }
    
}
