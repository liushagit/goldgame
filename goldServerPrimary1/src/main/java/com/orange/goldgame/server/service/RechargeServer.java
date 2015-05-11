package com.orange.goldgame.server.service;

import java.util.Date;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketRequest;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.InputMessage;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.HandlerEngine;

public class RechargeServer extends BaseServer{
    
    
    public void addExpectConsumeLog(SocketRequest request, SocketResponse response) throws JuiceException{
        InputMessage im = request.getInputMessage();
        int playerId = im.getInt();
        int money = im.getInt();
        Date date = new Date();
        
        HandlerEngine.addExpectConsumeLog(playerId,money,date);
        OutputMessage om = new OutputMessage();
        om.putShort((short)1);
        
        request.getSession().sendMessage(Protocol.RESPONSE_EXPECT_READY_PAY, om);
        
    }
    
    
    
}
