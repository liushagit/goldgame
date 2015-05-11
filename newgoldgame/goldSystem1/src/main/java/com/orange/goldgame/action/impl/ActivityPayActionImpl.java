package com.orange.goldgame.action.impl;


import com.orange.goldgame.action.ActivityPayAction;
import com.orange.goldgame.domain.ActivityPayRecord;
import com.orange.goldgame.system.DBManager;

public class ActivityPayActionImpl implements ActivityPayAction {

	@Override
	public int addActivityPayRecord(ActivityPayRecord record) {
		return DBManager.insertActivityPayRecord(record);
	}

	@Override
	public void updateActivityPayRecord(ActivityPayRecord record) {
		DBManager.updateActivityPayRecord(record);
	}

	@Override
	public ActivityPayRecord findActivityPayRecord(int player_id,String pay_sign) {
		return DBManager.queryActivityPayRecords(player_id,pay_sign);
	}

}
