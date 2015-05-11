package com.orange.goldgame.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juice.orange.game.container.GameSession;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.manager.SessionManger;
/**
 * @author wuruihuang 2013.7.15
 */
public class FriendService {
	public static final int FRIENDTYPE = 1;
	public static final int LOVERTYPE = 2;
	public static final int LOVERSTATUSSUCC = 0;
	

	public static Map<Integer,PlayerFriend> getPlayerFriendByPlayer(Player player,int friendType){
		return getPlayerFriendByPlayer(player,true,friendType);
	}

	public static Map<Integer,PlayerFriend> getPlayerFriendByPlayer(Player player,boolean fush,int friendType){
		Map<Integer,PlayerFriend> pfs = player.getFriendMap();
		Map<Integer,PlayerFriend> lovers = player.getLoverMap();
		
		if(pfs==null || fush){
			pfs = new HashMap<Integer,PlayerFriend>();
			lovers = new HashMap<Integer,PlayerFriend>();
            List<PlayerFriend> pfl = BaseEngine.getInstace().getFriendActionIpml().getFriendsList(player.getId());
            for(PlayerFriend pf : pfl){
            	if (pf.getFriendType().intValue() == LOVERTYPE) {
            		lovers.put(pf.getFriendId(), pf);
				}
            	if (pf.getFriendType().intValue() == FRIENDTYPE) {
            		pfs.put(pf.getFriendId(), pf);
				}
            	if(pf.getFriendId()>0){
            		GameSession session = SessionManger.getInstance().getSession(pf.getFriendId());
            		if (session == null) {
						continue;
					}
            		Player fPlayer = PlayerService.getOnLinePlayer(session);
            		if (fPlayer == null) {
						continue;
					}
//            		Player fPlayer=PlayerService.quaryPlayerByid(pf.getFriendId());
            		if(fPlayer!=null&&!pf.getFriendName().equals(fPlayer.getNickname())){
            			pf.setFriendName(fPlayer.getNickname());
            			BaseEngine.getInstace().getFriendActionIpml().updateFriend(pf);
            		}           		
            	}
            }
        }
		player.setLoverMap(lovers);
		if (friendType == LOVERTYPE) {
    		return lovers;
		}
        return pfs;
    }
    
    
    public static PlayerFriend addFriend(Player player,Player friend,int friendType){
    	int status = friendType == LOVERTYPE ? 1 : 0;
    	return addFriend(player, friend, friendType, status);
    }
    public static PlayerFriend addFriend(Player player,Player friend,int friendType,int status){
        PlayerFriend pf = new PlayerFriend();
        pf.setAddTime(new Date());
        pf.setFriendId(friend.getId());
        pf.setFriendName(friend.getNickname());
        pf.setFriendType(friendType);
        pf.setFriendStatus(status);
        pf.setPlayerId(player.getId());
        pf.setSex(friend.getSex()==1?true:false);
        pf.setLoverIntimacy(0);
        pf.setLoverGifts("");
        getPlayerFriendByPlayer(player,friendType).put(pf.getFriendId(), pf);
        int res = BaseEngine.getInstace().getFriendActionIpml().addFriend(pf);
        if (res < 0) {
			return null;
		}
        pf.setId(res);
        return pf;
    }
    
    public static void deleteFriend(Player player,int friendId){
        PlayerFriend pf = getPlayerFriendByPlayer(player,FRIENDTYPE).remove(friendId);
        BaseEngine.getInstace().getFriendActionIpml().deleteFriend(pf);
    }
    
}
