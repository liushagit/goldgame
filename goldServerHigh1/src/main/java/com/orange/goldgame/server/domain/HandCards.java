package com.orange.goldgame.server.domain;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.server.emun.CardColour;
import com.orange.goldgame.server.emun.CardValue;

/**
 * @author wuruihuang 2013.5.28
 * 玩家的手牌
 */
public class HandCards implements Comparable<HandCards>{
	
	private Card[] cards = new Card[3];
	
	private Logger log = LoggerFactory.getLogger(HandCards.class);
	
	public HandCards(){
	    
	}
	
	public int other_card = 1;
	public int duizi_card = 2;
	public int shunzi_card = 3;
	public int jinhua_card = 4;
	public int tonghuashun_card = 5;
	public int santiao_card = 6;
	public HandCards(Card[] cards){
	    this.cards = cards;
	}

    @Override
    public int compareTo(HandCards handCards) {
    	Card[] cardsa=cards;
    	Card[] cardsb=handCards.getCards();
    	Arrays.sort(cardsa); 
    	Arrays.sort(cardsb);
    	if(checkSameCol(cardsa,cardsb)){
    		return compareToSameColor(cardsa,cardsb);
    	}else{
    		int cf1 = parseValue(cards);
    		int cf2 = parseValue(handCards.getCards());
    		//有特殊牌和三条并存的情况
    		if(cf1==0 && cf2>60000){
    			return 1;
    		}
    		if(cf2==0 && cf1>60000){
    			return -1;
    		}
    		
    		//常规的情况
    		if(cf1 > cf2) return 1;
    		else return -1;  		
    	}
    }
    
    /**是否比较双方都为金花**/
    private boolean checkSameCol(Card[] cardsa,Card[] cardsb){
    	if(isSameColor(cardsa) && isSameColor(cardsb)){
    		if(!isSequence(cardsa) && !isSequence(cardsb)){
    			return true;
    		}
    	}
    	return false;
    }
    
    /**对金花进行比较**/
	private int compareToSameColor(Card[] cardsa, Card[] cardsb) {
		int result=-1;
		for(int index = cardsa.length-1;index >= 0;index--){
			System.out.println(cardsa[index].getCardNum() +"------"+index+"------"+cardsb[index].getCardNum());
			if(cardsa[index].getCardNum()>cardsb[index].getCardNum()){
				return 1;
			}
			if(cardsa[index].getCardNum()<cardsb[index].getCardNum()){
				return -1;
			}
		}
		return result;
	}
	
	/**
     * 
     * 是否是成对
     * */
    public boolean isDouble(Card[] cards){
        if(cards==null || cards.length<3) return false;
        //如果只有两张牌相同，则返回true
        if((cards[0].getCardNum()==cards[1].getCardNum() 
            || cards[1].getCardNum()==cards[2].getCardNum())
                &&
            cards[0].getCardNum()!=cards[2].getCardNum()){
            log.debug("对子");
            return true;
        }
        return false;
    }
    
    /**
     * 是否成三条
     * @param cards
     * @return
     */
    public boolean isThreeble(Card[] cards){
        if(cards==null || cards.length<3) return false;
        
        if(cards[0].getCardNum()==cards[1].getCardNum()
           &&cards[1].getCardNum()==cards[2].getCardNum()
           &&cards[0].getCardNum()==cards[2].getCardNum()){
            
            return true;
        }
        
        return false;
        
    }
    
    /**
     * 是否是顺子
     * @param cards 如果个数小于3会直接返回false
     * @return
     */
    public boolean isFrequence(Card[] cards){
        if(cards==null || cards.length<3) return false;
        
        //123的情况
        if(cards[0].getCardNum()==2 && cards[1].getCardNum()==3 && cards[2].getCardNum()==14){
            return true;
        }
        if(cards[2].getCardNum()-cards[1].getCardNum()==1 
                && cards[1].getCardNum()-cards[0].getCardNum()==1){
            
            return true;
        }
        return false;
    }
    
    
    /**
     * 是否是同花
     * @param cards 如果个数小于3会直接返回false
     * @return
     */
    public boolean isSameColor(Card[] cards){
        if(cards==null || cards.length<3) return false;
        
        if(cards[0].getCardColour() == cards[1].getCardColour()
           && 
           cards[1].getCardColour() == cards[2].getCardColour()
           &&
           cards[0].getCardColour() == cards[2].getCardColour()){
            
            return true;
            
        }
        return false;
    }
    
    /**
     * 是否是特殊牌 ：235
     * @param cards
     * @return
     */
    public boolean isSpecial(Card[] cards){
        if(cards==null || cards.length<3) return false;
        
        if(cards[0].getCardColour()!=cards[1].getCardColour()
                &&cards[1].getCardColour()!=cards[2].getCardColour()
                &&cards[0].getCardColour()!=cards[2].getCardColour()){
            
            if(cards[0].getCardValue()==CardValue.TWO
                    && cards[1].getCardValue()==CardValue.THREE
                    && cards[2].getCardValue()==CardValue.FIVE){
                return true;
            }
            
        }
        return false;
        
    }
    
    /**
     * 是否是同花顺
     * @return
     */
    public boolean isSequence(Card[] cards){
        if(cards==null || cards.length<3) return false;
        
        boolean b1 = false;
        boolean b2 = false;
        
        //判断是否为同花
        if(cards[0].getCardColour() == cards[1].getCardColour()
            && 
            cards[1].getCardColour() == cards[2].getCardColour()
            &&
            cards[0].getCardColour() == cards[2].getCardColour()){
             
             b1 = true;
             
        }
        //判断是否为顺子
        if(cards[2].getCardNum()-cards[1].getCardNum()==1 
                && cards[1].getCardNum()-cards[0].getCardNum()==1){
            b2 = true;
        }
        
        return b1&&b2;
    }
    
    
    private int parseValue(Card[] cards){
        
        Arrays.sort(cards);
        
        int value = 0;
        //如果是三条
        if(isThreeble(cards)){
            value = 60000+cards[0].getCardNum()*60;
        }
        //如果是同花顺
        else if(isSequence(cards)){
            value = 50000+cards[1].getCardNum()*60;
        }
        //如果是金花
        else if(isSameColor(cards)){
            value = 40000+cards[2].getCardNum()*60;
        }
        //如果是顺子
        else if(isFrequence(cards)){
            value = 30000+cards[1].getCardNum()*60;
        }
        //如果是对子
        else if(isDouble(cards)){
            value = 20000+cards[1].getCardNum()*60+cards[2].getCardNum();
        }
        //如果是特殊牌
        else if(isSpecial(cards)){
            value = 0;
        }
        //否则就是散牌
        else{
            value = 10000+cards[2].getCardNum()*300+cards[1].getCardNum()*20+cards[0].getCardNum();
        }
        
        return value;
    }
    
    public static void main(String[] args){
//    	compareHandCard|352350|[DIAMOND,4, DIAMOND,11, DIAMOND,13]|209772|[HEARTS,6, HEARTS,9, HEARTS,11]|352350
        Card[] cardsa = new Card[3];
        Card a1 = new Card(CardColour.DIAMOND,CardValue.KING);
        Card a2 = new Card(CardColour.DIAMOND,CardValue.NINE);
        Card a3 = new Card(CardColour.DIAMOND,CardValue.JACK);
        
        cardsa[0] = a1;
        cardsa[1] = a2;
        cardsa[2] = a3;
        
        Card[] cardsb = new Card[3];
        Card b1 = new Card(CardColour.HEARTS,CardValue.JACK);
        Card b2 = new Card(CardColour.HEARTS,CardValue.KING);
        Card b3 = new Card(CardColour.HEARTS,CardValue.NINE);
        cardsb[0] = b1;
        cardsb[1] = b2;
        cardsb[2] = b3;
        HandCards hc1 = new HandCards(cardsa);
        HandCards hc2 = new HandCards(cardsb);
        int i = hc1.compareTo(hc2);
        System.out.println(i);
    }
	
	public Card getCard1() {
		return cards[0];
	}
	public void setCard(Card card,int index) {
	    cards[index] = card;
	}
	public void setCard1(Card card1) {
	    cards[0] = card1;
	}
	public Card getCard2() {
		return cards[1];
	}
	public void setCard2(Card card2) {
	    cards[1] = card2;
	}
	public Card getCard3() {
		return cards[2];
	}
	public void setCard3(Card card3) {
	    cards[2] = card3;
	}

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }
}
