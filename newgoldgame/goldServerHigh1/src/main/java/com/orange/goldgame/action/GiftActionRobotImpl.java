package com.orange.goldgame.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.juice.orange.game.cached.MemcachedResource;
import com.orange.goldgame.cache.action.PropCacheAction;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.Gift;
import com.orange.goldgame.domain.PropsConfig;
import com.orange.goldgame.server.manager.RobotManager;
import com.orange.goldgame.server.service.GiftService;

public class GiftActionRobotImpl implements GiftAction{
    private Random rd = new Random();
    

    @Override
    public List<Gift> findGiftByPlayerId(int playerId) {
        List<Gift> list = new ArrayList<Gift>();
        Map<Integer, PropsConfig> configMap = ResourceCache.getInstance().getPropsConfigs();
        for(int id : configMap.keySet()){
            if(configMap.get(id).getPropsType().equals(PropCacheAction.PROP_PRESS)){
                list.add(initGift(playerId,id));
            }
        }
        return list;
    }

    @Override
    public void update(Gift gift) {
    	String key = RobotManager.getInstance().getPresentKey(gift.getPlayerId()) + "_" + gift.getPropId();
    	Object object = MemcachedResource.get(key);
    	String propNum[] = new String[2];
    	if (object != null) {
    		propNum = object.toString().split("_");
    		int num = Integer.parseInt(propNum[1]);
    		num ++;
    		propNum[1] = num + "";
		}else {
			propNum[0] = gift.getPropId() + "";
			propNum[1] = gift.getAmont() + "";
		}
    	MemcachedResource.save(key , propNum[0] + "_" + propNum[1], 15 * 24 * 60 * 60);
    }

    @Override
    public Gift findGiftByPlayerIdAndPropId(int playerId, int propId) {
        return initGift(playerId, propId);
    }
    
    public Gift initGift(int playerId, int propId) {
        Gift gift = new Gift();
        if (GiftService.getInstance().getInitMap().containsKey(propId)) {
        	gift.setAmont(rd.nextInt(GiftService.getInstance().getInitMap().get(propId)));
		}else {
			gift.setAmont(0);
		}
        gift.setPlayerId(playerId);
        gift.setPropId(propId);
        setGift(gift);
        return gift;
    }
    

    private void setGift(Gift gift){
    	String key = RobotManager.getInstance().getPresentKey(gift.getPlayerId()) + "_" + gift.getPropId();
    	Object object = MemcachedResource.get(key);
    	String propNum[] = new String[2];
    	if (object != null) {
    		propNum = object.toString().split("_");
    		gift.setAmont(Integer.parseInt(propNum[1]));
		}else {
			MemcachedResource.save(key , gift.getPropId() + "_" + gift.getAmont(), 15 * 24 * 60 * 60);
		}
    }
}
