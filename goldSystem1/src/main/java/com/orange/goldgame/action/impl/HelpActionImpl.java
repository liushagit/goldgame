package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.HelpAction;
import com.orange.goldgame.domain.HelpQa;
import com.orange.goldgame.domain.HelpType;
import com.orange.goldgame.system.DBManager;

public class HelpActionImpl implements HelpAction {

	@Override
	public List<HelpQa> loadAllHelpInfo(int helpTypeId) {		
		return DBManager.getHelpInfos(helpTypeId);
	}

	@Override
	public HelpType loadHelpTypeInfo(int helpTypeId) {
		return DBManager.getHelpType(helpTypeId);
	}

}
