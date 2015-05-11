package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.ClientToolNoticeAction;
import com.orange.goldgame.domain.ClientToolNotice;
import com.orange.goldgame.system.DBManager;

public class ClientToolNoticeActionImpl implements ClientToolNoticeAction {

	@Override
	public List<ClientToolNotice> getClientNotices() {
		return DBManager.queryClientNotices();
	}

	@Override
	public ClientToolNotice addClientNotice(ClientToolNotice notice) {
		return DBManager.insertClientNotice(notice);
	}

	@Override
	public void updateClientNotice(ClientToolNotice notice) {
		DBManager.updateClientNotice(notice);
	}

	@Override
	public void deleteClientNotice(int noticeId) {
		DBManager.deleteClientNotice(noticeId);
	}

	@Override
	public ClientToolNotice getClientToolNotice(int noticeId) {
		return DBManager.queryClientNotice(noticeId);
	}

}
