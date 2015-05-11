package com.orange.goldgame.server.pay;

import java.util.Date;

import com.juice.orange.game.exception.JuiceException;
import com.juice.orange.game.handler.SocketResponse;
import com.juice.orange.game.util.OutputMessage;
import com.orange.goldgame.core.ErrorCode;
import com.orange.goldgame.domain.PayInfo;
import com.orange.goldgame.domain.PlayerPay;
import com.orange.goldgame.protocol.Protocol;
import com.orange.goldgame.server.engine.BaseEngine;
import com.orange.goldgame.server.service.PayService;

public class MMPayService {

	/**
	 * MM充值
	 * 
	 * @param request
	 * @param response
	 * @throws JuiceException
	 */
	public static ErrorCode mmPay(String mminfo, SocketResponse response) {
		String infos[] = mminfo.split("_");
		if (infos == null || infos.length <= 0) {
			OutputMessage out = new OutputMessage();
			out.putString("success");
			response.sendMessage(Protocol.RESPONSE_MOBILEMM, out);
			return new ErrorCode(-1, "");
		}

		String appId = infos[0];
		if (!PayService.APP_ID.equals(appId)) {
			return new ErrorCode(-1, "id错误");
		}
		int playerId = Integer.parseInt(infos[1]);
		String payId = infos[2];
		String orderId = infos[3];
		String channelId = infos[4];

		if (playerId != 386281) {
			return new ErrorCode(-1, "测试阶段需要白名单");
		}

		PlayerPay playerPay = BaseEngine.getInstace().getPlayerPayAction()
				.quaryPlayerPay(playerId, orderId);
		if (playerPay != null) {
			return new ErrorCode(-1, "订单已经处理");
		}

		playerPay = new PlayerPay();
		playerPay.setOrderId(orderId);
		playerPay.setPayTime(new Date());
		playerPay.setPlayerId(playerId);
		PayInfo pi = PayInfoService.getPayInfo(payId);
		playerPay.setPayInfoId(pi.getPayId());
		playerPay.setGold(pi.getMoney());
		playerPay.setPayType(pi.getPayType() + "");
		playerPay.setTradeId("");
		playerPay.setBuyId("");
		return new ErrorCode(ErrorCode.SUCC, playerPay);

	}
}
