
package com.orange.goldgame.action.impl;

import java.util.List;

import com.orange.goldgame.action.NoticeConfigAction;
import com.orange.goldgame.domain.NoticeConfig;
import com.orange.goldgame.system.DBManager;

public class NoticeConfigActionImpl implements NoticeConfigAction{

    @Override
    public List<NoticeConfig> getAllNoticeConfig() {
        return DBManager.loadAllNoticeConfig();
    }

	@Override
	public NoticeConfig addNoticeConfig(NoticeConfig notice) {
		return DBManager.insertNoticeConfig(notice);
	}

	@Override
	public void updateNoticeConfig(NoticeConfig notice) {
		DBManager.updateNoticeConfig(notice);
	}

	@Override
	public void deleteNoticeConfig(int noticeConfigId) {
		DBManager.deleteNoticeConfig(noticeConfigId);
	}

	@Override
	public NoticeConfig getNoticeConfig(int noticeId) {
		return DBManager.queryNoticeConfig(noticeId);
	}

}
