package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.PayInfoAction;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.system.DBManager;

public class PayInfoActionImpl implements PayInfoAction{

	@Override
	public List<PayInfo> quaryAllPayInfos() {
		return DBManager.quaryAllPayInfos();
	}

}
