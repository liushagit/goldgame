package com.orange.goldgame.server.util;

import com.orange.goldgame.server.domain.Card;
import com.orange.goldgame.server.domain.HandCards;
import com.orange.goldgame.server.emun.CardColour;
import com.orange.goldgame.server.emun.CardValue;


public class CardConvertor {
	 /** 大小王  "JOKER" "VICEJOKER" */  
	 /** 红桃              黑桃           方块              梅花 */  
	 /** HEARTS,  SPADES, DIAMOND, PLUM */
	 /**  "A",    "2",   "3",    "4",   "5",    "6",  "7",    "8",    "9",   "10", "J",   "Q",   "K"   */
	 /**  ACE,    TWO,   THREE,  FOUR,  FIVE,   SIX,  SEVEN,  EIGHT,  NINE,  TEN,  JACK,  QUEEN,  KING8*/

    /**A  2     3     4     5     6     7     8     9     10    J     Q     K         
	"1",  "2",  "3",  "4",  "5",  "6",  "7",  "8",  "9",  "10", "11", "12", "13"//方块
	"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26"//梅花
	"27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39"//红心
	"40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52"//黑扇
	"53","54",//小王,大王
	*/
	public static Byte converterToCardId(Card card) {
		byte cardValue = 0;
		if(card.getValue()=="JOKER"){
			//大王
			cardValue = (byte)54;
		}else if(card.getValue()=="VICEJOKER"){
			//小王
			cardValue = (byte)53;
		}else if(card.getValue()==null&&card.getCardColour()==CardColour.DIAMOND){
			//方块
			if(card.getCardValue()==CardValue.ACE){
				cardValue = (byte)1;
			}else if(card.getCardValue()==CardValue.TWO){
				cardValue = (byte)2;
			}else if(card.getCardValue()==CardValue.THREE){
				cardValue = (byte)3;
			}else if(card.getCardValue()==CardValue.FOUR){
				cardValue = (byte)4;
			}else if(card.getCardValue()==CardValue.FIVE){
				cardValue = (byte)5;
			}else if(card.getCardValue()==CardValue.SIX){
				cardValue = (byte)6;
			}else if(card.getCardValue()==CardValue.SEVEN){
				cardValue = (byte)7;
			}else if(card.getCardValue()==CardValue.EIGHT){
				cardValue = (byte)8;
			}else if(card.getCardValue()==CardValue.NINE){
				cardValue = (byte)9;
			}else if(card.getCardValue()==CardValue.TEN){
				cardValue = (byte)10;
			}else if(card.getCardValue()==CardValue.JACK){
				cardValue = (byte)11;
			}else if(card.getCardValue()==CardValue.QUEEN){
				cardValue = (byte)12;
			}else if(card.getCardValue()==CardValue.KING){
				cardValue = (byte)13;
			}
		}else if(card.getValue()==null&&card.getCardColour()==CardColour.PLUM){
			//梅花
			if(card.getCardValue()==CardValue.ACE){
				cardValue = (byte)14;
			}else if(card.getCardValue()==CardValue.TWO){
				cardValue = (byte)15;
			}else if(card.getCardValue()==CardValue.THREE){
				cardValue = (byte)16;
			}else if(card.getCardValue()==CardValue.FOUR){
				cardValue = (byte)17;
			}else if(card.getCardValue()==CardValue.FIVE){
				cardValue = (byte)18;
			}else if(card.getCardValue()==CardValue.SIX){
				cardValue = (byte)19;
			}else if(card.getCardValue()==CardValue.SEVEN){
				cardValue = (byte)20;
			}else if(card.getCardValue()==CardValue.EIGHT){
				cardValue = (byte)21;
			}else if(card.getCardValue()==CardValue.NINE){
				cardValue = (byte)22;
			}else if(card.getCardValue()==CardValue.TEN){
				cardValue = (byte)23;
			}else if(card.getCardValue()==CardValue.JACK){
				cardValue = (byte)24;
			}else if(card.getCardValue()==CardValue.QUEEN){
				cardValue = (byte)25;
			}else if(card.getCardValue()==CardValue.KING){
				cardValue = (byte)26;
			}
		}else if(card.getValue()==null&&card.getCardColour()==CardColour.HEARTS){
			//红心
			if(card.getCardValue()==CardValue.ACE){
				cardValue = (byte)27;
			}else if(card.getCardValue()==CardValue.TWO){
				cardValue = (byte)28;
			}else if(card.getCardValue()==CardValue.THREE){
				cardValue = (byte)29;
			}else if(card.getCardValue()==CardValue.FOUR){
				cardValue = (byte)30;
			}else if(card.getCardValue()==CardValue.FIVE){
				cardValue = (byte)31;
			}else if(card.getCardValue()==CardValue.SIX){
				cardValue = (byte)32;
			}else if(card.getCardValue()==CardValue.SEVEN){
				cardValue = (byte)33;
			}else if(card.getCardValue()==CardValue.EIGHT){
				cardValue = (byte)34;
			}else if(card.getCardValue()==CardValue.NINE){
				cardValue = (byte)35;
			}else if(card.getCardValue()==CardValue.TEN){
				cardValue = (byte)36;
			}else if(card.getCardValue()==CardValue.JACK){
				cardValue = (byte)37;
			}else if(card.getCardValue()==CardValue.QUEEN){
				cardValue = (byte)38;
			}else if(card.getCardValue()==CardValue.KING){
				cardValue = (byte)39;
			}
		}else if(card.getValue()==null&&card.getCardColour()==CardColour.SPADES){
			//黑扇
			if(card.getCardValue()==CardValue.ACE){
				cardValue = (byte)40;
			}else if(card.getCardValue()==CardValue.TWO){
				cardValue = (byte)41;
			}else if(card.getCardValue()==CardValue.THREE){
				cardValue = (byte)42;
			}else if(card.getCardValue()==CardValue.FOUR){
				cardValue = (byte)43;
			}else if(card.getCardValue()==CardValue.FIVE){
				cardValue = (byte)44;
			}else if(card.getCardValue()==CardValue.SIX){
				cardValue = (byte)45;
			}else if(card.getCardValue()==CardValue.SEVEN){
				cardValue = (byte)46;
			}else if(card.getCardValue()==CardValue.EIGHT){
				cardValue = (byte)47;
			}else if(card.getCardValue()==CardValue.NINE){
				cardValue = (byte)48;
			}else if(card.getCardValue()==CardValue.TEN){
				cardValue = (byte)49;
			}else if(card.getCardValue()==CardValue.JACK){
				cardValue = (byte)50;
			}else if(card.getCardValue()==CardValue.QUEEN){
				cardValue = (byte)51;
			}else if(card.getCardValue()==CardValue.KING){
				cardValue = (byte)52;
			}
		}
		return cardValue;
	}

	public static byte convertToCardValue(CardValue cardValue) {
		byte value = (byte)1;
		if(cardValue==CardValue.ACE){
			value = (byte)14;
		}else if(cardValue==CardValue.TWO){
			value = (byte)2;
		}else if(cardValue==CardValue.THREE){
			value = (byte)3;
		}else if(cardValue==CardValue.FOUR){
			value = (byte)4;
		}else if(cardValue==CardValue.FIVE){
			value = (byte)5;
		}else if(cardValue==CardValue.SIX){
			value = (byte)6;
		}else if(cardValue==CardValue.SEVEN){
			value = (byte)7;
		}else if(cardValue==CardValue.EIGHT){
			value = (byte)8;
		}else if(cardValue==CardValue.NINE){
			value = (byte)9;
		}else if(cardValue==CardValue.TEN){
			value = (byte)10;
		}else if(cardValue==CardValue.JACK){
			value = (byte)11;
		}else if(cardValue==CardValue.QUEEN){
			value = (byte)12;
		}else if(cardValue==CardValue.KING){
			value = (byte)13;
		}
		return value;
	}
	
	public static byte convertToCardColour(CardColour cardColour) {
		byte colour = (byte)1;
		if(cardColour==CardColour.DIAMOND){
			colour = (byte)2;
		}else if(cardColour==CardColour.HEARTS){
			colour = (byte)3;
		}else if(cardColour==CardColour.PLUM){
			colour = (byte)4;
		}else if(cardColour==CardColour.SPADES){
			colour = (byte)5;
		}
		return colour;
	}
	
	/**
	 * 转换玩家手牌的牌型
	 * @param handCards
	 * @return
	 */
	public static byte convertToCardType(HandCards handCards) {
		/**
		 * 散牌：单张 1
		 * 成对: 对子 2 ---有特殊情况
		 * 成顺子：顺子或拖拉机 3
		 * 成花色：金花 4 
		 * 既成顺子又成花色：顺金 5
		 * 成三条：豹子或炸弹 6
		 * 花色不同的235：特殊牌 7 ----有特殊情况
		 */
		//默认玩家手牌的牌型是单张，所以不用对玩家手牌进行是否是单张的判断
		byte cardType = (byte)1;
		boolean isJinhua = false;
		boolean isShunzi = false;
		Card card1 = handCards.getCard1();
		Card card2 = handCards.getCard1();
		Card card3 = handCards.getCard1();
		//判断是否是特殊牌
		if(CardConvertor.convertToCardValue(card1.getCardValue())==2||CardConvertor.convertToCardValue(card1.getCardValue())==3||CardConvertor.convertToCardValue(card1.getCardValue())==5){
			if(CardConvertor.convertToCardValue(card2.getCardValue())==2||CardConvertor.convertToCardValue(card2.getCardValue())==3||CardConvertor.convertToCardValue(card2.getCardValue())==5){
				if(CardConvertor.convertToCardValue(card3.getCardValue())==2||CardConvertor.convertToCardValue(card3.getCardValue())==3||CardConvertor.convertToCardValue(card3.getCardValue())==5){
					cardType = (byte)7;
				}
			}
		}
		//判断是否是炸弹
		if(cardType != (byte)7){
			if(card1.getCardValue()==card2.getCardValue()&&card2.getCardValue()==card3.getCardValue()&&card1.getCardValue()==card3.getCardValue()){
				cardType = (byte)6;
			}
		}
		
		
		//判断是否是金花
		if(cardType != (byte)7||cardType != (byte)6){
			if(card1.getCardColour()==card2.getCardColour()&&card1.getCardColour()==card3.getCardColour()&&card2.getCardColour()==card3.getCardColour()){
				isJinhua = true;
				cardType = (byte)4;
			}
		}
		
		//判断是否是顺子
		if(cardType != (byte)7||cardType != (byte)6){
			if(card1.getCardColour()==card2.getCardColour()&&card1.getCardColour()==card3.getCardColour()&&card2.getCardColour()==card3.getCardColour()){
				isShunzi = true;
				cardType = (byte)3;
			}
		}
		
		
		//判断是否是对子,或者是顺金
		if(cardType==(byte)1){
			if(card1.getCardValue()==card2.getCardValue()||card2.getCardValue()==card3.getCardValue()||card1.getCardValue()==card3.getCardValue()){
				cardType = (byte)2;
			}else{
				//判断是否是顺金
				if(isShunzi&&isJinhua){
					cardType = (byte)5;
				}
			}
		}
		return cardType;
	}

}
