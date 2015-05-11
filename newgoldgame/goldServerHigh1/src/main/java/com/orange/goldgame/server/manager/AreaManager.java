package com.orange.goldgame.server.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.orange.goldgame.server.domain.Area;


/**
 * @author wuruihuang 2013.6.14
 * 管理
 */
public class AreaManager {
	private static AreaManager areaManager;
	
	/** 第一个参数指的是场次的id,第二个参数指场次*/
	private Map<Short, Area> areaMap;
	
	/**
	 * 第一个参数指的是房间的id,第二个参数指的是场次的id，目的将房间和场次关联起来
	 */
	private Map<String, Byte> gameRoomToArea;
	
	private AreaManager(){
		this.areaMap = new HashMap<Short, Area>();
		this.gameRoomToArea = new HashMap<String, Byte>();
	}
	
	public static AreaManager getInstance(){
		if(areaManager==null){
			areaManager = new AreaManager();
		}
		return areaManager;
	}
	
	public Map<String, Byte> getGameRoomToArea() {
		return gameRoomToArea;
	}
	
	public void add(Area area) {
		this.areaMap.put(area.getAreaId(), area);
	}
	
	public Area getAreaById(int areaId) {
		return this.areaMap.get(areaId);
	}
	
	public List<Area> getAllAreas() {
		List<Area> areaList = new ArrayList<Area>();
		for(Entry<Short, Area> entry:areaMap.entrySet()){
			areaList.add(entry.getValue());
		}
		return areaList;
	}
	
	/**
	 * 根据场次id来场次中的房间id
	 * @param areaId
	 * @return
	 */
	public String getRoomIdByAreaId(byte areaId) {
		String roomId = null;
		for(Entry<String, Byte> entry:this.gameRoomToArea.entrySet()){
			if(entry.getValue()==areaId){
				roomId = entry.getKey();
				break;
			}
		}
		return roomId;
	}

	/**
	 * 初始化游戏场次
	 */
	public void initAreas() {
		for(int i=0;i<5;i++){
			Area area = new Area();
			area.setAreaId((byte)i);
			area.setAreaClass((byte)i);
			area.setAreaType((byte)i);
			area.setIntrodution("test");
			area.setLimitGolds(1000);
			area.setSingleGolds(100);
			this.areaMap.put(area.getAreaId(), area);
		}
	}
	
}
