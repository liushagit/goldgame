package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.vo.ExchangeRecordVo;
import com.orange.goldgame.vo.MessageVo;


public interface MessageAction {
	List<String> loadSystemMessage();

	List<MessageVo> loadFriendsMessage(int playerId);

	List<ExchangeRecordVo> loadGoodsExchangeMessage(int playerId);
}
