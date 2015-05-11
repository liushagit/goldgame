package com.orange.goldgame.business.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author wuruihuang 2013.5.31
 * 一副扑克牌，可以设置是否启用大小王
 */
public class Poker {
    private Random rd = new Random();
	/**定义常量  "小王" "大王"*/
    private final static String[] VALUES = {"VICEJOKER","JOKER"};
    /** 2~A 一共有13张牌**/
    private static final int MAX_CARD_NUM = 13;
    /** 最大循环次数 **/
    private static final int MAX_RAND_NUM  = 10;
    private Map<Integer, Card> cards;
    private int cardSize;
    /**默认不使用大小王*/
    private boolean isUseJokers;
    /**默认每次发手牌的张数*/
    private int number = 3;
	/**记录每次发手牌的位置,默认位置是0*/
    
    public Poker(){
    	isUseJokers = false;
    	cards = new HashMap<Integer, Card>();
    }
    
    /**是否启用大小王*/
    public void setUseJokers(boolean isUseJokers) {
		this.isUseJokers = isUseJokers;
	}
    /**设置每次发手牌的张数*/
    public void setNumber(int number) {
		this.number = number;
	}
    /*初始化出一副扑克*/
    public void initPoker(){
        
        byte cardId = 0;
        Card c = null;
        for(CardColour colour : CardColour.values()){
            for(CardValue value : CardValue.values()){
                c = new Card(colour, value);
                c.setCardId(cardId++);
                cards.put((int)cardId, c);
            }
        }
        
        //如果使用大小王
        if(isUseJokers){
        	c = new Card(VALUES[0]);
        	c.setCardId(cardId++);
        	cards.put((int)c.getCardId(), c);
        	c = new Card(VALUES[1]);
        	c.setCardId(cardId++);
        	cards.put((int)c.getCardId(), c);
        }
        cardSize = cards.size();
    }
    
    
    
   //洗牌
    public List<Card> shuffle(){
    	return null;
    }
    
	/**重新设置整副扑克*/
	public void reset() {
		//重设不能明牌，未实用过
		for(Card card : cards.values()){
			card.setFront(false);
			card.setUsed(false);
		}
	}
	
	/**
	 * 获取指定数字牌
	 * @param number
	 * @return
	 */
	public Card getOneCardByNumber(int index){
		Card card = null;
		int i = 0;
		int res = 0;
		do {
			res = MAX_CARD_NUM * rd.nextInt(4) + index;//每张牌有四个花色
			card = cards.get(res);
			 i ++;
		} while (card == null || card.isUsed() && i <= MAX_RAND_NUM);
		if (card != null && card.isUsed()) {
			card = null;
		}
		if (card != null) {
			card.setUsed(true);
		}
		return card;
	}
	
	/**
	 * 获取指定花色
	 * 0方块，1梅花，2红桃，4黑桃
	 * @param color
	 * @return
	 */
	public Card getOneCardByColor(int color){
		Card card = null;
		int i = 0;
		int res = 0;
		do {
			res = color * MAX_CARD_NUM + rd.nextInt(MAX_CARD_NUM);
			card = cards.get(res);
			i ++;
		} while (card == null || (card.isUsed() && i <= MAX_RAND_NUM));
		if (card != null && card.isUsed()) {
			card = null;
		}
		if (card != null) {
			card.setUsed(true);
		}
		return card;
	}
	

	/**
	 * 检查是否能换牌
	 * @return
	 */
	public boolean checkPoker(){
		boolean isExchange = false;
		for(Card c : cards.values()){
			if(!c.isUsed()){
				isExchange = true;
				break;
			}
		}
		return isExchange;
	}
	
	
    /**从剩下的牌中随机获取一张*/
	public Card getOneCardRandom() {
		int index = 0;
		Card card = null;
		int res = 0;
		do {
			index = rd.nextInt(cardSize);
			card = cards.get(index);
			res ++;
		} while ((card == null || card.isUsed()) && res < 500);
		if (card.isUsed()) {
			card = null;
		}else {
		    card.setUsed(true);
        }
		return card;
	}

	 /**随机获取一份手牌*/
	public HandCards getHandCards() {
		HandCards handCards = new HandCards();
		Card card = null;
		for (int i = 0; i < number; i++) {
			card = getOneCardRandom();
			handCards.setCard(card,i);
		}
		return handCards;
	}
	
	public static void main(String[] args){
	    Poker poker = new Poker();
	    poker.initPoker();
        poker.shuffle();
	    Random rd = new Random();
	    for(int i=1;i<=1000;i++){
	        int type = rd.nextInt(5)+1;
//	        System.out.println(i+","+type);
	        HandCards hc = poker.getHandCards(type);
	        for(Card c : hc.getCards()){
	            if(c == null)
	                System.out.println("null");
	        }
	    }
	    
	    
	    
	}
	
	/**
	 * 获取一副手牌
	 * 1随机获取，2获取对子，3获取顺子，4获取同花，5获取同花顺，6获取三张
	 * @param type
	 * @return
	 */
	public HandCards getHandCards(int type){
		if (type == 1) {
			return getHandCards();
		}
		if (type == 2) {
			return getDouble(1);
		}
		if (type == 3) {
			return getStraight();
		}
		if (type == 4) {
			return getFlush();
		}
		if (type == 5) {
			return getStraightFlush();
		}
		if (type == 6) {
			return getDouble(0);
		}
		return null;
	}
	


	/**
	 * 获取一个对子,三张
	 * @param res 0代表三张，1代表对子
	 * @return
	 */
	private HandCards getDouble(int res) {
		List<Card> list = new ArrayList<Card>();
		Card card = null;
		int index = rd.nextInt(MAX_CARD_NUM);
		for (int i = 0; i < number - res; i++) {
			card = getOneCardByNumber(index);
			if (card != null) {
				list.add(card);
			}
		}
		checkNum(list);
		return getHandCards(list);
	}
	
	/**
	 * 获取一个顺子
	 * @return
	 */
	private HandCards getStraight(){
		List<Card> list = new ArrayList<Card>();
		Card card = null;
		int res = rd.nextInt(MAX_CARD_NUM - 2);
		for (int i = 0; i < number; i++) {
			card = getOneCardByNumber(res + i);
			if (card != null) {
				list.add(card);
			}
		}
		checkNum(list);
		return getHandCards(list);
	}
	
	/**
	 * 获取一个同花
	 * @return
	 */
	private HandCards getFlush(){
		int res = rd.nextInt(4);
		List<Card> list = new ArrayList<Card>();
		Card card = null;
		for (int i = 0; i < number; i++) {
			card = getOneCardByColor(res);
			if (card != null) {
				list.add(card);
			}
		}
		checkNum(list);
		return getHandCards(list);
		
	}
	
	/**
	 * 获取同花顺
	 * @return
	 */
	private HandCards getStraightFlush(){
		List<Card> list = new ArrayList<Card>();
		Card card = null;
		int index = 0;
		do {
			int res = rd.nextInt(4) * MAX_CARD_NUM + rd.nextInt(MAX_CARD_NUM - 2);
			list.clear();
			for (int i = 0; i < number; i++) {
				card = cards.get(res);
				if (card!= null && !card.isUsed()) {
					list.add(card);
				}
				res ++;
			}
			
			index ++;
		} while (list.size() < number && index < MAX_RAND_NUM);
		checkNum(list);
		return getHandCards(list);
	}
	
	/**
	 * 根据已有牌生成一副手牌
	 * @param list
	 * @return
	 */
	private HandCards getHandCards(List<Card> list){
		HandCards handCards = new HandCards();
		int index = 0;
		for (Card c : list) {
			handCards.setCard(c, index);
			index++;
		}
		return handCards;
	}
	
	/**
	 * 确保获取手牌足够
	 * @param list
	 */
	private void checkNum(List<Card> list){
		Card card = null;
		int list_size = list.size();
		while (list_size < number) {
			card = getOneCardRandom();
//			if(card==null){
//			    continue;
//			}
			list.add(card);
			list_size ++;
		}
	}
}
