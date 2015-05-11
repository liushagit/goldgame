package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.GetOnce;

public interface GetOnceAction {
	void insert(int playerId);
	GetOnce getGetOnce(int playerId,String dateKey);
	List<GetOnce> getAllGetOnce(int playerId,String dateKey);
	void modify(GetOnce getOnce);
	void insert(GetOnce getOnce);
}
