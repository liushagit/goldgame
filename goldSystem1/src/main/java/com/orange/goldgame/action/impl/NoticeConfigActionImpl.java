
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

}
