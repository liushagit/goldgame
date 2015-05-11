package com.orange.goldgame.business.entity;

public class ErrorCode {

	private int status;
	private String msg;
	private Object object;
	public static final int SUCC = 0;
	public ErrorCode(int status){
		this.status = status;
	}
	public ErrorCode(int status,String msg){
		this.status = status;
		this.msg = msg;
	}
	public ErrorCode(int status,String msg,Object object){
		this.status = status;
		this.msg = msg;
		this.object = object;
	}
	public ErrorCode(int status,Object object){
		this.status = status;
		this.object = object;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
