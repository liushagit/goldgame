package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.PlayerFriend;

/**
 * 好友action
 * @author hcz
 *
 */
public interface FriendAction {
	/** 返回好友列表*/
	public List<PlayerFriend> getFriendsList(int playerId);
	
	/** 添加好友 */
	public void addFriend(int playerId1,int playerId2);

	public void deleteFriend(PlayerFriend pf);

	public PlayerFriend getFriend(int playerId, int friendId);

	public PlayerFriend addFriend(PlayerFriend pf);
	
	public void updateFriend(PlayerFriend pf);
}
