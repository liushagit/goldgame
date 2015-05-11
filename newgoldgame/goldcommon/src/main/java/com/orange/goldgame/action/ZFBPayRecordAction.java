package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.ZfbPayRecord;

public interface ZFBPayRecordAction {
	
	
	public void addZFBPayRecord(ZfbPayRecord record);
	
	public List<ZfbPayRecord> getZFBRecordsByPlayerId(int playerId);

}
