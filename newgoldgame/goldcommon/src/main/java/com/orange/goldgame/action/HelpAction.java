package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.HelpQa;
import com.orange.goldgame.domain.HelpType;

public interface HelpAction {
	
	public List<HelpQa> loadAllHelpInfo();
	
	public List<HelpType> loadHelpTypeInfo();

}
