package com.orange.goldgame.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.juice.orange.game.database.ConnectionResource;
import com.juice.orange.game.database.IJuiceDBHandler;
import com.orange.goldgame.domain.Friends;

public class FriendsDAO extends ConnectionResource{
	private static IJuiceDBHandler<Friends> LIMIT_HANDLER = new IJuiceDBHandler<Friends>() {
		@Override
		public Friends handler(ResultSet rs) throws SQLException {
			Friends f = new Friends();
			f.setFriendId(rs.getInt("firendId"));
			f.setFriendName(rs.getString("friendName"));
			f.setIsOnline(rs.getInt("isOnline"));
			return f;
		}
	};
	
	/** 添加好友*/
	public void addFriend(String friendName,int isOnline,int playerId){
		String sql="insert into friends(friendName,isOnline,playerId)values(?,?,?)";
		this.saveOrUpdate(sql, friendName,isOnline,playerId);
	}
	
	/** 返回好友列表*/
	public List<Friends> getFriendsList(int playerId){
		String sql="select friendId,friendName,isOnline from friends where playerId=?";
		return this.queryForList(sql, LIMIT_HANDLER, playerId);
	}
}
