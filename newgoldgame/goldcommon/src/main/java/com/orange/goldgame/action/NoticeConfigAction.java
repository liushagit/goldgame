package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.NoticeConfig;

public interface NoticeConfigAction {
    
    public List<NoticeConfig> getAllNoticeConfig();
    
    public NoticeConfig addNoticeConfig(NoticeConfig notice);
    
    public void updateNoticeConfig(NoticeConfig notice);
    
    public void deleteNoticeConfig(int noticeConfigId);
    
    public NoticeConfig getNoticeConfig(int noticeId);
    
}
