package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.ClientToolNotice;

public interface ClientToolNoticeAction {
	
	public List<ClientToolNotice> getClientNotices();
	
	public ClientToolNotice addClientNotice(ClientToolNotice notice);
	
	public void updateClientNotice(ClientToolNotice notice);
	
	public void deleteClientNotice(int noticeId);
	
	public ClientToolNotice getClientToolNotice(int noticeId);

}
