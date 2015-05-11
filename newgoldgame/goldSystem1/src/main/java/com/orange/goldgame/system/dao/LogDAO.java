package com.orange.goldgame.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.juice.orange.game.database.ConnectionResource;
import com.juice.orange.game.database.IJuiceDBHandler;
import com.orange.goldgame.domain.Log;
/**
 * 日志操作类DAO
 * @author Administrator
 *
 */
public class LogDAO extends ConnectionResource {
	private static IJuiceDBHandler<Log> LIMIT_HANDLER = new IJuiceDBHandler<Log>() {
		@Override
		public Log handler(ResultSet rs) throws SQLException {
			Log l= new Log();
			l.setLogId(rs.getInt("logId"));
			l.setType(rs.getString("type"));
			l.setDate(rs.getDate("date"));
			return l;
		}
	};
	
	/** 日志记录*/
	public void addLog(int playerId,String type ,Date date){
		String sql="insert into log(type,date,playerId)values(?,?,?)";
		this.saveOrUpdate(sql, type,date,playerId);
	}
}
