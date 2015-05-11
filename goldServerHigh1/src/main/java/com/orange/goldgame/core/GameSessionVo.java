package com.orange.goldgame.core;

import com.juice.orange.game.container.GameSession;

public class GameSessionVo {

	private GameSession session;
	private long lastUpdateTime;
	public GameSession getSession() {
		return session;
	}
	public void setSession(GameSession session) {
		this.session = session;
	}
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
