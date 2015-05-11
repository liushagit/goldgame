package com.orange.goldgame.action.impl;

import java.sql.SQLException;
import java.util.List;

import com.orange.goldgame.action.PayOrderAction;
import com.orange.goldgame.domain.PayOrder;
import com.orange.goldgame.domain.PayOrderExample;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.PayOrderDAO;
import com.orange.goldgame.system.dao.PayOrderDAOImpl;

public class PayOrderActionImpl implements PayOrderAction {
	private PayOrderDAO dao = (PayOrderDAO)DBManager.getDao(PayOrderDAOImpl.class);
	@Override
	public int insertPayOrder(PayOrder order) {
		
		int res = -1;
		try {
			res = dao.insert(order);
		} catch (SQLException e) {
			res = -1;
			DBManager.log.error("insertPayOrder", e);
		}
		return res;
	}

	@Override
	public PayOrder loadPayOrderById(int orderId) {
		PayOrderExample example = new PayOrderExample();
		example.createCriteria().andIdEqualTo(orderId);
		PayOrder order = null;
		try {
			List<PayOrder> list = dao.selectByExample(example);
			if (list != null || list.size() > 0) {
				order = list.get(0);
			}
		} catch (SQLException e) {
			DBManager.log.error("loadPayOrderById", e);
			order = null;
		}
		return order;
	}

	@Override
	public void updatePayOrder(PayOrder order) {
		try {
			dao.updateByPrimaryKey(order);
		} catch (SQLException e) {
			DBManager.log.error("updatePayOrder", e);
		}
	}

}
