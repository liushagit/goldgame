package com.orange.goldgame.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.server.engine.BaseEngine;
/**
 * @author wuruihuang 2013.7.15
 */
public class FriendService {
	

    public static Map<Integer,PlayerFriend> getPlayerFriendByPlayer(Player player){
        Map<Integer,PlayerFriend> pfs = player.getFriendMap();
        if(pfs==null){
            pfs = new HashMap<Integer,PlayerFriend>();
            List<PlayerFriend> pfl = BaseEngine.getInstace().getFriendActionIpml().getFriendsList(player.getId());
            for(PlayerFriend pf : pfl){
                pfs.put(pf.getFriendId(), pf);
            }
        }
        return pfs;
    }
    
    
    public static void addFriend(Player player,Player friend){
        PlayerFriend pf = new PlayerFriend();
        pf.setAddTime(new Date());
        pf.setFriendId(friend.getId());
        pf.setFriendName(friend.getNickname());
        pf.setFriendType(1);
        pf.setPlayerId(player.getId());
        pf.setSex(friend.getSex()==1?true:false);
        getPlayerFriendByPlayer(player).put(pf.getFriendId(), pf);
        BaseEngine.getInstace().getFriendActionIpml().addFriend(pf);
    }
    
    public static void deleteFriend(Player player,int friendId){
        PlayerFriend pf = getPlayerFriendByPlayer(player).remove(friendId);
        BaseEngine.getInstace().getFriendActionIpml().deleteFriend(pf);
    }
    
}
