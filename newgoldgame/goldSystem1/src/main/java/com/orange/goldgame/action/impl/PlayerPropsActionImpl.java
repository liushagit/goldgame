package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.PlayerPropsAction;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.system.DBManager;

public class PlayerPropsActionImpl implements PlayerPropsAction{

	@Override
	public void updatePlayerProps(PlayerProps pp) {
		DBManager.updatePlayerProps(pp);
	}

	@Override
	public List<PlayerProps> quaryAllPlayerProps(Player player) {
		return DBManager.quaryAllPlayerProps(player);
	}

	@Override
	public int insertPlayerProp(PlayerProps pp) {
		return DBManager.insertPlayerProps(pp);
	}

	@Override
	public void deletePlayerProp(PlayerProps pp) {
		DBManager.deletePlayerProps(pp);
	}

	@Override
	public List<PlayerProps> getAllPlayerExhangeCount(int exchangeID) {		
		return DBManager.getAllPlayerExhangeCount(exchangeID);
	}

	@Override
	public PlayerProps getPlayerProp(int playerId,int propId) {		
		return DBManager.getPlayerProp(playerId, propId);
	}


}
