package com.orange.goldgame.admin.command;

import java.util.LinkedList;
import java.util.List;


public class AdminCmdSet {
	private static  final AdminCmdSet install = new AdminCmdSet();
	
	public static AdminCmdSet getInstall() {
		return install;
	}

	private List<AdminCmd> chain = new LinkedList<AdminCmd>();
	
	public List<AdminCmd> getChain() {
		return chain;
	}

	public  void init(){
		chain.add(new OnleNumCmd());
		chain.add(new CloseRobotCmd());
		chain.add(new ClosePresentCmd());
		chain.add(new ChartCmd());
	}
}
