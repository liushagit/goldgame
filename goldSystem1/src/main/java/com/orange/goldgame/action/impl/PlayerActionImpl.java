package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.PlayerAction;
import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;
import com.orange.goldgame.system.DBManager;

public class PlayerActionImpl implements PlayerAction {

	@Override
	public Player login(String account, String nickname, String password) {
		return null;
//		return getPlayerAO().login(account, nickname, password);
//		return getPlayerAO().login(account, nickname, password);
	}

	@Override
	public void update(int playerId, String sex, int age, String nickname,
			String tag, String connect) {
//		getPlayerAO().update(playerId, sex, age, nickname, tag, connect);
//		getPlayerAO().update(playerId, sex, age, nickname, tag, connect);
	}

	@Override
	public List<Player> getPlayerMsg(int playerId) {
//		return getPlayerAO().getPlayerMsg(playerId);
//		return getPlayerAO().getPlayerMsg(playerId);
		return null;
	}

	@Override
	public List<Player> getPlayerMsg(String account, String password) {
		return null;
//		return getPlayerAO().getPlayerMsg(account, password);
//		return getPlayerAO().getPlayerMsg(account, password);
	}

	@Override
	public Player loadPlayerByCode(String machineCode) {
		Player player = DBManager.getPlayerByCode(machineCode);
		if (player == null) {
			return null;
		}
		return player;
	}

	@Override
	public Player loadPlayerById(int playerId) {
		return DBManager.getPlayerById(playerId);
	}

	@Override
	public Player createPlayer(String nickname,String machineCode,String app_channel) {
		return DBManager.createPlayer(nickname,machineCode,app_channel);
	}

	@Override
	public void modifyPlayer(Player p) {
		DBManager.updatePlayer(p);
	}

	@Override
	public List<PlayerProps> loadAllPersonalItemById(int playerId) {
		List<PlayerProps> persionalItems = DBManager.loadAllPlayerProps(playerId);
		return persionalItems;
	}

	@Override
	public List<Player> getAllPlayers() {
		List<Player> players=DBManager.loadAllPlayers();
		return players;
	}
	
	

}
