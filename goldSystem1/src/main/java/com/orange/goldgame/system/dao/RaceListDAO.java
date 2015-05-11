package com.orange.goldgame.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.juice.orange.game.database.ConnectionResource;
import com.juice.orange.game.database.IJuiceDBHandler;
import com.orange.goldgame.domain.RaceListModel;
/**
 * 比赛榜单
 * @author Administrator
 *
 */
public class RaceListDAO extends ConnectionResource {
	private static IJuiceDBHandler<RaceListModel> LIMIT_HANDLER = new IJuiceDBHandler<RaceListModel>() {
		@Override
		public RaceListModel handler(ResultSet rs) throws SQLException {
			RaceListModel r = new RaceListModel();
			r.setNickname(rs.getString("nickname"));
			r.setExchangeVoucher(rs.getInt("exchangeVoucher"));
			r.setGolds(rs.getInt("golds"));
			r.setRaceNumber(rs.getInt("raceNumber"));
			return r;
		}
	};
	
	/** 请求比赛榜*/
	public List<RaceListModel> getRaceList(){
		String sql="SELECT pl.nickname,es.exchangeVoucher,es.golds,pl.wins+pl.loses AS raceNumber " +
				    "FROM player pl RIGHT JOIN estate es on pl.playerId = es.playerId " +
				    "ORDER BY pl.wins+pl.loses DESC";
		return this.queryForList(sql,LIMIT_HANDLER);
	}
}
