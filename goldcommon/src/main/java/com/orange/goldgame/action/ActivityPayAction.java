package com.orange.goldgame.action;


import com.orange.goldgame.domain.ActivityPayRecord;

public interface ActivityPayAction {
	
	public int addActivityPayRecord(ActivityPayRecord record);
	
	public void updateActivityPayRecord(ActivityPayRecord record);
	
	public ActivityPayRecord findActivityPayRecord(int player_id,String pay_sign);

}
