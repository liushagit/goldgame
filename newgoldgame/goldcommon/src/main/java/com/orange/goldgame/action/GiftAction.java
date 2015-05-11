package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.Gift;

public interface GiftAction {
	/** 查询礼物*/
	public List<Gift> findGiftByPlayerId(int playerId);
	
	/**更新礼物*/
	public void update(Gift gift);
	
	public Gift findGiftByPlayerIdAndPropId(int playerId,int propId);
	
	public Gift initGift(int playerId,int propId);
}
