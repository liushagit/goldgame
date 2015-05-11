package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.Player;
import com.orange.goldgame.domain.PlayerProps;

public interface PlayerPropsAction {

	void updatePlayerProps(PlayerProps pp);
	List<PlayerProps> quaryAllPlayerProps(Player player);
	int insertPlayerProp(PlayerProps pp);
	void deletePlayerProp(PlayerProps pp);
	
	//获取所有玩家所持有兑换券数目
	public List<PlayerProps> getAllPlayerExhangeCount(int exchangeID);
	
	public PlayerProps getPlayerProp(int playerId,int propId);
}
