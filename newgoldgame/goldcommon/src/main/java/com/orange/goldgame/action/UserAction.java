/**
 * SuperStarCommon
 * com.orange.superstar.login.action
 * UserAction.java
 */
package com.orange.goldgame.action;

import java.util.Date;

import com.orange.goldgame.exception.LoginException;
import com.orange.goldgame.vo.User;


/**
 * @author shaojieque
 * 2013-5-4
 */
public interface UserAction {
	/**
	 * 查询用户
	 * @param id
	 * @return
	 */
	User getUser(int id);
	
	/**
	 * 查询已有用户
	 * @param id
	 * @return
	 */
	User getExistUser(int id) throws LoginException;
	
	/**
	 * 查询用户
	 * @param name
	 * @return
	 */
	User getUser(String name);
	
	/**
	 * 用户是否存在
	 * @param name
	 * @return
	 */
	boolean isExist(String name);
	
	/**
	 * 创建用户 
	 * @param name
	 * @return
	 */
	User createUser(String name);
	
	/**
	 * 创建用户 
	 * @param user
	 * @return
	 * @throws LoginException
	 */
	User createUserRole(User user) throws LoginException;
	
	
	/**
	 * 修改用户信息
	 * @param userId
	 * @param logo
	 * @param birthday
	 * @param area
	 */
	void modifyUser(int userId, String logo, Date birthday, int area);
}
