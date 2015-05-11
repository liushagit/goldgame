/**
 * 
 */
package com.orange.goldgame.vo;

/**
 * @author guojiang
 * 
 */
public class InPayListInfoVo {

	private int aPlayerId;
	private String bPayId;
	private int cPhoneType;
	private byte dIsFull;

	public byte getdIsFull() {
		return dIsFull;
	}

	public void setdIsFull(byte dIsFull) {
		this.dIsFull = dIsFull;
	}

	public int getaPlayerId() {
		return aPlayerId;
	}

	public void setaPlayerId(int aPlayerId) {
		this.aPlayerId = aPlayerId;
	}

	public String getbPayId() {
		return bPayId;
	}

	public void setbPayId(String bPayId) {
		this.bPayId = bPayId;
	}

	public int getcPhoneType() {
		return cPhoneType;
	}

	public void setcPhoneType(int cPhoneType) {
		this.cPhoneType = cPhoneType;
	}
}
