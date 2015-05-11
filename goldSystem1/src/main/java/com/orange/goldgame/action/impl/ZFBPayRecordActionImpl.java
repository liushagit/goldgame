package com.orange.goldgame.action.impl;

import com.orange.goldgame.action.ZFBPayRecordAction;
import com.orange.goldgame.domain.ZfbPayRecord;
import com.orange.goldgame.system.DBManager;

public class ZFBPayRecordActionImpl implements ZFBPayRecordAction {

	@Override
	public void addZFBPayRecord(ZfbPayRecord record) {
		DBManager.insertZFBRecord(record);
	}

}
