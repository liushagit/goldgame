//package com.orange.goldgame.action.impl;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.orange.goldgame.action.PlayerLoverAction;
//import com.orange.goldgame.domain.Player;
//import com.orange.goldgame.domain.PlayerLover;
//import com.orange.goldgame.domain.PlayerLoverExample;
//import com.orange.goldgame.system.DBManager;
//import com.orange.goldgame.system.dao.PlayerLoverDAO;
//import com.orange.goldgame.system.dao.PlayerLoverDAOImpl;
//
//public class PlayerLoverActionImpl implements PlayerLoverAction{
//
//	@Override
//	public int insert(PlayerLover playerLover) {
//		return DBManager.insertPlayerLover(playerLover);
//	}
//
//	@Override
//	public void update(PlayerLover playerLover) {
//		DBManager.updatePlayerLover(playerLover);
//	}
//
//	@Override
//	public List<PlayerLover> quaryAll(Player player) {
//		return DBManager.quaryAllPlayerLover(player);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<PlayerLover> quaryTop() {
//		List<PlayerLover> list = null;
//		PlayerLoverDAO dao = (PlayerLoverDAO)DBManager.getDao(PlayerLoverDAOImpl.class);
//		PlayerLoverExample example = new PlayerLoverExample();
//		example.setOrderByClause("lover_intimacy desc limit 6");
//		try {
//			list = dao.selectByExample(example);
//		} catch (SQLException e) {
//			list = new ArrayList<PlayerLover>();
//		}
//		return list;
//	}
//
//}
