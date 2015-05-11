package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.PlayerWheelRecoder;

public interface PlayerWheelRecoderAction {

	int insertOneRecoder(PlayerWheelRecoder recoder);

	void updateRecoder(PlayerWheelRecoder recoder);
	
	PlayerWheelRecoder quaryById(int id);
	
	List<PlayerWheelRecoder> quaryByTime(String beginTime,String endTime,int status);
}
