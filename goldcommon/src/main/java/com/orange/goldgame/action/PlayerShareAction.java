package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.PlayerShare;
import com.orange.goldgame.domain.PlayerShareInfo;


public interface PlayerShareAction {

	  public int insert(PlayerShare playerShare,PlayerShareInfo playerShareInfo);
	  
	  public void update(PlayerShare playerShare,PlayerShareInfo playerShareInfo);
}
