package com.orange.goldgame.server.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;

import com.orange.goldgame.server.emun.CardValue;
//import org.apache.log4j.Logger;
//import com.juice.orange.game.log.LoggerFactory;
import com.orange.goldgame.server.emun.CardColour;

/**
 * @author wuruihuang 2013.5.28
 * 玩家的手牌
 */
public class HandCards implements Comparable<HandCards>{
	
//	private Logger logger=LoggerFactory.getLogger(HandCards.class);
	
	private Card[] cards = new Card[3];
	
//	private Logger log = LoggerFactory.getLogger(HandCards.class);
	
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
		for(int index=cardsa.length-1;index>=0;index--){
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
//            log.debug("对子");
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
    	//红桃AK4和梅花A24比牌，红桃AK4输了
    	try {
		    Card[] cardsa = new Card[3];
		    Card[] cardsb = new Card[3];
		    System.out.println("数字表示：数字1表示方可  2表示梅花   3表示红桃   4表示黑桃    2~14分别代表2~Ace");
		    System.out.println("输入的手牌格式:花色|牌值  花色|牌值  花色|牌值   ->花色与牌值用"+"|"+"+分开,卡牌与卡牌用空格分开");
		    BufferedReader strin1 = new BufferedReader(new InputStreamReader(System.in));
			String hCard1=strin1.readLine();
			
			BufferedReader strin2 = new BufferedReader(new InputStreamReader(System.in));
			String hCard2=strin2.readLine();
			
			String[] cara=hCard1.split(" ");
		    Card a1 = getAssignCard(cara[0]);
		    Card a2 = getAssignCard(cara[1]);
		    Card a3 = getAssignCard(cara[2]);
		    
		    cardsa[0] = a1;
		    cardsa[1] = a2;
		    cardsa[2] = a3;
		    
		    String[] carb=hCard2.split(" ");
		    Card b1 = getAssignCard(carb[0]);
		    Card b2 = getAssignCard(carb[1]);
		    Card b3 = getAssignCard(carb[2]);
		    cardsb[0] = b1;
		    cardsb[1] = b2;
		    cardsb[2] = b3;
		    HandCards hc1 = new HandCards(cardsa);
		    HandCards hc2 = new HandCards(cardsb);
		    int i = hc1.compareTo(hc2);
		    String top=i>0?"胜利":"失败";
		    System.out.println(top);
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
//        System.out.println("num1:"+cardsa[2].getCardNum()+" num2:"+cardsb[2].getCardNum()+" reault:"+i);
    }
    static CardColour[] cols={CardColour.DIAMOND,CardColour.PLUM,CardColour.HEARTS,CardColour.SPADES};
    private static Card getAssignCard(String str){
    	String[] card=str.split("\\|");
    	CardColour cColour=null;
    	CardValue cValue=null;
    	for(int i=0;i<card.length;i++){
    		if(i==0){
    			int index=Integer.parseInt(card[0])-1;
    			cColour=cols[index];
    		}
    		if(i==1){
    			cValue=Card.getCardValue(Integer.parseInt(card[1]));
    		}
    	}
    	return new Card(cColour,cValue);
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
