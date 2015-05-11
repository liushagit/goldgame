package com.orange.goldgame.command;

import java.io.Serializable;

public class CommandResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private Object result;
	//////////////////////////////////////////////////////////////////
	public CommandResult() {
	}
	
	public CommandResult(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
