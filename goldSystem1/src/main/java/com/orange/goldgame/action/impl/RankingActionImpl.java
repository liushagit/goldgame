package com.orange.goldgame.action.impl;

import java.util.ArrayList;
import java.util.List;

import com.orange.goldgame.action.RankingAction;
import com.orange.goldgame.domain.Ranking;
import com.orange.goldgame.system.DBManager;
import com.orange.goldgame.system.dao.RankingDAO;
import com.orange.goldgame.system.dao.RankingDAOImpl;

public class RankingActionImpl implements RankingAction {
	

	RankingDAO dao=(RankingDAO) DBManager.getDao(RankingDAOImpl.class);
	@Override
	public List<Ranking> loadAllRankingByRankId(int rankTypeId) {
		return DBManager.getUserRankByRankType(rankTypeId);
	}

	//获取排行信息
	@Override
	public List<Ranking> loadRankingByRankId(int rankTypeId, int count) {
			List<Ranking> ranks=DBManager.getUserRankByRankType(rankTypeId);
			List<Ranking> newRanks=new ArrayList<Ranking>();;
			if(ranks.size()>0){				
				for(int i=0;i<count;i++){
					newRanks.add(ranks.get(i));
				}		
			}		
			return newRanks;					
	}

	@Override
	public void insert(Ranking rank) {
		 DBManager.insert(rank);		
	}

	@Override
	public void update(Ranking rank) {
		 DBManager.update(rank);
	}

	@Override
	public void deleteForRankType(int rankTypeID) {
		DBManager.deleteForRankType(rankTypeID);
	}

	@Override
	public boolean queryRank(int playerID) {	
		return DBManager.queryRank(playerID);
	}

}
