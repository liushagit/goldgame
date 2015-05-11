package com.orange.goldgame.command;

import java.io.Serializable;

import com.juice.orange.game.util.OutputMessage;

public class CommandRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cmd;
	private int pid;
	private String accountId;
	private OutputMessage out;

	public OutputMessage getOut() {
		return out;
	}

	public void setOut(OutputMessage out) {
		this.out = out;
	}

	public CommandRequest() {
	}

	public CommandRequest(int cmd) {
		super();
		this.cmd = cmd;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
