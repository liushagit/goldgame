package com.orange.goldgame.vo;

import java.util.List;

public class OutShareIndexVo {

	private String aInviteCode;
	private int bFriendNum;
	private byte cIsInvited;
	private String dShareContent;
	private String eAppUrl;
	private List<OutShareIndexRewardListVo> fShareRewardList;
	private List<OutShareIndexPlayerListVo> gSharePlayerList;
	private String hIconUrl;
	public String getaInviteCode() {
		return aInviteCode;
	}
	public void setaInviteCode(String aInviteCode) {
		this.aInviteCode = aInviteCode;
	}
	public int getbFriendNum() {
		return bFriendNum;
	}
	public void setbFriendNum(int bFriendNum) {
		this.bFriendNum = bFriendNum;
	}
	public byte getcIsInvited() {
		return cIsInvited;
	}
	public void setcIsInvited(byte cIsInvited) {
		this.cIsInvited = cIsInvited;
	}
	public String getdShareContent() {
		return dShareContent;
	}
	public void setdShareContent(String dShareContent) {
		this.dShareContent = dShareContent;
	}
	public String geteAppUrl() {
		return eAppUrl;
	}
	public void seteAppUrl(String eAppUrl) {
		this.eAppUrl = eAppUrl;
	}
	public List<OutShareIndexRewardListVo> getfShareRewardList() {
		return fShareRewardList;
	}
	public void setfShareRewardList(List<OutShareIndexRewardListVo> fShareRewardList) {
		this.fShareRewardList = fShareRewardList;
	}
	public List<OutShareIndexPlayerListVo> getgSharePlayerList() {
		return gSharePlayerList;
	}
	public void setgSharePlayerList(List<OutShareIndexPlayerListVo> gSharePlayerList) {
		this.gSharePlayerList = gSharePlayerList;
	}
	public String gethIconUrl() {
		return hIconUrl;
	}
	public void sethIconUrl(String hIconUrl) {
		this.hIconUrl = hIconUrl;
	}
}
