package com.orange.goldgame.server.domain;

import java.util.HashMap;
import java.util.Map;

import com.orange.goldgame.server.emun.CardColour;
import com.orange.goldgame.server.emun.CardValue;


/**
 * @author wuruihuang 2013.5.31
 * 一副扑克牌中的一张牌
 */
public class Card implements Comparable<Card>{
	
	private byte cardId;
	/**表示大小王   "JOKER" "VICEJOKER" */
	private String value;

	/**是大小王的牌时此成员变量作废*/
	private CardColour cardColour;
	private CardValue cardValue;
	private int cardNum;
	
	/**是否可以明牌,默认是不能明牌*/
	private boolean isFront = false;
	
	/**是否被使用过了,默认是没有被使用过的牌*/
	private boolean isUsed = false;
	/**是否被换过,默认是没有被使换过的牌*/
	private boolean change = false;
	
	/**牌与值对应*/
	private static Map<CardValue,Integer> cardValueMap = new HashMap<CardValue,Integer>();
	static{
	    cardValueMap.put(CardValue.TWO, 2);
	    cardValueMap.put(CardValue.THREE, 3);
	    cardValueMap.put(CardValue.FOUR, 4);
	    cardValueMap.put(CardValue.FIVE, 5);
	    cardValueMap.put(CardValue.SIX, 6);
	    cardValueMap.put(CardValue.SEVEN, 7);
	    cardValueMap.put(CardValue.EIGHT, 8);
	    cardValueMap.put(CardValue.NINE, 9);
	    cardValueMap.put(CardValue.TEN, 10);
	    cardValueMap.put(CardValue.JACK, 11);
	    cardValueMap.put(CardValue.QUEEN, 12);
	    cardValueMap.put(CardValue.KING, 13);
        cardValueMap.put(CardValue.ACE, 14);
	}
	
	/**提供构造除大小王之外的牌*/
	public Card(CardColour colour, CardValue value) {
		this.cardColour = colour;
		this.cardValue = value;
		this.cardNum = cardValueMap.get(value);
	}
	
	/**提供构造大小王的牌*/
	public Card(String isJoker) {
		this.value = isJoker;
		this.cardColour = null;
		this.cardValue = null;
		this.cardNum = 0;
	}
	
	public byte getCardId() {
		return cardId;
	}

	public void setCardId(byte cardId) {
		this.cardId = cardId;
	}
	
	public boolean isFront() {
		return isFront;
	}

	public void setFront(boolean isFront) {
		this.isFront = isFront;
	}
	
	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	public String getValue() {
		return value;
	}
	
	public CardColour getCardColour() {
		return cardColour;
	}

	public CardValue getCardValue() {
		return cardValue;
	}

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    @Override
    public int compareTo(Card c) {
        if(this.cardNum>c.getCardNum()){
            return 1;
        }
        if(this.cardNum==c.getCardNum()){
            return 0;
        }
        return -1;
    }
    
    public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}
    @Override
    public String toString() {
        return this.getCardColour()+","+this.getCardNum();
    }
}
