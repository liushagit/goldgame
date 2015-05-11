package com.orange.goldgame.system.ao;

import java.util.ArrayList;
import java.util.List;

import com.orange.goldgame.domain.ExchangeRecord;
import com.orange.goldgame.domain.Message;
import com.orange.goldgame.system.Application;
import com.orange.goldgame.system.DAOFactory;
import com.orange.goldgame.vo.ExchangeRecordVo;
import com.orange.goldgame.vo.MessageVo;

/**
 * @author wuruihuang 2013.6.21
 *
 */
public class MessageAO {
	private DAOFactory factory;
	
	public MessageAO() {
		this.factory = Application.getDAOFactory();
	}

	public List<String> getSystemMessage() {
		return factory.getMessageDAO().getSystemMessage();
	}

	/**
	 * @param playerId
	 * @return
	 */
	public List<MessageVo> getFriendsMessage(int playerId) {
		List<MessageVo> messageVoList = new ArrayList<MessageVo>();
		List<Message> messages = factory.getMessageDAO().getFriendsMessage(playerId);
		for(Message mes:messages){
			MessageVo messageVo = new MessageVo();
			messageVo.setMessage(mes);
			messageVoList.add(messageVo);
		}
		return messageVoList;
	}

	public List<ExchangeRecordVo> getGoodsExchangeMessage(int playerId) {
		List<ExchangeRecordVo> exchangeRecordVoList = new ArrayList<ExchangeRecordVo>();
		List<ExchangeRecord> exchangeMessages = factory.getMessageDAO().getGoodsExchangeMessage(playerId);
		for(ExchangeRecord record:exchangeMessages){
			ExchangeRecordVo exchangeRecordVo = new ExchangeRecordVo();
			exchangeRecordVo.setRecordMessage(record);
			//GoodsVo goods = factory.getGoodsDAO().getGoodsById(record.getGoodsId());
//			exchangeRecordVo.setGoodsIntr(goods.getIntro());
			exchangeRecordVoList.add(exchangeRecordVo);
		}
		return exchangeRecordVoList;
	}
}
