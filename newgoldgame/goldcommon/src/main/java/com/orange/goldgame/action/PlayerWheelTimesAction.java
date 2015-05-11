/**
 * 
 */
package com.orange.goldgame.action;

import com.orange.goldgame.domain.PlayerWheelTimes;

/**
 * @author guojiang
 *
 */
public interface PlayerWheelTimesAction {

	PlayerWheelTimes quaryPlayerWheelTimes(int playerId);
	
	int insert(PlayerWheelTimes pwt);
	
	void update(PlayerWheelTimes pwt);
	
}
