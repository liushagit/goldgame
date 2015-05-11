/**
 * 
 */
package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.PlayerActivityCpa;

/**
 * @author guojiang
 *
 */
public interface PlayerActivityCpaAction {

	int insert(PlayerActivityCpa pac);
	
	void update(PlayerActivityCpa pac);
	
	List<PlayerActivityCpa> quaryByPlayerId(int playerId);
}
