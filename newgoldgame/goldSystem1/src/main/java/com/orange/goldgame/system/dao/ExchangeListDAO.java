package com.orange.goldgame.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.juice.orange.game.database.ConnectionResource;
import com.juice.orange.game.database.IJuiceDBHandler;
import com.orange.goldgame.domain.ExchangeListModel;
/**
 * 兑换榜单DAO
 * @author hcz
 *
 */
public class ExchangeListDAO extends ConnectionResource {
	private static IJuiceDBHandler<ExchangeListModel> LIMIT_HANDLER = new IJuiceDBHandler<ExchangeListModel>() {
		@Override
		public ExchangeListModel handler(ResultSet rs) throws SQLException {
			ExchangeListModel e = new ExchangeListModel();
			  e.setNickname(rs.getString("nickname"));
			  e.setExchangeVoucher(rs.getInt("ExchangeVoucher"));
			return e;
		}
	};
	
	/** 请求兑换榜*/
	public List<ExchangeListModel> getExchangeList(){
	    String sql="SELECT pl.nickname ,es.exchangeVoucher" +
                " from player pl" +
                " RIGHT JOIN" +
                " (SELECT playerId ,exchangeVoucher " +
                " FROM estate" +
                " ) es" +
                " ON pl.playerId = es.playerId" +
                " ORDER BY es.exchangeVoucher DESC" +
                " LIMIT 0,50";
		return this.queryForList(sql,LIMIT_HANDLER);
	}
	
}
