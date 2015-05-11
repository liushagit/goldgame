/**
 * SuperStarCommon
 * com.orange.superstar.action
 * PropAction.java
 */
package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.domain.PackageProps;
import com.orange.goldgame.domain.PropsConfig;

/**
 * @author wuruihuang 2013.6.20
 * 2013-5-9
 */
public interface PropsAction {
	
	void insertPlayerProps(int playerid);
	
	List<PropsConfig> getAllPropsConfig();
	
	/**
	 * 获取特定的道具信息
	 * @return
	 */
	PropsConfig queryPropsById(int propsConfigId);
	
	/**
	 * 查询指定类型的所有道具
	 * @param type
	 * @return
	 */
	List<PropsConfig> queryPropsByType(int type);
	
	/**
	 * 获取在商城中出售的所有道具
	 * @return
	 */
	List<PackageProps> queryAllPackageProp();
	
	/**
	 * 获取在商城中出售的特定道具
	 * @return
	 */
	PackageProps queryPackagePropsById(int propsConfigId);

	void updatePropsNumberByTowId(int playerId, int propsId, int integer);

}
