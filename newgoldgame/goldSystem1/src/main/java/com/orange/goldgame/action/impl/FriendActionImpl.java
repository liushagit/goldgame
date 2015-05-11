package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.orange.goldgame.action.FriendAction;
import com.orange.goldgame.domain.PlayerFriend;
import com.orange.goldgame.domain.PlayerFriendExample;
import com.orange.goldgame.system.Application;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PlayerFriendDAO;
import com.orange.goldgame.system.dao.PlayerFriendDAOImpl;

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
	public int addFriend(PlayerFriend pf) {
		return DBManager.inserFriend(pf);
	}

	@Override
	public void updateFriend(PlayerFriend pf) {
		DBManager.updateFriend(pf);
	}

	@Override
	public List<PlayerFriend> getTop() {
		PlayerFriendDAO dao = (PlayerFriendDAO) DBManager.getDao(PlayerFriendDAOImpl.class);
		PlayerFriendExample example = new PlayerFriendExample();
		example.createCriteria().andFriendTypeEqualTo(2).andLoverIntimacyGreaterThan(0);
		example.setOrderByClause("lover_intimacy desc limit 20");
		List<PlayerFriend> list = null;
		try {
			list = dao.selectByExample(example);
		} catch (SQLException e) {
			list = new ArrayList<PlayerFriend>();
		}
		return list;
	}

}
