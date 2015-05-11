package com.orange.goldgame.action;

public interface ExitedPlayerAction {
	boolean isExitInGameServer(int playerId);
	void removePlayer(int playerId);
	int getSessionNum();
}
