package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.FriendAction;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.system.Application;
import com.orange.goldgame.system.DBManager;

public class FriendActionImpl extends Application implements FriendAction{

	@Override
	public List<PlayerFriend> getFriendsList(int playerId) {
		return DBManager.loadFriendListById(playerId);
	}

    @Override
    public void addFriend(int playerId1, int playerId2) {
//        getFriendsAO().addFriend(playerId1, playerId2);
//        getFriendsAO().addFriend(playerId1, playerId2);
    	
    }

	@Override
	public void deleteFriend(PlayerFriend pf) {
		DBManager.removeFriend(pf);
	}

	@Override
	public PlayerFriend getFriend(int playerId, int friendId) {
		
		return DBManager.selectPlayerFriend(playerId,friendId);
	}

	@Override
	public PlayerFriend addFriend(PlayerFriend pf) {
		return DBManager.inserFriend(pf);
	}

	@Override
	public void updateFriend(PlayerFriend pf) {
		DBManager.updateFriend(pf);
	}

}
