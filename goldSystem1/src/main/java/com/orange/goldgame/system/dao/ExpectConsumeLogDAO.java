package com.orange.goldgame.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.juice.orange.game.database.IJuiceDBHandler;
import com.orange.goldgame.domain.ExpectConsumeLog;

public class ExpectConsumeLogDAO {
    private static IJuiceDBHandler<ExpectConsumeLog> LIMIT_HANDLER = new IJuiceDBHandler<ExpectConsumeLog>() {
        @Override
        public ExpectConsumeLog handler(ResultSet rs) throws SQLException {
            ExpectConsumeLog f = new ExpectConsumeLog();
            f.setId(rs.getInt("id"));
            f.setPlayerId(rs.getInt("playerId"));
            f.setMoney(rs.getInt("money"));
            f.setDate(rs.getDate("date"));
            return f;
        }
    };
    
    public void addLog(ExpectConsumeLog log){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "insert into ExpectConsumeLog (playerId,money,date)" +
        		" values("+log.getPlayerId()+","+log.getMoney()+","+df.format(log.getDate())+")";
    }
}
