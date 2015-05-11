package com.orange.goldgame.server.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orange.goldgame.server.domain.Card;
import com.orange.goldgame.server.domain.Gamer;
import com.orange.goldgame.server.domain.Poker;



/**
 * 
 * @author wuruihuang 2013.5.28
 * 管理房间的扑克poker
 */
public class CardManager {
	private static CardManager cardManager;
	
	/** 第一个参数指的是房间的id,第二个参数指房间内的一副扑克牌*/
	private Map<String, Poker> pokersMap;
	
	private CardManager(){
		this.pokersMap = new HashMap<String, Poker>();
	}
	
	public static CardManager getInstance(){
		if(cardManager==null){
			cardManager = new CardManager();
		}
		return cardManager;
	}
	
	public Map<String, Poker> getPokersMap() {
		return pokersMap;
	}

	/**
	 * 系统随机发牌
	 * @param roomId
	 * @param playerIds
	 */
	public void dispacherCards(String roomId, List<Integer> playerIds) {
		//系统随机洗牌
		Poker poker = pokersMap.get(roomId);
		poker.shuffle();
		//按照系统随机洗牌之后的顺序依次进行发牌
		for(Integer playerId:playerIds){
			Gamer gamer = GamerManager.getInstance().getGamersMap().get(roomId).get(playerId);
			gamer.setHandCards(poker.getHandCards());
		}
	}
	
	/**
	 * 改变手牌其中的一张
	 * @param roomId
	 * @param playerId
	 * @param cardIndex
	 * @param cardId
	 */
	public Card changePlayerSingleCard(String roomId, int playerId,byte cardId) {
		//获取另一张牌
		Poker poker = pokersMap.get(roomId);
		Card exchangeCard = poker.getOneCardRandom();
		Map<Integer,Gamer> gamerMap = GamerManager.getInstance().getGamersMap().get(roomId);
		Gamer gamer = gamerMap.get(playerId);
		//玩家换牌
		gamer.exchangeCard(gamer,cardId,exchangeCard);
		return exchangeCard;
	}
	/*
	
	*//**
	 * 比牌，判断玩家输赢
	 * @param activeGamer
	 * @param passiveGamer
	 * @return
	 *//*
	public int compareCards(Gamer activeGamer, Gamer passiveGamer) {
		int winId = 0;
		HandCards activeHandCards = activeGamer.getHandCards();
		HandCards passiveHandCards = passiveGamer.getHandCards();
		
		HandCards winHandCards = activeHandCards.Comparetor(passiveHandCards);
		
		if(winHandCards.equals(activeHandCards)){
			winId = activeGamer.getPlayerId();
		}else if(winHandCards.equals(passiveHandCards)){
			winId = passiveGamer.getPlayerId();
		}
		return winId;
	}*/
	
	public void clear(String roomId) {
		this.pokersMap.remove(roomId);
	}
}
