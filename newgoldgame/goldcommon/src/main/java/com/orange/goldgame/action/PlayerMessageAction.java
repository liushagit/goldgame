package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.PlayerMessage;

public interface PlayerMessageAction {
	int insert(PlayerMessage playerMessage);

	List<PlayerMessage> quaryAllMessage(int playerId, int loverId);

	void update(PlayerMessage playerMessage);

	void updateDeleteAllPlayerMessage(List<PlayerMessage> updateMessage,List<PlayerMessage> deleteMessage);
}
