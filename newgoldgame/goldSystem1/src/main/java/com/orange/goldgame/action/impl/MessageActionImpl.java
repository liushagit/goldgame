package com.orange.goldgame.action.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.action.MessageAction;
import com.orange.goldgame.system.Application;
import com.orange.goldgame.system.ao.MessageAO;
import com.orange.goldgame.vo.ExchangeRecordVo;
import com.orange.goldgame.vo.MessageVo;

/**
 * @author wuruihuang 2013.6.21
 *
 */
public class MessageActionImpl implements MessageAction {
	private static final Logger logger = LoggerFactory.getLogger(MessageActionImpl.class);
    @Override
    public List<String> loadSystemMessage() {
    	List<String> message = new ArrayList<String>();
    	MessageAO messageAo = Application.getMessgaeAO();
    	message = messageAo.getSystemMessage();
        return message;
    }

	@Override
	public List<MessageVo> loadFriendsMessage(int playerId) {
		List<MessageVo> message = new ArrayList<MessageVo>();
    	message = Application.getMessgaeAO().getFriendsMessage(playerId);
        return message;
	}

	@Override
	public List<ExchangeRecordVo> loadGoodsExchangeMessage(int playerId) {
		List<ExchangeRecordVo> message = new ArrayList<ExchangeRecordVo>();
    	message = Application.getMessgaeAO().getGoodsExchangeMessage(playerId);
        return message;
	}

}
