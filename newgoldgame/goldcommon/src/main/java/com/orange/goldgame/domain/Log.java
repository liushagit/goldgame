package com.orange.goldgame.domain;

import java.util.Date;

/**
 * 日志记录
 * @author hcz
 *
 */
public class Log {
	private int logId;
	private String type;//操作类型
	private Date date;//操作日期
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
