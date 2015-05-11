/**
 * SuperStarSystem
 * com.orange.superstar.system.ao
 * BaseAO.java
 */
package com.orange.goldgame.system.ao;

import com.orange.goldgame.system.Application;
import com.orange.goldgame.system.DAOFactory;
import com.orange.goldgame.system.dao.GiftDAO;
import com.orange.goldgame.system.dao.GoodsDAO;
import com.orange.goldgame.system.dao.LogDAO;
import com.orange.goldgame.system.dao.MessageDAO;
import com.orange.goldgame.system.dao.PlayerDAO;


/**
 * @author shaojieque
 * 2013-5-6
 * @param <EstateDAO>
 */
public class BaseAO extends Application {
	protected DAOFactory factory;

	public void setFactory(DAOFactory factory) {
		this.factory = factory;
	}
	protected PlayerDAO getPlayerDAO(){
		return factory.getPlayerDAO();
	}
	protected LogDAO getLogDAO(){
		return factory.getLogDAO();
	}
	protected GiftDAO getGiftDAO(){
		return factory.getGiftDAO();
	}
	protected GoodsDAO getGoodsDAO(){
		return factory.getGoodsDAO();
	}
	protected MessageDAO getMessageDAO(){
		return factory.getMessageDAO();
	}
}
