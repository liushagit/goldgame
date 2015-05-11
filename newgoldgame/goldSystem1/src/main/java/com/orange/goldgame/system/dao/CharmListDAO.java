package com.orange.goldgame.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.juice.orange.game.database.ConnectionResource;
import com.juice.orange.game.database.IJuiceDBHandler;
import com.orange.goldgame.domain.CharmListModel;
/**
 * 魅力榜
 * @author Administrator
 *
 */
public class CharmListDAO extends ConnectionResource {
	private static IJuiceDBHandler<CharmListModel> LIMIT_HANDLER = new IJuiceDBHandler<CharmListModel>() {
		@Override
		public CharmListModel handler(ResultSet rs) throws SQLException {
			CharmListModel c = new CharmListModel();
			 c.setNickname(rs.getString("nickname"));
			 c.setGiftNumber(rs.getInt("giftNumber"));
			 c.setGiftWealth(rs.getInt("giftWealth"));
			return c;
		}
	};
	
	/** 请求魅力榜*/
	public List<CharmListModel> getCharmList(){
	    String sql="SELECT p.nickname,g.giftNumber,g.giftWealth" +
                " FROM gift g" +
                " LEFT JOIN player p" +
                " ON p.playerId = g.playerId" +
                " ORDER BY g.giftWealth DESC" +
                " LIMIT 0,50";
	    return this.queryForList(sql,LIMIT_HANDLER);

	}
}
