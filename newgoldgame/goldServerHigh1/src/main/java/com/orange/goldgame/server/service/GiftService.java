package com.orange.goldgame.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.Gift;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.server.engine.BaseEngine;

public class GiftService {
    //key是道具ID
    private Map<Integer,PropsConfig> giftConfigMap;
    
    private static final GiftService instance = new GiftService();
    private Map<Integer, Integer> initMap = new HashMap<Integer, Integer>();
    
    
    public Map<Integer, Integer> getInitMap() {
		return initMap;
	}

	public static GiftService getInstance(){
        return instance;
    }
    
    private GiftService(){
    	initMap.put(9, 10);
    	initMap.put(10, 10);
    }
    
    public Map<Integer,PropsConfig> getAllGiftConfig(){
        if(giftConfigMap==null){
            giftConfigMap = new HashMap<Integer, PropsConfig>();
            Map<Integer, PropsConfig> configMap = ResourceCache.getInstance().getPropsConfigs();
            for(int id : configMap.keySet()){
                PropsConfig pc = configMap.get(id);
                if(pc.getPropsType().equals(PropCacheAction.PROP_PRESS)){
                    giftConfigMap.put(id, pc);
                }
            }
        }
        return giftConfigMap;
    }
    
    public void addGift(Player player,int giftId){
        Gift gift = getPlayerGift(player,giftId);
        if(gift==null){
            gift = BaseEngine.getInstace().getGiftActionIpml(player.getId()).findGiftByPlayerIdAndPropId(player.getId(), giftId);
            getGiftMap(player).put(gift.getPropId(), gift);
        }
        gift.setAmont(gift.getAmont()+1);
        BaseEngine.getInstace().getGiftActionIpml(player.getId()).update(gift);
    }
    
    private Map<Integer, Gift> getGiftMap(Player player){
        Map<Integer, Gift> giftMap = player.getGiftMap();
        if(giftMap == null){
            giftMap = new HashMap<Integer, Gift>();
            List<Gift> gifts = BaseEngine.getInstace().getGiftActionIpml(player.getId())
                .findGiftByPlayerId(player.getId());
            if(gifts!=null){
                for(Gift gift : gifts){
                    giftMap.put(gift.getPropId(), gift);
                }
            }
            player.setGiftMap(giftMap);
        }
        return giftMap;
    }
    
    public Gift getPlayerGift(Player player,int giftId){
        Gift gift = getGiftMap(player).get(giftId);
        return gift;
    }
}
