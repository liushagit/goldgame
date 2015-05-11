package com.orange.goldgame.system.ao;

import com.orange.goldgame.domain.Gift;

/**
 * 礼物AO类
 * @author Administrator
 *
 */
public class GiftAO extends BaseAO {

	/** 初始化礼物*/
	public void initializeGift(int playerId){
//		this.factory.getGiftDAO().insertGift(playerId);
	}
	
	/** 返回礼物列表*/
	public Gift getGift(int playerId){
		return null;
//		return this.factory.getGiftDAO().getGift(playerId);
	}
	
	/** 收到礼物时，更新礼物*/
	public void updateGift(int playerId,byte giftType){
		int flower=0;
		int egg=0;
		int car=0;
		int house=0;
		int houseboat=0;
		switch(giftType){
		case 1://花
			flower=1;
			break;
		case 2://金蛋
			egg=1;
			break;
		case 3://车
			car=1;
			break;
		case 4://房子
			house=1;
			break;
		case 5://豪车
			houseboat=1;
			break;
		}
		
//		this.factory.getGiftDAO().updateGift(playerId, flower, egg, car, house, houseboat);
	}
}
