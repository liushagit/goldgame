package com.orange.goldgame.protocol;

import java.util.HashMap;
import java.util.Map;

public class ProtocolSet {

	private static final ProtocolSet instance = new ProtocolSet();
	private Map<String, String> protocolMap = new HashMap<String, String>();
	public static ProtocolSet getInstance() {
		return instance;
	}
	private ProtocolSet(){}
	
	public void init(){
		
	}
}
