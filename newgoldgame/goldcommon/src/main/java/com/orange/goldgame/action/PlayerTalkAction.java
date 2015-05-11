package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.PlayerTalk;

public interface PlayerTalkAction {
	
	public void insertPlayerTalk(PlayerTalk talk);
	
	public List<PlayerTalk> queryAllTalkFofPlayer(int playerId);

}
