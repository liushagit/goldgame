package com.orange.goldgame.server.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.cached.MemcachedResource;
import com.juice.orange.game.container.GameSession;
import com.juice.orange.game.log.LoggerFactory;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.Constants;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerInfoCenter;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.service.PlayerService;

public class PlayerInfoCenterManager {
    
	private static Logger log=LoggerFactory.getLogger(PlayerInfoCenterManager.class);
    private static PlayerInfoCenterManager instance = new PlayerInfoCenterManager();
    
    public static final int LENGTH = 20;
    
    private PlayerInfoCenterManager(){
        
    }
    
    public List<PlayerInfoCenter> getPlayerInfoCeterListByPlayerId(int playerId){
        
        Player player = PlayerService.getSimplePlayerByPlayerId(playerId);
        
        if(player == null){
            return null;
        }
        Object object = null;
        try {
        	object = MemcachedResource.get(Constants.MESSAGE_INOF + playerId);
		} catch (Exception e) {
		}
        List<PlayerInfoCenter> list = null;
        if (object != null) {
        	try{
        		list = (List<PlayerInfoCenter>) object;
        	}catch(Exception e){
        		log.info("Memcache error....");
        	}
		}
        if(list == null){
            list = BaseEngine.getInstace()
                    .getPlayerInfoCenterAction()
                    .getPlayerInfoCeterListByPlayerId(playerId);
            player.setInfoCenter(list);
        }
        return sortAndLimitCount(list);
        
    }
    
    private List<PlayerInfoCenter> sortAndLimitCount(List<PlayerInfoCenter> list){
        Collections.sort(list,new Comparator<PlayerInfoCenter>() {
            @Override
            public int compare(PlayerInfoCenter o1, PlayerInfoCenter o2) {
                return (int)(o2.getCreateTime().getTime()/1000 - o1.getCreateTime().getTime()/1000);
            }
        });
        List<PlayerInfoCenter> returnList = new ArrayList<PlayerInfoCenter>();
        if(list.size()>LENGTH){
            list = list.subList(0, LENGTH);
            
        }
        returnList.addAll(list);
        return returnList;
    }
    
    public void addPlayerInfoCenter(int playerId,boolean isEditDb,int message_type,String message_info,String msg){
    	addPlayerInfoCenter(playerId, isEditDb, message_type, message_info, msg, 0, 0, 0);
    }
    
    public void addPlayerInfoCenter(int playerId,boolean isEditDb,int message_type,String message_info,String msg,int rgb,int start,int end){
        PlayerInfoCenter pic = new PlayerInfoCenter();
        pic.setCreateTime(new Date());
        pic.setPlayerId(playerId);
        
        pic.setContent(msg);
        pic.setMessageType(message_type);
        pic.setMessageInfo(message_info);
        pic.setRgb(rgb);
        pic.setStart(start);
        pic.setEnd(end);
        if(isEditDb){
            BaseEngine.getInstace().getPlayerInfoCenterAction()
            .addPlayerInfoCenter(pic);
        }
        List<PlayerInfoCenter> list = getPlayerInfoCeterListByPlayerId(playerId);
        if(!list.contains(pic)){
        	list.add(pic);        	
        }
        if (list != null && list.size() > 0) {
        	MemcachedResource.save(Constants.MESSAGE_INOF + pic.getPlayerId(), list, 15 * 60);
		}
    }
    

    public static PlayerInfoCenterManager getInstance() {
        return instance;
    }
    
    /**
     * 通知玩家消息栏需要变亮
     * @param session
     */
    public void noticePlayerInfoCenter(GameSession session){
    	OutputMessage msg = new OutputMessage();
    	session.sendMessage(Protocol.RESPONSE_NOTICE_INFO, msg);
    }
}
