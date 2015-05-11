package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.Ranking;

public interface RankingAction {
	
	//根据rankType查询全部记录
	public List<Ranking> loadAllRankingByRankId(int rankTypeId);
	
	//查询有限条类型排行记录
	public List<Ranking> loadRankingByRankId(int rankTypeId,int count);
	
	public void insert(Ranking rank);
	
	public void update(Ranking rank);
	
	public void deleteForRankType(int rankTypeID);
	
	public boolean queryRank(int playerID);
	
	
	

}
