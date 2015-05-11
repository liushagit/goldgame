package com.orange.goldgame.action.impl;

import com.orange.goldgame.action.ExpectConsumeLogAction;
import com.orange.goldgame.domain.ExpectConsumeLog;
import com.orange.goldgame.system.Application;

public class ExpectConsumeLogAcgtionImpl extends Application implements ExpectConsumeLogAction{

    @Override
    public void insertLog(ExpectConsumeLog log) {
        getExpectConsumeLogAO().addLog(log);
    }

}
