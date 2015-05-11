package com.orange.goldgame.server.engine;

import java.util.Arrays;

import com.orange.goldgame.server.domain.Card;
import com.orange.goldgame.server.emun.CardColour;
import com.orange.goldgame.server.emun.CardValue;




public class GameLogicEngine {
    
    /**
     * 
     * 是否是成对
     * */
    public static boolean isDouble(Card[] cards){
        if(cards==null || cards.length<3) return false;
        Arrays.sort(cards);
        //如果只有两张牌相同，则返回true
        if((cards[0].getCardNum()==cards[1].getCardNum() 
            || cards[1].getCardNum()==cards[2].getCardNum())
                &&
            cards[0].getCardNum()!=cards[2].getCardNum()){
            return true;
        }
        return false;
    }
    
    /**
     * 是否成三条
     * @param cards
     * @return
     */
    public static boolean isThreeble(Card[] cards){
        if(cards==null || cards.length<3) return false;
        Arrays.sort(cards);
        
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
    public static boolean isFrequence(Card[] cards){
        if(cards==null || cards.length<3) return false;
        Arrays.sort(cards);
        
        //123的情况
        if(cards[0].getCardNum()==2 && cards[0].getCardNum()==3 && cards[0].getCardNum()==14){
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
    public static boolean isSameColor(Card[] cards){
        if(cards==null || cards.length<3) return false;
        Arrays.sort(cards);
        
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
    public static boolean isSpecial(Card[] cards){
        if(cards==null || cards.length<3) return false;
        Arrays.sort(cards);
        
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
    public static boolean isSequence(Card[] cards){
        if(cards==null || cards.length<3) return false;
        Arrays.sort(cards);
        
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
    
    /**
     * 两手牌的比较
     * @param cards1
     * @param cards2
     * @return 
     *          cards1>cards2 return 1
     *          cards1=cards2 return 0
     *          cards1<cards2 return -1
     */
    public static int compare(Card[] cards1,Card[] cards2){
        int cf1 = parseValue(cards1);
        int cf2 = parseValue(cards2);
        
        //有特殊牌和三条并存的情况
        if(cf1==0 && cf2>6000){
            return 1;
        }
        if(cf2==0 && cf1>6000){
            return -1;
        }
        
        //常规的情况
        if(cf1 > cf2) return 1;
        if(cf1 == cf2) return 0;
        else return -1;
        
    }
    
    private static int parseValue(Card[] cards){
        int value = 0;
        //如果是三条
        if(isThreeble(cards)){
            value = 6000+cards[0].getCardNum();
        }
        //如果是同花顺
        else if(isSequence(cards)){
            value = 5000+cards[1].getCardNum();
        }
        //如果是金花
        else if(isSameColor(cards)){
            value = 4000+cards[2].getCardNum();
        }
        //如果是顺子
        else if(isFrequence(cards)){
            value = 3000+cards[1].getCardNum();
        }
        //如果是对子
        else if(isDouble(cards)){
            value = 2000+cards[1].getCardNum()*100+cards[2].getCardNum();
        }
        //如果是特殊牌
        else if(isSpecial(cards)){
            value = 0;
        }
        //否则就是散牌
        else{
            value = 1000+cards[0].getCardNum()+cards[1].getCardNum()+cards[2].getCardNum();
        }
        
        return value;
    }
    
    public static void main(String[] args){
        Card[] cardsa = new Card[3];
        Card a1 = new Card(CardColour.DIAMOND,CardValue.TEN);
        Card a2 = new Card(CardColour.SPADES,CardValue.TEN);
        Card a3 = new Card(CardColour.HEARTS,CardValue.TEN);
        cardsa[0] = a1;
        cardsa[1] = a2;
        cardsa[2] = a3;
        
        Card[] cardsb = new Card[3];
        Card b1 = new Card(CardColour.DIAMOND,CardValue.FIVE);
        Card b2 = new Card(CardColour.HEARTS,CardValue.ACE);
        Card b3 = new Card(CardColour.SPADES,CardValue.THREE);
        cardsb[0] = b1;
        cardsb[1] = b2;
        cardsb[2] = b3;
        
        int i = GameLogicEngine.compare(cardsa, cardsb);
        System.out.println(i);
    }
    
    
}
