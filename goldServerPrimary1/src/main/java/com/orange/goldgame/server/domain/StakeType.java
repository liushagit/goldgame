package com.orange.goldgame.server.domain;


/**
 * 注码类型
 * @author yesheng
 *
 */
public class StakeType {
    
    private byte id;
    private int gold;
    
    public byte getId() {
        return id;
    }
    public void setId(byte id) {
        this.id = id;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    
}
