package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.HelpAction;
import com.orange.goldgame.domain.HelpQa;
import com.orange.goldgame.domain.HelpType;
import com.orange.goldgame.system.DBManager;

public class HelpActionImpl implements HelpAction {

	@Override
	public List<HelpQa> loadAllHelpInfo() {
		return DBManager.getHelpInfos();
	}

	@Override
	public List<HelpType> loadHelpTypeInfo() {
		return DBManager.getHelpType();
	}

}
