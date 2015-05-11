package com.orange.goldgame.vo;

import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.util.IOutputMeasageVo;

public class GamingPlayerSeat implements IOutputMeasageVo{

    private int playerId;
    private byte seatId;
    
    @Override
    public int getLength() {
        return 4+1;
    }

    @Override
    public OutputMessage parseMessage() {
        OutputMessage om = new OutputMessage();
        om.putInt(playerId);
        om.putByte(seatId);
        return om;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public byte getSeatId() {
        return seatId;
    }

    public void setSeatId(byte seatId) {
        this.seatId = seatId;
    }

}
