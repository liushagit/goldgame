package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.PlayerTalkAction;
import com.orange.goldgame.domain.PlayerTalk;
import com.orange.goldgame.system.DBManager;

public class PlayerTalkActionImpl implements PlayerTalkAction{

	@Override
	public void insertPlayerTalk(PlayerTalk talk) {
		DBManager.insertPlayerTalk(talk);
	}

	@Override
	public List<PlayerTalk> queryAllTalkFofPlayer(int playerId) {
		return DBManager.queryPlayerTalkList(playerId);
	}

}
