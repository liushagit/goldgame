package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.PlayerPay;

public interface PlayerPayAction {

	PlayerPay quaryPlayerPay(int player_id, String order_id);

	int insertPlayerPay(PlayerPay pay);

	List<PlayerPay> queryAllPayForPlayer(int playerId);

	int getSumToday(int playerId);
}
