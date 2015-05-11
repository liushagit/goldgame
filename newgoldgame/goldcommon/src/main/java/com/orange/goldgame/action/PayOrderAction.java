package com.orange.goldgame.action;

import com.orange.goldgame.domain.PayOrder;

public interface PayOrderAction {

	int insertPayOrder(PayOrder order);

	PayOrder loadPayOrderById(int orderId);

	void updatePayOrder(PayOrder order);
}
