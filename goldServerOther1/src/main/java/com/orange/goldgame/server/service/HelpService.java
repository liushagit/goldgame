package com.orange.goldgame.server.service;

import java.util.List;

import com.orange.goldgame.core.Constants;
import com.orange.goldgame.core.ResourceCache;
import com.orange.goldgame.domain.HelpQa;
import com.orange.goldgame.domain.HelpType;
import com.orange.goldgame.server.engine.BaseEngine;

public class HelpService {
	
	public static String getHelpTypeName(int helpTypeId){
		HelpType type=BaseEngine.getInstace().getHelpAction().loadHelpTypeInfo(helpTypeId);
		return type.getName();
	}
	
	public static List<HelpQa> getAllHelpInfo(int helpTypeId){
		return BaseEngine.getInstace().getHelpAction().loadAllHelpInfo(helpTypeId);
	}
	
	public static String getCustomerInfo(){
	    return ResourceCache.getInstance().getAppConfigs()
	            .get(Constants.CUSTOMER_INFO).getAppValue();
	}
}
