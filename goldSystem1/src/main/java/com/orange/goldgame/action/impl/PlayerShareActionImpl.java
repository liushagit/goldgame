package com.orange.goldgame.action.impl;

import com.orange.goldgame.action.PlayerShareAction;
import com.orange.goldgame.domain.PlayerShare;
import com.orange.goldgame.domain.PlayerShareInfo;
import com.orange.goldgame.system.DBManager;

public class PlayerShareActionImpl implements PlayerShareAction{

	@Override
	public int insert(PlayerShare playerShare,PlayerShareInfo playerShareInfo) {
		return DBManager.insertPlayerShare(playerShare,playerShareInfo);
	}

	@Override
	public void update(PlayerShare playerShare,PlayerShareInfo playerShareInfo) {
		DBManager.updatePlayerShare(playerShare,playerShareInfo);
	}

}
