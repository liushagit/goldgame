/**
 * SuperStarCommon
 * com.orange.superstar.action
 * UserRankAction.java
 */
package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.vo.UserRank;


/**
 * @author shaojieque
 * 2013-5-13
 */
public interface UserRankAction {
	/**
	 * 添加用户排名
	 * @param urs
	 */
	void addUserRanks(List<UserRank> urs);
	
	/**
	 * 获取Top ten
	 * @return
	 */
	List<UserRank> getTopTen();
}
