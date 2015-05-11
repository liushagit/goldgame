package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.ShareRewardAction;
import com.orange.goldgame.domain.ShareReward;
import com.orange.goldgame.system.DBManager;

public class ShareRewardActionImpl implements ShareRewardAction{

	@Override
	public List<ShareReward> quaryAll() {
		return DBManager.quaryAllShareRewards();
	}

}
