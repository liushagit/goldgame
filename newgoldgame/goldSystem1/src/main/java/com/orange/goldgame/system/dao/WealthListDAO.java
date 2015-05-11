package com.orange.goldgame.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.juice.orange.game.database.ConnectionResource;
import com.juice.orange.game.database.IJuiceDBHandler;
import com.orange.goldgame.domain.WealthListModel;

public class WealthListDAO extends ConnectionResource {
	private static IJuiceDBHandler<WealthListModel> LIMIT_HANDLER = new IJuiceDBHandler<WealthListModel>() {
		@Override
		public WealthListModel handler(ResultSet rs) throws SQLException {
			WealthListModel w = new WealthListModel();
			w.setNickname(rs.getString("nickname"));
			w.setAppellation(rs.getString("appellation"));
			w.setGolds(rs.getInt("golds"));
			return w;
		}
	};
	
	/** 请求财富榜*/
	public List<WealthListModel> getWealthList(){

		String sql="SELECT pl.nickname ,pl.appellation ,es.golds" +
				   " from player pl" +
				   " RIGHT JOIN" +
				   " (SELECT playerId ,golds " +
				   " FROM estate" +
				   " ) es" +
				   " ON pl.playerId = es.playerId" +
				   " ORDER BY es.golds DESC" +
				   " LIMIT 0,50";
		return this.queryForList(sql,LIMIT_HANDLER);

	}
}
