package com.orange.goldgame.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.juice.orange.game.database.ConnectionResource;
import com.juice.orange.game.database.IJuiceDBHandler;
import com.orange.goldgame.domain.Activity;
import com.orange.goldgame.domain.CsvUtil;

public class ActivityDAO extends ConnectionResource{
    private static IJuiceDBHandler<Activity> LIMIT_HANDLER = new IJuiceDBHandler<Activity>() {
        @Override
        public Activity handler(ResultSet rs) throws SQLException {
            Activity c = new Activity();
             c.setId(rs.getInt("id"));
             c.setIntroduction(rs.getString("giftNumber"));
             c.setName(rs.getString("giftWealth"));
            return c;
        }
    };
    
    public List<Activity> getAllActivity(){
        List<Activity> acts = new ArrayList<Activity>();
        CsvUtil csvUtil = new CsvUtil();
        return null;
    }
}
