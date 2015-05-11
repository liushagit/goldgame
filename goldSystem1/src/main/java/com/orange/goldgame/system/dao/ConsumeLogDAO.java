package com.orange.goldgame.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.juice.orange.game.database.ConnectionResource;
import com.juice.orange.game.database.IJuiceDBHandler;
import com.orange.goldgame.domain.ConsumeLog;

public class ConsumeLogDAO extends ConnectionResource{
    
    private static IJuiceDBHandler<ConsumeLog> LIMIT_HANDLER = new IJuiceDBHandler<ConsumeLog>() {
        @Override
        public ConsumeLog handler(ResultSet rs) throws SQLException {
            ConsumeLog c = new ConsumeLog();
            c.setId(rs.getInt("id"));
            c.setPlayerId(rs.getInt("playerId"));
            c.setDate(rs.getDate("date"));
            c.setMoney(rs.getInt("money"));
            c.setUseType(rs.getString("useType"));
            return c;
        }
    };
    
    public void addConsumeLog(ConsumeLog log){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "insert into consumelog (playerId,date,money,useType)" +
        		" values ("+log.getPlayerId()+",'"+df.format(log.getDate())+"',"+log.getMoney()+",'"+log.getUseType()+"')";
        this.executeUpdate(sql);
    }
}
