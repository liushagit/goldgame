package com.orange.goldgame.action;

import com.orange.goldgame.domain.PlayerPayApril;

public interface PlayerPayAprilAction {

	PlayerPayApril quaryPlayerPayApril(int playerId);

	void updatePlayerPayApril(PlayerPayApril april);

	int insertPlayerPayApril(PlayerPayApril april);
}
