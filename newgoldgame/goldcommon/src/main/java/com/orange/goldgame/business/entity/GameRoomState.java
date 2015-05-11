package com.orange.goldgame.business.entity;

/**
 * 房间的状态
 * @author wuruihuang 2013.5.28
 */
public enum GameRoomState {
	/**房间空闲,无人使用*/
	ROOM_EMPTY,
	/**房间有机器人*/
	ROOM_HAS_ROBOT,
	/**房间已被占用，有机器人，不满人*/
	ROOM_USEING_ROBOT_,
	ROOM_USEING_ROBOT,
	ROOM_USEING,
	
	/**房间已被占用，满人*/
	ROOM_FULL,
	/**房间废弃*/
	ROOM_ABANDONED
}
