package com.orange.goldgame.system.ao;

import java.util.Date;

/**
 * 日志操作类
 * @author Administrator
 *
 */
public class LogAO extends BaseAO {

	/** 添加操作日志*/
	public void add(int playerId,String type ,Date date){
		this.factory.getLogDAO().addLog(playerId, type, date);
	}
}
