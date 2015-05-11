package com.orange.goldgame.system.dao;

import java.io.IOException;

import com.orange.goldgame.domain.CsvUtil;


public class ActivityCsv {
    
    private static ActivityCsv instance = null;
    private CsvUtil csv;
    public static ActivityCsv getInstance(){
        if(instance == null){
            instance = new ActivityCsv();
        }
        return instance;
    }
    private ActivityCsv(){
        String url = System.getProperty("user.dir")+"/config/activity.csv";
        try {
            csv = new CsvUtil(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public CsvUtil getCsv(){
        return csv;
    }
    
}
