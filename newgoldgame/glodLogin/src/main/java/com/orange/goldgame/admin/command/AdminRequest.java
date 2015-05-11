package com.orange.goldgame.admin.command;

import java.util.LinkedList;
import java.util.List;

public class AdminRequest {
	public String cmd;
	public List<String> param = new LinkedList<String>();

	public AdminRequest(String s) {
		String[] d = s.split("[|]");
		if (d.length == 0) {
			return;
		}
		cmd = d[0];
		if (d.length > 1) {
			for (int i = 1; i < d.length; i++) {
				param.add(d[i]);
			}
		}
	}
}
