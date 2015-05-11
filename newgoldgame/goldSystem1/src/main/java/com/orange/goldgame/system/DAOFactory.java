/**
 * JuiceGameServer
 * com.orange.server
 * DAOFactory.java
 */
package com.orange.goldgame.system;

import com.orange.goldgame.system.dao.CharmListDAO;
import com.orange.goldgame.system.dao.ExchangeListDAO;
import com.orange.goldgame.system.dao.ExpectConsumeLogDAO;
import com.orange.goldgame.system.dao.FriendsDAO;
import com.orange.goldgame.system.dao.GiftDAO;
import com.orange.goldgame.system.dao.GoodsDAO;
import com.orange.goldgame.system.dao.LogDAO;
import com.orange.goldgame.system.dao.MessageDAO;
import com.orange.goldgame.system.dao.PlayerDAO;
import com.orange.goldgame.system.dao.PropDAO;
import com.orange.goldgame.system.dao.RaceListDAO;
import com.orange.goldgame.system.dao.WealthListDAO;


/**
 * @author shaojieque 
 * 2013-3-24
 */
public interface DAOFactory {
	public PlayerDAO getPlayerDAO();
	public GiftDAO getGiftDAO();
	public LogDAO getLogDAO();
	public FriendsDAO getFriendsDAO();
	public WealthListDAO getWealthListDAO();
	public CharmListDAO getCharmListDAO();
	public ExchangeListDAO getExchangeListDAO();
	public RaceListDAO getRaceListDAO();
	public PropDAO getPropDAO();
	public GoodsDAO getGoodsDAO();
	public MessageDAO getMessageDAO();
	public ExpectConsumeLogDAO getExpectConsumeLogDAO();
}
