/**
 * SuperStarCommon
 * com.orange.superstar.action
 * PropAction.java
 */
package com.orange.goldgame.action;

import java.util.List;

import com.orange.goldgame.exception.SuperstarException;
import com.orange.goldgame.vo.Prop;
import com.orange.goldgame.vo.UserProp;

/**
 * @author shaojieque
 * 2013-5-9
 */
public interface PropAction {
	/**
	 * 查询指定类型道具
	 * @param type
	 * @return
	 */
	List<Prop> queryPropsByType(int type);
	
	/**
	 * 获取所有道具
	 * @return
	 */
	List<Prop> queryAllProp();
	
	/**
	 * 获取指定道具
	 * @param propId
	 * @return
	 */
	Prop getProp(int propId);
	
	/**
	 * 获取我的道具
	 * @param userId
	 * @return
	 */
	List<UserProp> getMyProps(int userId);
	
	/**
	 * 购买道具
	 * @param userId
	 * @param propId
	 * @return
	 * @throws SuperstarException
	 */
	boolean buyProp(int userId, int propId) throws SuperstarException;
}
