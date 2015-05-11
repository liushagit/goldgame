package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.PlayerPay;

public interface PlayerPayAction {

	PlayerPay quaryPlayerPay(int player_id, String order_id);

	
	int insertPlayerPay(PlayerPay pay);
	
//	public List<PlayerPay> queryAllPayForPlayer(int playerId);

	List<PlayerPay> queryAllPayForPlayer(int playerId);

	int getSumToday(int playerId);
	
	public List<PlayerPay> queryPayByRange(int start , int end);
}
